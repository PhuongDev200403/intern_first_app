package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class LoginPlace extends Place{
    
    public LoginPlace(){
    }
    
    public String getToken(){
        return PlaceToken.LOGIN;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace>{

        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace();
        }

        @Override
        public String getToken(LoginPlace place) {
            return place.getToken();
        }
        
    }
}
