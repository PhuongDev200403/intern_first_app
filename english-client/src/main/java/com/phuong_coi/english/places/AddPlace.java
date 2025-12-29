package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class AddPlace extends Place{
    public AddPlace(){

    }

    public static class Tokenizer implements PlaceTokenizer{

        @Override
        public Place getPlace(String token) {
            return new AddPlace();
        }

        @Override
        public String getToken(Place place) {
            return PlaceToken.ADD;
        }

    }
}
