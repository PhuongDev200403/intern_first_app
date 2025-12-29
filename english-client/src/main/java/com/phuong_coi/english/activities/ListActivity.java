package com.phuong_coi.english.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.phuong_coi.english.ClientFactory;
import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.view.EmployeeDetail;
import com.phuong_coi.english.view.TableView;

public class ListActivity extends AbstractActivity{
    private ClientFactory clientFactory;
    private Place place;
    private EventBus eventBus;

    private TableView view;
    private EmployeeDetail detailView;

    public ListActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = clientFactory.getTableView();
        detailView = clientFactory.getEmployeeDetail();
        view.setEventBus(eventBus);
        panel.setWidget(view.asWidget());

        loadEmployees();

        detailView.getBtnUpdate().addClickHandler(c -> {
            EmployeeDTO employeeDTO = detailView.getDataFromForm();
            onBtnUpdateClicked(employeeDTO);
        });
        //view.getBtnRemove().addClickHandler(c -> onBtnRemoveClicked);
        //bắt sự kiện create employee
        eventBus.addHandler(EmployeeEvent.TYPE, event -> {
            switch (event.geAction()) {
                case CREATE:
                    GWT.log("Nhân viên mới được tạo và reload bảng");
                    loadEmployees(); // reload toàn bộ
                    break;
                case UPDATE:
                    detailView.showDetail(event.getEmployee());
                    break;
                case DELETE:
                    GWT.log("Nhân viên bị xóa và reload bảng");
                    onBtnRemoveClicked(event.getEmployee());
                    break;
                default:
                    break;
            }
        });
    }

    private void loadEmployees(){
        //call RPC
        clientFactory.getEmployeeService().getAll(new AsyncCallback<List<EmployeeDTO>>(){
            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Lỗi khi lấy danh sách nhân viên :" + caught.getMessage());
            }

            @Override
            public void onSuccess(List<EmployeeDTO> result) {
                GWT.log("Thành công lấy danh sách người dùng :" + result);
                view.showEmployees(result);
            }
        });
    }

    //Logic cập nhật nhân viên 
    private void onBtnUpdateClicked(EmployeeDTO em){

        if (detailView.getFullName().isEmpty()) {
            Window.alert("tên nhân viên không được để trống nhé");
        }
        if (detailView.getRole().isEmpty()) {
            Window.alert("Quyền truy cập không được để trống");
        }

        em.setFullName(detailView.getFullName());
        em.setRole(detailView.getRole());
        // call service để lấy ra nhân viên tương ứng và set dữ liệu mới cho nhân viên đấy
        clientFactory.getEmployeeService().updateEm(em, em.getEmployeeId(), new AsyncCallback<EmployeeDTO>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Có lỗi khi cập nhật thông tin nhân viên :" + caught.getMessage());
            }

            @Override
            public void onSuccess(EmployeeDTO result) {
                Window.alert("Cập nhật thông tin nhân viên thành công");
                // đóng popup
                detailView.hide();
                loadEmployees();
            }
        });
    }

    // logic xóa nhân viên
    private void onBtnRemoveClicked(EmployeeDTO em){
        //call rpc
        if (Window.confirm("Xóa nhân viên " + em.getFullName() + " (ID: " + em.getEmployeeId() + ")?")) {
            clientFactory.getEmployeeService().deleteEm(em.getEmployeeId(), new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    Window.alert("Xóa thành công");
                    loadEmployees(); // reload bảng
                    eventBus.fireEvent(new EmployeeEvent(Action.DELETE, em)); // optional
                }
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Lỗi xóa: " + caught.getMessage());
                }
            });
        }
    }
}
