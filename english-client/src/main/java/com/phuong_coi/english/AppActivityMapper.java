package com.phuong_coi.english;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.phuong_coi.english.activities.FormActivity;
import com.phuong_coi.english.activities.HomeActivity;
import com.phuong_coi.english.activities.ListActivity;
import com.phuong_coi.english.activities.LoginActivity;
import com.phuong_coi.english.activities.RegisterActivity;
import com.phuong_coi.english.places.AddPlace;
import com.phuong_coi.english.places.HomePlace;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.places.LoginPlace;
import com.phuong_coi.english.places.RegisterPlace;

public class AppActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;
    
    public AppActivityMapper(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
    
    @Override
    public Activity getActivity(Place place) {
        if (place instanceof LoginPlace) {
            return new LoginActivity(clientFactory);
        } else if (place instanceof HomePlace) {
            return new HomeActivity(clientFactory);
        } else if (place instanceof ListPlace) {
            return new ListActivity(clientFactory);
        } else if (place instanceof RegisterPlace) {
            return new RegisterActivity(clientFactory);
        } else if (place instanceof AddPlace) {
            return new FormActivity(clientFactory);
        }
        return null;
    }
}