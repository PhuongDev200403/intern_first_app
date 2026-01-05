package com.phuong_coi.english.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;

public class TableView extends Composite {
    interface TableViewUiBinder extends UiBinder<Widget, TableView> {
    }

    private static TableViewUiBinder tableViewUiBinder = GWT.create(TableViewUiBinder.class);

    @UiField
    FlexTable table;
    @UiField
    TextBox txtSearch;
    @UiField
    HTMLPanel emptyMessagePanel;
    @UiField 
    ListBox filterByRole;
    @UiField
    ListBox sortByName;


    private EventBus eventBus;

    public TableView() {
        initWidget(tableViewUiBinder.createAndBindUi(this));

        // Thiết lập tiêu đề bảng
        table.setText(0, 0, "ID");
        table.setText(0, 1, "FullName");
        table.setText(0, 2, "Email");
        table.setText(0, 3, "Phone Number");
        table.setText(0, 4, "Role");
        table.setText(0, 5, "Action");

        String[] roles = {"All", "ADMIN", "USER"};
        for (String string : roles) {
            filterByRole.addItem(string);
        }
        filterByRole.setSelectedIndex(0);
        table.addStyleName("align-middle");
        txtSearch.getElement().setAttribute("placeholder", "Tìm kiếm theo tên, email, số điện thoại...");

        String[] keySorts = {"All","A - Z", "Z - A"};
        for (String string : keySorts) {
            sortByName.addItem(string);
        }
        sortByName.setSelectedIndex(0);

        txtSearch.addKeyDownHandler(event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                performedSearch();
                event.preventDefault();
            }
        });

        filterByRole.addChangeHandler(event ->{
            performedFilter();
        });

    }

    public void showEmployees(List<EmployeeDTO> employees) {

        if (employees == null || employees.isEmpty()) {
            // Ẩn table và hiển thị empty message
            table.removeStyleName("d-block");
            table.addStyleName("d-none");
            emptyMessagePanel.removeStyleName("d-none");
            emptyMessagePanel.addStyleName("d-block");
            GWT.log("Danh sách rỗng nên hiển thị empty message panel nhé");
        } else {

            while (table.getRowCount() > 1) {
                table.removeRow(1);
            }

            // Hiển thị table và ẩn empty message
            table.removeStyleName("d-none");
            table.addStyleName("d-block");
            emptyMessagePanel.removeStyleName("d-block");
            emptyMessagePanel.addStyleName("d-none");
            GWT.log("Danh sách có nhân viên nên hiển thị bảng");

            int row = 1;
            for (EmployeeDTO employeeDTO : employees) {
                table.setText(row, 0, employeeDTO.getEmployeeId().toString());
                table.setText(row, 1, employeeDTO.getFullName());
                table.setText(row, 2, employeeDTO.getEmail());
                table.setText(row, 3, employeeDTO.getPhoneNumber());
                table.setText(row, 4, employeeDTO.getRole());
                // Panel chứa nút hành động
                HorizontalPanel panel = new HorizontalPanel();
                panel.setSpacing(6); // khoảng cách giữa các nút

                Button btnRemove = new Button("Xóa");
                btnRemove.addStyleName("btn btn-danger btn-sm me-3");

                Button btnUpdate = new Button("Cập nhật");
                btnUpdate.addStyleName("btn btn-primary btn-sm");

                btnUpdate.addClickHandler(e -> fireEvent(new EmployeeEvent(EmployeeEvent.Action.CLICK, employeeDTO)));
                btnRemove.addClickHandler(e -> fireEvent(new EmployeeEvent(EmployeeEvent.Action.DELETE, employeeDTO)));

                panel.add(btnRemove);
                panel.add(btnUpdate);

                table.setWidget(row, 5, panel);
                row++;
            }
        }
    }

    public void showMessage(String message) {
        Window.alert(message);
    }

    private void fireEvent(EmployeeEvent event) {
        if (eventBus != null) {
            eventBus.fireEvent(event);
        }
    }

    public void performedSearch() {
        String keyword = txtSearch.getText().trim();

        if (eventBus != null) {
            eventBus.fireEvent(new EmployeeEvent(EmployeeEvent.Action.SEARCH, keyword));
        }
    }

    public void performedFilter(){
        String keyword = filterByRole.getSelectedValue();

        if (eventBus != null) {
            eventBus.fireEvent(new EmployeeEvent(EmployeeEvent.Action.FILTER, keyword));
        }
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setSearch(String keyword) {
        this.txtSearch.setText(keyword != null ? keyword : "");
    }

    public String getSearch() {
        return txtSearch.getText().trim();
    }

    public String getValueInListBox(){
        return filterByRole.getSelectedItemText();
    }

    public void performedSort(){
        String keyword = sortByName.getSelectedValue();

        if (eventBus != null) {
            eventBus.fireEvent(new EmployeeEvent(Action.FILTER, keyword));
        }
    }
}
