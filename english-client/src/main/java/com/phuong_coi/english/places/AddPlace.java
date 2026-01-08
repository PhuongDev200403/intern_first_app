package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class AddPlace extends Place{
    
    public AddPlace(){
    }

    public String getToken(){
        return PlaceToken.ADD;
    }

    public static class Tokenizer implements PlaceTokenizer<AddPlace>{

        @Override
        public AddPlace getPlace(String token) {
            return new AddPlace();
        }

        @Override
        public String getToken(AddPlace place) {
            return place.getToken();
        }

    }
}
