package com.phuong_coi.english;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.phuong_coi.english.places.AddPlace;
import com.phuong_coi.english.places.HomePlace;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.places.LoginPlace;
import com.phuong_coi.english.places.RegisterPlace;

public class AppPlaceHistoryMapper implements PlaceHistoryMapper{
    public static final String DELIMITER = "/";
	public static final String COLON = "=";

    private PlaceHistoryMapper placeHistoryMapper;

    public AppPlaceHistoryMapper(){};

    public AppPlaceHistoryMapper(PlaceHistoryMapper placeHistoryMapper){
        this.placeHistoryMapper = placeHistoryMapper;
    }

    @Override
    public Place getPlace(String token) {
        String[] tokens = token.split(DELIMITER); //lấy ra token trên url
        GWT.log("Token hiện tại :" + java.util.Arrays.toString(tokens));
        String tokenPlace = tokens[0].trim();
        if (token == null || token.trim().isEmpty()) {
            return new LoginPlace(); // default
        }else if (tokenPlace.startsWith(PlaceToken.HOME)) {
            return new HomePlace();
        }
        else if (tokenPlace.startsWith(PlaceToken.ADD)){
            return new ListPlace();
        }else if(tokenPlace.startsWith(PlaceToken.REGISTER)){
            return new RegisterPlace();
        }else{
            return new LoginPlace();
        }
    }

    //get default place: mặc định là trang login
    public Place defaultPlace(){
        return new LoginPlace(); // trả về login place;
    }

    //lấy ra giá trị value trong param được truyền vào 
    public long getValue(String str){
		String itemIdValue = "";
		if (str.contains(COLON)) {
			String [] tokens = str.split(COLON);
			itemIdValue = tokens[1];
		}
		else {
			itemIdValue = str;
		}
		try{
			return Long.valueOf(itemIdValue);
		} catch(Exception e){
			return -1;
		}
	}

    @Override
    public String getToken(Place place) {
        String token = placeHistoryMapper.getToken(place); // trả về token được lấy từ phương thức getToken trong Place tương ứng
        if(token != null && token.contains(COLON)){
			if (token.endsWith(COLON)) {
				token = token.replace(COLON, "");
	        }else {
	        	token = token.replace(COLON, "");
	        }
			return token;
		}

        if(place instanceof LoginPlace){ // nếu place được truyền vào là instance của place tương ứng thì trả về token tương ứng
            return PlaceToken.LOGIN;
        }else if(place instanceof HomePlace){
            return PlaceToken.HOME;
        }else if(place instanceof ListPlace){
            return PlaceToken.LIST;
        }else if(place instanceof RegisterPlace){
            return PlaceToken.REGISTER;
        }else if(place instanceof AddPlace){
            return PlaceToken.ADD;
        }
        else {
            return PlaceToken.LOGIN;
        }
    }
}
