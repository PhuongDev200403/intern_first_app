package com.phuong_coi.english;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.phuong_coi.english.places.AddPlace;
import com.phuong_coi.english.places.HomePlace;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.places.LoginPlace;
import com.phuong_coi.english.places.RegisterPlace;

public class AppPlaceHistoryMapper implements PlaceHistoryMapper {

    @Override
    public Place getPlace(String token) {
        if (token == null || token.trim().isEmpty()) {
            return new LoginPlace();
        }
        
        switch (token) {
            case "login":
                return new LoginPlace();
            case "register":
                return new RegisterPlace();
            case "home":
                return new HomePlace();
            case "employees":
                return new ListPlace();
            case "add-employee":
                return new AddPlace();
            default:
                return new LoginPlace();
        }
    }

    @Override
    public String getToken(Place place) {
        if (place instanceof LoginPlace) {
            return "login";
        } else if (place instanceof RegisterPlace) {
            return "register";
        } else if (place instanceof HomePlace) {
            return "home";
        } else if (place instanceof ListPlace) {
            return "employees";
        } else if (place instanceof AddPlace) {
            return "add-employee";
        }
        return "login";
    }
}
