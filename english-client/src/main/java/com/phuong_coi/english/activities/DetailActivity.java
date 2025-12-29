package com.phuong_coi.english.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.places.ListPlace;
import com.phuong_coi.english.view.EmployeeDetail;

public class DetailActivity extends AbstractActivity{
    private ClientFactory clientFactory;
    private EventBus eventBus;
    private Place place;

    private EmployeeDetail view;

    public DetailActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getEmployeeDetail();
        panel.setWidget(view.asWidget());

        //Lắng nghe tín hiệu click vào nút cập nhật từ bên table view hiển thị popup cùng với nhân viên tương ứng
        eventBus.addHandler(EmployeeEvent.TYPE, e ->{
            if(e.geAction() == Action.VIEW){ 
                EmployeeDTO em = e.getEmployee();
                GWT.log("đang xem chi nhân viên có employeeId :" + em.getEmployeeId() + em.getFullName());
                view.showDetail(em);

                //Hiển thị detail 

            }
            
        });
    }

    private void onBtnUpdateClicked(EmployeeDTO em){
        // load dữ liệu từ form cập nhật và kiểm tra tính hợp lệ của dữ liệu đấy
        if (view.getFullName().isEmpty()) {
            Window.alert("tên nhân viên không được để trống nhé");
        }
        if (view.getRole().isEmpty()) {
            Window.alert("Quyền truy cập không được để trống");
        }

        em.setFullName(view.getFullName());
        em.setRole(view.getRole());

        // call service để lấy ra nhân viên tương ứng và set dữ liệu mới cho nhân viên đấy
        clientFactory.getEmployeeService().updateEm(em, em.getEmployeeId(), new AsyncCallback<EmployeeDTO>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Có lỗi khi cập nhật thông tin nhân viên :" + caught.getMessage());
            }

            @Override
            public void onSuccess(EmployeeDTO result) {
                Window.alert("Cập nhật thông tin nhân viên thành công");
                GWT.log("Bắt đầu fire event sự kiện click nút cập nhật trong form");
                eventBus.fireEvent(new EmployeeEvent(Action.UPDATE, em));
                GWT.log("Thành cồng fire event cập nhật thông tin nhân viên tại detail activity");
                // Sử dụng ListPlace như một giá trị Place đúng kiểu, ví dụ:
                clientFactory.getPlaceController().goTo(new ListPlace());
            }
        });
    }
}
