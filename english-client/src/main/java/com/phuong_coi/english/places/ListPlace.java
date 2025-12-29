package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class ListPlace extends Place{
    public static class Tokenizer implements PlaceTokenizer {

        @Override
        public Place getPlace(String token) {
           return new ListPlace();
        }

        @Override
        public String getToken(Place place) {
            return PlaceToken.LIST;
        }
    
    }
}
