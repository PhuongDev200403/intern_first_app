package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
//import com.phuong_coi.english.event.AuthActionEvent;
//import com.phuong_coi.english.event.AuthActionEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.places.LoginPlace;
import com.phuong_coi.english.view.FormRegisterView;

public class RegisterActivity extends AbstractActivity{

    private ClientFactory clientFactory;
    private EventBus eventBus;
    private Place place;

    private FormRegisterView view;

    public RegisterActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getFormRegister();
        panel.setWidget(view.asWidget());
        view.getBtnRegister().addClickHandler(event -> onRegisterClicked());
    }

    private void onRegisterClicked(){
        //Kiểm tra tính hợp lệ của dữ liệu
        if(view.getFullName() == null || view.getFullName().isEmpty()){
            view.showMessage("Không được để trống tên nhân viên");
            return;
        }
        if(view.getEmail() == null || view.getEmail().isEmpty()){
            view.showMessage("Không được để trống email của nhân viên");
            return;
        }
        if(view.getPhoneNumber() == null || view.getPhoneNumber().isEmpty()){
            view.showMessage("Không được để trống số điện thoại");
            return;
        }

        EmployeeRequest employee = new EmployeeRequest();
        employee.setEmail(view.getEmail());
        employee.setFullName(view.getFullName());
        employee.setPhoneNumber(view.getPhoneNumber());
        employee.setPassword(view.getPassword());
        employee.setRole(view.getRole());

        //Call service
        clientFactory.getAuthService().register(employee,new AsyncCallback<EmployeeDTO>(){

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Lỗi khi đăng ký :" + caught.getMessage());
                view.clearForm();
            }
            @Override
            public void onSuccess(EmployeeDTO result) {
                //thành công thì fire event và đổi sang màn đăng nhập
                GWT.log("Đăng ký thành công nhân viên mới :" + result);
                //fire event đăng ký thành công thì bay sang trang đăng nhập
                //eventBus.fireEvent(new AuthActionEvent(Action.Register, result));
                clientFactory.getPlaceController().goTo(new LoginPlace());
            }
        });
    }
}
