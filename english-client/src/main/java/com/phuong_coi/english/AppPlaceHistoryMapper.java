package com.phuong_coi.english;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.phuong_coi.english.places.AddPlace;
import com.phuong_coi.english.places.HomePlace;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.places.LoginPlace;
import com.phuong_coi.english.places.RegisterPlace;

public class AppPlaceHistoryMapper implements PlaceHistoryMapper {
    public static final String DELIMITER = "/";
    //public static final String COLON = "=";

    @Override
    public Place getPlace(String token) {
        String[] tokens = token.split(DELIMITER);
        if (tokens.length ==  0 || token.trim().isEmpty()) {
            return new LoginPlace();
        }
        Place nextPlace = null;
        String tokenPlace = tokens[0].trim();
        if (tokenPlace.indexOf(PlaceToken.ADD) == 0) {
            nextPlace = new AddPlace();
        }else if (tokenPlace.indexOf(PlaceToken.HOME) == 0) {
            nextPlace = new HomePlace();
        }else if(tokenPlace.indexOf(PlaceToken.LIST) == 0){
            nextPlace = new ListPlace();
        }else if(tokenPlace.indexOf(PlaceToken.REGISTER) == 0){
            nextPlace = new RegisterPlace();
        }else if(tokenPlace.indexOf(PlaceToken.LOGIN) == 0){
            nextPlace = new LoginPlace();
        }else{
            nextPlace = new LoginPlace();
        }
        return nextPlace;
    }

    @Override
    public String getToken(Place place) {
        
        if (place instanceof LoginPlace) {
            return PlaceToken.LOGIN;
        } else if (place instanceof RegisterPlace) {
            return PlaceToken.REGISTER;
        } else if (place instanceof HomePlace) {
            return PlaceToken.HOME;
        } else if (place instanceof ListPlace) {
            return PlaceToken.LIST;
        } else if (place instanceof AddPlace) {
            return PlaceToken.ADD;
        }
        return PlaceToken.LOGIN;
    }
}
