package com.phuong_coi.english.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.phuong_coi.english.PlaceToken;

public class ListPlace extends Place{

    public ListPlace(){}

    public String getToken(){
        return PlaceToken.LIST;
    }
    public static class Tokenizer implements PlaceTokenizer<ListPlace>{

        @Override
        public ListPlace getPlace(String token) {
            return new ListPlace();
        }

        @Override
        public String getToken(ListPlace place) {
            return place.getToken();
        }
    }
}
