package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
//import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
//import com.phuong_coi.english.event.EmployeeEvent;
//import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.model.EmployeeRequest;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.view.Form;

public class FormActivity extends AbstractActivity {

    private ClientFactory clientFactory;
    private EventBus eventBus;
    private Place place;

    private Form view;

    public FormActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getForm();
        panel.setWidget(view.asWidget());
        view.getBtnAdd().addClickHandler(event -> onAddBtnClicked());
    }

    // Sự kiện click nút add
    private void onAddBtnClicked() {
        // kiểm tra dữ liệu có hợp lệ hay không bên phía client
        if (view.getEmail().isEmpty()) {
            view.showMessage("Không được để trống trường email");
            return;
        }
        if (view.getFullName().isEmpty()) {
            view.showMessage("Không được để trống trường fullname");
            return;
        }
        if (view.getSoDienThoai().isEmpty()) {
            view.showMessage("Không được để trống trường số điện thoại");
            return;
        }
        if (view.getPassword().isEmpty()) {
            view.showMessage("Không được để trống password");
            return;
        }
        // Tạo nhân viên mới với dữ liệu được lấy từ form
        EmployeeRequest request = new EmployeeRequest();
        request.setEmail(view.getEmail());
        request.setFullName(view.getFullName());
        request.setPhoneNumber(view.getSoDienThoai());
        request.setRole(view.getRole());
        request.setPassword(view.getPassword());

        // Call service bằng clientFactory
        clientFactory.getEmployeeService().createEm(request, new AsyncCallback<EmployeeDTO>() {

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage(caught.getMessage());
            }

            @Override
            public void onSuccess(EmployeeDTO result) {
                view.showMessage("Thêm nhân viên thành công!");
                view.clearForm();

                //eventBus.fireEvent(new EmployeeEvent(Action.CREATE, result));

                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        clientFactory.getPlaceController().goTo(new ListPlace()); //nhảy sang trang danh sách nhân viên
                    }
                };
                timer.schedule(1500);
            }

        });
    }
}
