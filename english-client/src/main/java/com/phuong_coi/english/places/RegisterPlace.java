package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class RegisterPlace extends Place{
    
    public RegisterPlace(){
    }

    

    public static class Tokenizer implements PlaceTokenizer<RegisterPlace> {

        @Override
        public RegisterPlace getPlace(String token) {
            return new RegisterPlace();
        }

        @Override
        public String getToken(RegisterPlace place) {
            return PlaceToken.REGISTER;
        }
    
        
    }
}
