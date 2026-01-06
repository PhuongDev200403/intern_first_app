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
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;
import com.phuong_coi.english.view.EmployeeDetail;
import com.phuong_coi.english.view.EmployeeTableView;

public class ListActivity extends AbstractActivity{
    private ClientFactory clientFactory;
    private Place place;
    private EventBus eventBus;

    // OLD LOGIC - COMMENTED OUT
    // private TableView view;
    private EmployeeTableView view; // NEW: Using CellTable
    private EmployeeDetail detailView;

    public ListActivity(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }
    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        // OLD LOGIC - COMMENTED OUT
        // view = clientFactory.getTableView();
        view = clientFactory.getEmployeeTableView(); // NEW: Using CellTable
        this.eventBus = eventBus;
        detailView = clientFactory.getEmployeeDetail();
        view.setEventBus(eventBus);
        panel.setWidget(view.asWidget());

        loadEmployees();

        detailView.getBtnUpdate().addClickHandler(c -> {
            EmployeeDTO employeeDTO = detailView.getDataFromForm();
            onBtnUpdateClicked(employeeDTO);
        });

        eventBus.addHandler(EmployeeEvent.TYPE, event -> {
            switch (event.getAction()) {
                case CREATE:
                    GWT.log("Nhân viên mới được tạo và reload bảng");
                    loadEmployees();
                    break;
                case CLICK:
                    detailView.showDetail(event.getEmployee());
                    GWT.log("Load dữ liệu bảng trước khi cập nhật dữ liệu mới");
                    break;
                case DELETE:
                    GWT.log("Nhân viên bị xóa và reload bảng");
                    onBtnRemoveClicked(event.getEmployee());
                    break;
                case DELETE_MULTIPLE: 
                    GWT.log("Xóa nhiều nhân viên");
                    onBtnRemoveMultipleClicked((List<EmployeeDTO>) event.getData());
                    break;
                case SEARCH:
                    GWT.log("Bắt được tín hiệu tìm kiếm nhân viên");
                    onSearch();
                    break;
                case UPDATE:
                    GWT.log("Bắt được tín hiệu cập nhật nhân viên và reload lại bảng");
                    loadEmployees();
                    break;
                case FILTER:
                    GWT.log("Bắt được tín hiệu lọc nhân viên theo quyền truy cập");
                    onFilterByRole();
                    break;
                case SORT:
                    GWT.log("Bắt được tin hiệu sắp xếp nhân viên");
                    onSortByName();
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
            return;
        }
        if (detailView.getRole().isEmpty()) {
            Window.alert("Quyền truy cập không được để trống");
            return;
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
                //fire event 
                eventBus.fireEvent(new EmployeeEvent(Action.UPDATE, result));
                GWT.log("Cập nhật dữ liệu sau khi đã cập nhật thành công nhân viên");
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
                }
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Lỗi xóa: " + caught.getMessage());
                }
            });
        }
    }

    //logic cho bộ lọc nhân viên
    //Tìm kiếm nhân viên theo tên, theo email, theo số điện thoại
    private void onSearch(){
        String keyword = view.getSearch();

        if (keyword.isEmpty()) {
            loadEmployees();
            return;
        }
        //Call RPC 
        clientFactory.getEmployeeService().search(keyword, new AsyncCallback<List<EmployeeDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Có lỗi xảy ra trong quá trình tìm kiếm");
            }

            @Override
            public void onSuccess(List<EmployeeDTO> result) {
                GWT.log("Thành công tìm kiếm từ khóa :" + keyword);
                view.showEmployees(result);
            }
            
        });
    }

    private void onBtnRemoveMultipleClicked(List<EmployeeDTO> employees) {
        if (employees == null || employees.isEmpty()) {
            return;
        }

        // Delete each employee
        for (EmployeeDTO emp : employees) {
            clientFactory.getEmployeeService().deleteEm(emp.getEmployeeId(), new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    GWT.log("Xóa thành công nhân viên: " + emp.getFullName());
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    GWT.log("Lỗi xóa nhân viên " + emp.getFullName() + ": " + caught.getMessage());
                }
            });
        }
        
        com.google.gwt.user.client.Timer timer = new com.google.gwt.user.client.Timer() {
            @Override
            public void run() {
                loadEmployees();
                Window.alert("Đã xóa " + employees.size() + " nhân viên thành công!");
            }
        };
        timer.schedule(1000); // 1 second delay
    }

    public void onFilterByRole(){
        String keyword = view.getValueInListBox();

        GWT.log("Lọc theo :" + keyword);
        
        if(keyword == null || keyword.isEmpty()){
            loadEmployees();
        }

        //Call rpc
        clientFactory.getEmployeeService().getListByRole(keyword, new AsyncCallback<List<EmployeeDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Lỗi khi lọc nhân viên theo quyền truy cập");
            }

            @Override
            public void onSuccess(List<EmployeeDTO> result) {
                GWT.log("Lọc thành công :" + result);
                view.showEmployees(result);
            }
            
        });
    }

    private void onSortByName(){
        String keyword = view.getValueInSort();

        GWT.log("Sort theo :" + keyword);

        if (keyword == null) {
            loadEmployees();
        }

        //Call rpc
        clientFactory.getEmployeeService().sortByName(keyword, new AsyncCallback<List<EmployeeDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                view.showMessage("Có lỗi xảy ra khi sắp xếp :" + keyword + " " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<EmployeeDTO> result) {
                GWT.log("Sắp xếp "+ keyword + " thành công");
                view.showEmployees(result);
            }
        });
    }
}
