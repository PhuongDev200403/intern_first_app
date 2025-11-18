package com.phuong_coi.english;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.presenter.UserListPresenter;
import com.phuong_coi.english.view.CwFormView;
import com.phuong_coi.english.view.UserListView;

public class App implements EntryPoint {

	private final DeckPanel deckPanel = new DeckPanel();

	private final UserListView userListView = new UserListView();

	public void onModuleLoad() {

		String token = History.getToken();
		if (token != null && token.startsWith("#")) {
			History.replaceItem(token.substring(1), false); 
		}

		// add các view vào giao diện chính
		UserListPresenter presenter = new UserListPresenter(userListView);
		CwFormView formView = new CwFormView(presenter);
		
		// Kết nối formView với userListView để có thể fill data
		userListView.setFormView(formView);

		deckPanel.add(formView.onInitialize());

		deckPanel.add(userListView.asWidget());

		deckPanel.showWidget(0);

		// Add 2 nút vào để tạo menu
		RootPanel.get().add(createMenu());
		RootPanel.get().add(deckPanel);

		History.addValueChangeHandler(this::onHistoryChanged);

		// Load trang đầu tiên nến url rỗng
		if (History.getToken().isEmpty()) {
			History.newItem("add-user", true);
		} else {
			History.fireCurrentHistoryState();
		}

		presenter.loadUsers();
	}

	public Widget createMenu() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(10);
		panel.addStyleName("nav nav-pills mb-4 shadow-sm bg-white rounded");

		Anchor formLink = new Anchor("Thêm user", "add-user");
		formLink.addStyleName("nav-link text-primary fw-bold text-decoration-none");
		Anchor userList = new Anchor("danh sách users", "user-list");
		userList.addStyleName("nav-link text-primary fw-bold text-decoration-none");

		formLink.addClickHandler(e -> {
			e.preventDefault();
			History.newItem("add-user", true);
			formLink.addStyleName("active text-light");
			userList.removeStyleName("active");
			userList.removeStyleName("text-light");
		});

		userList.addClickHandler(e -> {
			e.preventDefault();
			History.newItem("user-list", true);
			userList.addStyleName("active text-light");
			formLink.removeStyleName("active");
			formLink.removeStyleName("text-light");
		});

		panel.add(formLink);
		panel.add(userList);
		return panel;
	}

	private void onHistoryChanged(ValueChangeEvent<String> event) {
		String token = event.getValue();

		switch (token) {
			case "add-user":
				deckPanel.showWidget(0);
				break;

			case "user-list":
				deckPanel.showWidget(1);
				break;
			default:
				deckPanel.showWidget(0);
				break;
		}
	}
}
