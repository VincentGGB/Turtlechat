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
package com.slowchat.letterbox.service;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "letterbox")
public class LetterBoxModel {
    @PrimaryKey()
    @NonNull
    private String idLetterBox;
    private StateType state;
    private float latitude;
    private float longitude;
    private Date lastUpdated;
    private Date removalDate;
    private Boolean needAlim;

    @NonNull
    public String getIdLetterBox() {
        return idLetterBox;
    }

    public void setIdLetterBox(@NonNull String idLetterBox) {
        this.idLetterBox = idLetterBox;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getNeedAlim() {
        return needAlim;
    }

    public void setNeedAlim(Boolean needAlim) {
        this.needAlim = needAlim;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

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


