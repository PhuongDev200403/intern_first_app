package com.phuong_coi.english;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.presenter.FormPresenter;
import com.phuong_coi.english.presenter.UserDetailPresenter;
import com.phuong_coi.english.presenter.UserListPresenter;
import com.phuong_coi.english.view.FormView;
import com.phuong_coi.english.view.UserDetailView;
import com.phuong_coi.english.view.UserListView;

public class App implements EntryPoint {

	private final DeckPanel deck = new DeckPanel();

	public void onModuleLoad() {
		FormView formView = new FormView();
		UserListView listView = new UserListView();
		UserDetailView detailView = new UserDetailView();

	
		UserDetailPresenter detailPresenter = new UserDetailPresenter(detailView);
		UserListPresenter listPresenter = new UserListPresenter(listView, detailPresenter);
		FormPresenter formPresenter = new FormPresenter(formView);

		// Kết nối ngược: ListView cần biết FormPresenter để click row
		//listView.setFormPresenter(formPresenter);
		//listView.setDetailPopupPresenter(detailPresenter);

		deck.add(formView);
		deck.add(listView.asWidget());
		deck.showWidget(0);

		RootPanel.get().add(createMenu());
		RootPanel.get().add(deck);

		History.addValueChangeHandler(this::handleHistory);
		if (History.getToken().isEmpty()) {
			History.newItem("add-user");
		} else {
			History.fireCurrentHistoryState();
		}

		listPresenter.loadUsers();
	}

	private Widget createMenu() {
		HorizontalPanel nav = new HorizontalPanel();
		nav.setSpacing(15);
		nav.addStyleName("nav nav-pills mb-3");

		Anchor addLink = new Anchor("Form");
		Anchor listLink = new Anchor("List");
		addLink.setHref("#add-user");
		listLink.setHref("#user-list");
		addLink.addStyleName("text-decoration-none fs-5 me-3 text-primary");
		listLink.addStyleName("text-decoration-none fs-5 text-primary");

		addLink.addClickHandler(e -> {
			e.preventDefault();
			History.newItem("add-user");
			addLink.addStyleName("active");
			listLink.removeStyleName("active");
		});
		listLink.addClickHandler(e -> {
			e.preventDefault();
			History.newItem("user-list");
			addLink.removeStyleName("active");
			listLink.addStyleName("active");
		});

		nav.add(addLink);
		nav.add(listLink);
		return nav;
	}

	private void handleHistory(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if ("user-list".equals(token)) {
			deck.showWidget(1);
		} else {
			deck.showWidget(0);
		}
	}
}
