/*
        TurtleChat
        Copyright (C) 2023  TurtleChat Open Source Community

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.slowchat.letterbox.domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LetterBoxModel implements Serializable {
    private String idLetterBox;
    private StateType state;
    private float latitude;
    private float longitude;
    private Date lastUpdated;
    private Date removalDate;
    private Boolean needAlim = false;

    public LetterBoxModel(String idLetterBox, StateType state, float latitude, float longitude, Date lastUpdated, Boolean needAlim) {
        this.idLetterBox = idLetterBox;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
        this.needAlim = needAlim;
    }

    public LetterBoxModel() {}

    public String getIdLetterBox() {
        return idLetterBox;
    }

    public StateType getState() {
        return state;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public Boolean getNeedAlim() {
        return needAlim;
    }

    public void setIdLetterBox(String idLetterBox) {
        this.idLetterBox = idLetterBox;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public void setNeedAlim(Boolean needAlim) {
        this.needAlim = needAlim;
    }

    public com.slowchat.letterbox.service.LetterBoxModel mapObjectToDbObject() {
        com.slowchat.letterbox.service.LetterBoxModel letterBoxModel = new com.slowchat.letterbox.service.LetterBoxModel();
        letterBoxModel.setIdLetterBox(this.idLetterBox);
        letterBoxModel.setLatitude(this.latitude);
        letterBoxModel.setLongitude(this.longitude);
        letterBoxModel.setNeedAlim(this.needAlim);
        letterBoxModel.setState(com.slowchat.letterbox.service.StateType.valueOf(String.valueOf(this.state)));
        letterBoxModel.setLastUpdated(this.lastUpdated);
        letterBoxModel.setRemovalDate(this.removalDate);
        return letterBoxModel;
    }

    public static com.slowchat.letterbox.domain.LetterBoxModel mapDbObjectToDomainObject(com.slowchat.letterbox.service.LetterBoxModel letterboxDb) {
        com.slowchat.letterbox.domain.LetterBoxModel letterBoxModel = new com.slowchat.letterbox.domain.LetterBoxModel();
        letterBoxModel.setIdLetterBox(letterboxDb.getIdLetterBox());
        letterBoxModel.setLatitude(letterboxDb.getLatitude());
        letterBoxModel.setLongitude(letterboxDb.getLongitude());
        letterBoxModel.setState(com.slowchat.letterbox.domain.StateType.valueOf(String.valueOf(letterboxDb.getState())));
        letterBoxModel.setLastUpdated(letterboxDb.getLastUpdated());
        letterBoxModel.setRemovalDate(letterboxDb.getRemovalDate());
        letterBoxModel.setNeedAlim(letterboxDb.getNeedAlim());
        return letterBoxModel;
    }

    public static List<LetterBoxModel> mapDbListObjectsToDomainListObjects(List<com.slowchat.letterbox.service.LetterBoxModel> letterBoxModelList) {
        List<LetterBoxModel> domainList = new ArrayList<>();
        for (com.slowchat.letterbox.service.LetterBoxModel letterboxDb : letterBoxModelList) {
            LetterBoxModel letterBoxModel = mapDbObjectToDomainObject(letterboxDb);
            domainList.add(letterBoxModel);
        }
        return domainList;
    }

    @NonNull
    @Override
    public String toString() {
        return "LetterBoxModel{" +
                "idLetterBox='" + idLetterBox + '\'' +
                ", state=" + state +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lastUpdated=" + lastUpdated +
                ", removalDate=" + removalDate +
                ", needAlim=" + needAlim +
                '}';
    }
}
