package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
import com.phuong_coi.english.places.AddPlace;
import com.phuong_coi.english.places.ListPlace;
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
        view = clientFactory.getHome();
        panel.setWidget(view.asWidget());
        
        // Handle button clicks
        view.getBtnAdd().addClickHandler(e -> 
            clientFactory.getPlaceController().goTo(new AddPlace())
        );
        
        view.getBtnView().addClickHandler(e -> 
            clientFactory.getPlaceController().goTo(new ListPlace())
        );
    }
    
}
