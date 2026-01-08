package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class HomePlace extends Place{

    public HomePlace(){
    }

    public String getToken(){
        return PlaceToken.HOME;
    }

    
    public static class Tokenizer implements PlaceTokenizer<HomePlace>{

        @Override
        public HomePlace getPlace(String token) {
            return new HomePlace();
        }

        @Override
        public String getToken(HomePlace place) {
            return place.getToken();

        }
        
    }
}
