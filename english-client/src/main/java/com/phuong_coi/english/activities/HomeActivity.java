package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
import com.phuong_coi.english.view.Home;

public class HomeActivity extends AbstractActivity{

    private ClientFactory clientFactory;
    private EventBus eventBus;
    private Place place;

    private Home view;

    public HomeActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        
    }
    
}
