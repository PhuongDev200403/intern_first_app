package com.phuong_coi.english;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.phuong_coi.english.places.LoginPlace;

public class App implements EntryPoint {

	public static final ClientFactory clientFactory = new ClientFactoryImpl();
	public LoginPlace defaultPlace = new LoginPlace();
	public void onModuleLoad(){
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		SimplePanel layout = new SimplePanel();

		AppActivityMapper activityMaper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMaper, eventBus);
		activityManager.setDisplay(layout);
		RootPanel.get().add(layout);

		AppPlaceHistoryMapper historyMapper = new AppPlaceHistoryMapper();
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);

		historyHandler.handleCurrentHistory();
	}
}