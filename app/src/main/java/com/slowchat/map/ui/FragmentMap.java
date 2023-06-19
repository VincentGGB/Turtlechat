package com.slowchat.map.ui;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slowchat.BuildConfig;
import com.slowchat.R;
import com.slowchat.letterbox.domain.LetterBoxModel;
import com.slowchat.letterbox.domain.LetterBoxViewModel;
import com.slowchat.letterbox.ui.DialogCreateLetterBox;
import com.slowchat.letterbox.ui.DialogEditLetterBox;
import com.slowchat.letterbox.ui.DialogLetterBox;
import com.slowchat.utilities.gps.Gps;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMap extends Fragment {
    private static final double BOUNDING_BOX_ZOOMED = 0.005;
    private static final double BOUNDING_BOX_ZOOMED_OUT = 5;

    private Gps gps;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gps = new Gps(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        MapView map = view.findViewById(R.id.fragment_map_mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMultiTouchControls(true);

        map.addOnFirstLayoutListener((v, left, top, right, bottom) -> {
            if (gps.isGpsAllowed()) {
                Location location = gps.getLocation();
                zoomAndCenterMap(map, location, false);
            } else {
                double latitude = 48.86666;
                double longitude = 2.33333;

                BoundingBox boundingBox = new BoundingBox(
                        latitude + BOUNDING_BOX_ZOOMED_OUT,
                        longitude + BOUNDING_BOX_ZOOMED_OUT,
                        latitude - BOUNDING_BOX_ZOOMED_OUT,
                        longitude - BOUNDING_BOX_ZOOMED_OUT
                );

                map.zoomToBoundingBox(boundingBox, false);
            }
        });

        List<com.slowchat.letterbox.domain.LetterBoxModel> myLetterBoxList = LetterBoxViewModel.getLetterBoxes();
        Map<Integer, LetterBoxModel> myMappedLetterBoxList = new HashMap<>();
        ArrayList<OverlayItem> overlayItems = new ArrayList<>();

        for (int x = 0; x < myLetterBoxList.size(); x++) {
            myMappedLetterBoxList.put(x, myLetterBoxList.get(x));
            String letterBoxId = String.valueOf(myLetterBoxList.get(x).getIdLetterBox());
            float letterBoxLatitude = myLetterBoxList.get(x).getLatitude();
            float letterBoxLongitude = myLetterBoxList.get(x).getLongitude();

            overlayItems.add(new OverlayItem(letterBoxId, "", new GeoPoint(letterBoxLatitude, letterBoxLongitude)));
        }

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayItems = new ItemizedOverlayWithFocus<>(view.getContext(), overlayItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {

                com.slowchat.letterbox.domain.LetterBoxModel searchedLetterBoxModel = myMappedLetterBoxList.get(index);

                assert searchedLetterBoxModel != null;
                DialogLetterBox myDialogLetterBox = new DialogLetterBox(requireContext(), searchedLetterBoxModel);
                myDialogLetterBox.show();

                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {

                com.slowchat.letterbox.domain.LetterBoxModel searchedLetterBoxModel = myMappedLetterBoxList.get(index);

                assert searchedLetterBoxModel != null;
                DialogEditLetterBox myDialogLetterBox = new DialogEditLetterBox(requireContext(), searchedLetterBoxModel);
                myDialogLetterBox.show();

                return true;
            }
        });

        itemizedOverlayItems.setFocusItemsOnTap(false);
        map.getOverlays().add(itemizedOverlayItems);

        FloatingActionButton fab = view.findViewById(R.id.fragment_map_fab);
        fab.setOnClickListener(l -> {
            Location location = gps.getLocation();
            zoomAndCenterMap(map, location, true);
        });

        FloatingActionButton fabCreateLetterBox = view.findViewById(R.id.fragment_map_fab_create_letter_box);
        fabCreateLetterBox.setOnClickListener(l -> {
            DialogCreateLetterBox myDialogCreateLetterBox = new DialogCreateLetterBox(requireContext(), new LetterBoxModel(), false);
            myDialogCreateLetterBox.show();
        });
    }

    private static void zoomAndCenterMap(MapView map, Location location, boolean animated) {
        if (location == null) {
            return;
        }

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        BoundingBox boundingBox = new BoundingBox(
                latitude + BOUNDING_BOX_ZOOMED,
                longitude + BOUNDING_BOX_ZOOMED,
                latitude - BOUNDING_BOX_ZOOMED,
                longitude - BOUNDING_BOX_ZOOMED
        );

        map.zoomToBoundingBox(boundingBox, animated);
    }
}