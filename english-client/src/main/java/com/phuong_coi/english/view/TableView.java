package com.phuong_coi.english.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.model.EmployeeDTO;

public class TableView extends Composite{
    interface TableViewUiBinder extends UiBinder<Widget, TableView>{};
    private TableViewUiBinder tableViewUiBinder = GWT.create(TableViewUiBinder.class);

    @UiField FlexTable table;

    private EventBus eventBus;

    public TableView(){
        initWidget(tableViewUiBinder.createAndBindUi(this));

        //Thiết lập tiêu đề cho bảng nhân viên
        table.setText(0, 0, "ID");
        table.setText(0, 1, "FullName");
        table.setText(0, 2, "Email");
        table.setText(0, 3, "Phone Number");
        table.setText(0, 4, "Role");
        table.setText(0, 5, "Action");
    }

    // Các phương thức trong table
    public void showEmployees(List<EmployeeDTO> employees){

        while (table.getRowCount() > 1) {
            table.removeRow(1);
        }

        int row = 1; 
        for (EmployeeDTO employeeDTO : employees) {
            
            table.setText(row, 0, employeeDTO.getEmployeeId().toString());
            table.setText(row, 1, employeeDTO.getFullName());
            table.setText(row, 2, employeeDTO.getEmail());
            table.setText(row, 3, employeeDTO.getPhoneNumber());
            table.setText(row, 4, employeeDTO.getRole());

            //Tạo một panle chưa cả 3 button
            HorizontalPanel panel = new HorizontalPanel();
            Button btnRemove = new Button("Xóa");
            Button btnViewDetail = new Button("Chi tiết");
            Button btnUpdate = new Button("Cập nhật");

            btnViewDetail.addClickHandler(e -> fireEvent(new EmployeeEvent(EmployeeEvent.Action.VIEW, employeeDTO)));
            btnUpdate.addClickHandler(e -> fireEvent(new EmployeeEvent(EmployeeEvent.Action.VIEW, employeeDTO)));
            btnRemove.addClickHandler(e -> fireEvent(new EmployeeEvent(EmployeeEvent.Action.DELETE, employeeDTO)));

            panel.add(btnViewDetail);
            panel.add(btnRemove);
            panel.add(btnUpdate);
            table.setWidget(row, 5, panel);
            row++;
        }
    }

    public void showMessage(String message){
        Window.alert(message);
    }

    private void fireEvent(EmployeeEvent event){
        if (eventBus != null) {
            eventBus.fireEvent(event);
        }
    }

    public void setEventBus(EventBus eventBus){
        this.eventBus = eventBus;
    }

    // public HasClickHandlers getBtnViewDetail(){
    //     return btnViewDetail;
    // }

    // public HasClickHandlers getBtnUpdate(){
    //     return btnUpdate;
    // }

    // public HasClickHandlers getBtnRemove(){
    //     return btnRemove;
    // }
}
