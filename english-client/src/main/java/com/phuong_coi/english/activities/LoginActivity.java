package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
import com.phuong_coi.english.event.AuthActionEvent;
import com.phuong_coi.english.event.AuthActionEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.places.HomePlace;
import com.phuong_coi.english.view.FormLoginView;

public class LoginActivity extends AbstractActivity{

    private ClientFactory clientFactory;
    private Place place;
    private EventBus eventBus;
    
    private FormLoginView view;

    // public LoginActivity(ClientFactory clientFactory, Place place){
    //     this.clientFactory = clientFactory;
    //     this.place = place;
    // }

    public LoginActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getFormLogin();
        panel.setWidget(view.asWidget());
        view.getBtnLogin().addClickHandler(event -> onLogin());
    }
    

    //Phương thức click nút đăng nhập
    private void onLogin(){
        //Kiểm tra tính hợp lệ của dữ liệu
        if(view.getEmail() == null || view.getEmail().isEmpty()){
            view.showMessageError("Email không được để trống");
        }

        if(view.getPassword() == null || view.getPassword().isEmpty()){
            view.showMessageError("Password không được để trống");
        }

        clientFactory.getAuthService().login(view.getEmail(), view.getPassword(), new AsyncCallback<EmployeeDTO>() {
            @Override
            public void onSuccess(EmployeeDTO result) {
                GWT.log("Phát tín hiệu nút đăng nhập được click thành công tại LoginActivities");
                eventBus.fireEvent(new AuthActionEvent(Action.Login, result));
                // nếu đúng email và password đúng thì đều vào trang homepage
                GWT.log("Người dùng :" + result.getFullName() + " Đang đăng nhập");
                clientFactory.getPlaceController().goTo(new HomePlace());
            }

            @Override
            public void onFailure(Throwable caught) {
                view.showMessageError("Đăng nhập thất bại: " + caught.getMessage());
            }
        });
    }
}