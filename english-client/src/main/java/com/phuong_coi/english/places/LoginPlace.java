package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class LoginPlace extends Place{
    //Không cần param nên không cần định nghĩa các thuộc tính

    public LoginPlace(){
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace>{

        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace();
        }

        @Override
        public String getToken(LoginPlace place) {
            return PlaceToken.LOGIN;
        }
    }

}
