package com.example.moorthi.taskkttelematices.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class LocationModel extends RealmObject {

    @PrimaryKey
    public Long id;

    public double lat;

    public double lng;

}
