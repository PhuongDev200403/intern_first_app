package com.phuong_coi.english.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.phuong_coi.english.event.EmployeeEvent;
import com.phuong_coi.english.event.EmployeeEvent.Action;
import com.phuong_coi.english.model.EmployeeDTO;

public class EmployeeTableView extends Composite {
    interface EmployeeTableViewUiBinder extends UiBinder<Widget, EmployeeTableView> {
    }

    private static EmployeeTableViewUiBinder uiBinder = GWT.create(EmployeeTableViewUiBinder.class);

    @UiField CellTable<EmployeeDTO> cellTable;
    @UiField TextBox txtSearch , txtSearchEmail, txtSearchPhoneNumber;
    @UiField ListBox filterByRole;
    @UiField ListBox sortByName;
    @UiField Button btnDeleteSelected;
    @UiField HTMLPanel emptyMessagePanel;
    @UiField SimplePager pager;

    private EventBus eventBus;
    private ListDataProvider<EmployeeDTO> dataProvider;
    private MultiSelectionModel<EmployeeDTO> selectionModel;

    public EmployeeTableView() {
        initWidget(uiBinder.createAndBindUi(this));

        dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(cellTable);
        
        pager.setPageStart(0);
        pager.setPageSize(5);
        pager.setDisplay(cellTable);

        selectionModel = new MultiSelectionModel<>();
        cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<EmployeeDTO>createCheckboxManager());

        initColumns();
        setupUI();
        btnDeleteSelected.setVisible(false);

        // Listen to selection changes
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                updateDeleteButtonVisibility();
            }
        });
    }

    private void initColumns() {
        cellTable.setWidth("100%");
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);
        // Cột checkbox
        Column<EmployeeDTO, Boolean> checkboxColumn = new Column<EmployeeDTO, Boolean>(new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(EmployeeDTO object) {
                return selectionModel.isSelected(object);
            }
        };
        cellTable.addColumn(checkboxColumn, "");
        cellTable.setColumnWidth(checkboxColumn, "12.5%");

        // Cột mã nhân viên
        TextColumn<EmployeeDTO> idColumn = new TextColumn<EmployeeDTO>() {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getEmployeeId().toString();
            }
        };
        cellTable.addColumn(idColumn, "ID");
        cellTable.setColumnWidth(idColumn, "6%");

        // Cột fullName
        TextColumn<EmployeeDTO> nameColumn = new TextColumn<EmployeeDTO>() {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getFullName();
            }
        };
        cellTable.addColumn(nameColumn, "Họ tên");
        cellTable.setColumnWidth(nameColumn, "19%");

        // Côt email
        TextColumn<EmployeeDTO> emailColumn = new TextColumn<EmployeeDTO>() {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getEmail();
            }
        };
        cellTable.addColumn(emailColumn, "Email");
        cellTable.setColumnWidth(emailColumn, "12.5%");

        //Cột số điện thoại
        TextColumn<EmployeeDTO> phoneColumn = new TextColumn<EmployeeDTO>() {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getPhoneNumber();
            }
        };
        cellTable.addColumn(phoneColumn, "Số điện thoại");
        cellTable.setColumnWidth(phoneColumn, "12.5%");

        // Role column
        TextColumn<EmployeeDTO> roleColumn = new TextColumn<EmployeeDTO>() {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getRole();
            }
        };
        cellTable.addColumn(roleColumn, "Quyền truy cập");
        cellTable.setColumnWidth(roleColumn, "12.5%");

        // Update button column
        Column<EmployeeDTO, String> updateColumn = new Column<EmployeeDTO, String>(new ButtonCell()) {
            @Override
            public String getValue(EmployeeDTO object) {
                return "Cập nhật";
            }
        };
        updateColumn.setFieldUpdater(new FieldUpdater<EmployeeDTO, String>() {
            @Override
            public void update(int index, EmployeeDTO object, String value) {
                fireEvent(new EmployeeEvent(EmployeeEvent.Action.CLICK, object));
            }
        });
        cellTable.addColumn(updateColumn, "Cập nhật");
        cellTable.setColumnWidth(updateColumn, "12.5%");

        // Delete button column
        Column<EmployeeDTO, String> deleteColumn = new Column<EmployeeDTO, String>(new ButtonCell()) {
            @Override
            public String getValue(EmployeeDTO object) {
                return "Xóa";
            }
        };
        deleteColumn.setFieldUpdater(new FieldUpdater<EmployeeDTO, String>() {
            @Override
            public void update(int index, EmployeeDTO object, String value) {
                fireEvent(new EmployeeEvent(EmployeeEvent.Action.DELETE, object));
            }
        });
        cellTable.addColumn(deleteColumn, "Xóa");
        cellTable.setColumnWidth(deleteColumn, "12.5%");
    }

    private void setupUI() {
        txtSearch.getElement().setAttribute("placeholder", "Tìm kiếm theo tên nhân viên...");
        txtSearchEmail.getElement().setAttribute("placeholder", "Tìm kiếm theo email của nhân viên...");
        txtSearchPhoneNumber.getElement().setAttribute("placeholder", "Tìm kiếm theo số điện thoại nhân viên...");

        txtSearch.addKeyDownHandler(event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                performSearch();
                event.preventDefault();
            }
        });

        txtSearch.addKeyUpHandler(event -> {
            performSearch();
        });

        txtSearchEmail.addKeyDownHandler(event -> {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                performSearchEmail();
                event.preventDefault();
            }
        });

        txtSearchPhoneNumber.addKeyDownHandler(event -> {
            if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                performSearchPhoneNumber();
                event.preventDefault();
            }
        });

        String[] roles = { "All", "ADMIN", "USER" };
        for (String role : roles) {
            filterByRole.addItem(role);
        }
        filterByRole.setSelectedIndex(0);
        filterByRole.addChangeHandler(event -> performFilter());

        String[] sorts = { "All", "Tăng dần", "Giảm dần" };
        for (String string : sorts) {
            sortByName.addItem(string);
        }
        sortByName.setSelectedIndex(0);
        sortByName.addChangeHandler(event -> performSort());

        btnDeleteSelected.addClickHandler(event -> deleteSelectedEmployees());
    }

    public void showEmployees(List<EmployeeDTO> employees) {
        if (employees == null || employees.isEmpty()) {
            // Show empty message
            cellTable.removeStyleName("d-block");
            cellTable.addStyleName("d-none");
            emptyMessagePanel.removeStyleName("d-none");
            emptyMessagePanel.addStyleName("d-block");
            GWT.log("Danh sách rỗng nên hiển thị empty message panel");
        } else {
            // Show table
            cellTable.removeStyleName("d-none");
            cellTable.addStyleName("d-block");
            emptyMessagePanel.removeStyleName("d-block");
            emptyMessagePanel.addStyleName("d-none");
            GWT.log("Danh sách có nhân viên nên hiển thị bảng");

            dataProvider.setList(employees != null ? employees : new ArrayList<>());

            selectionModel.clear();
            updateDeleteButtonVisibility();
        }

    }

    private void performSearchPhoneNumber(){
        String keyword = txtSearchPhoneNumber.getText().trim();
        fireEvent(new EmployeeEvent(Action.SEARCH_PHONE_NUMBER, keyword));
    }

    private void updateDeleteButtonVisibility() {
        Set<EmployeeDTO> selected = selectionModel.getSelectedSet();
        btnDeleteSelected.setVisible(!selected.isEmpty());

        if (!selected.isEmpty()) {
            btnDeleteSelected.setText("Xóa đã chọn (" + selected.size() + ")");
        }
    }

    private void deleteSelectedEmployees() {
        Set<EmployeeDTO> selected = selectionModel.getSelectedSet();
        if (selected.isEmpty()) {
            return;
        }

        StringBuilder message = new StringBuilder("Bạn có chắc chắn muốn xóa ");
        message.append(selected.size()).append(" nhân viên đã chọn?\n\n");

        for (EmployeeDTO emp : selected) {
            message.append("- ").append(emp.getFullName()).append(" (ID: ").append(emp.getEmployeeId()).append(")\n");
        }

        if (Window.confirm(message.toString())) {
            fireEvent(new EmployeeEvent(EmployeeEvent.Action.DELETE_MULTIPLE, new ArrayList<>(selected)));
        }
    }

    private void performSearch() {
        String keyword = txtSearch.getText().trim();
        GWT.log("fire event thành công khi thay đổi trạng thái ô TextBox tìm kiếm");
        fireEvent(new EmployeeEvent(EmployeeEvent.Action.SEARCH, keyword));
    }

    private void performSort() {
        String keyword = sortByName.getSelectedValue();
        GWT.log("fire event thành công khi thay đổi trạng thái của listBox sắp xếp theo tên");
        fireEvent(new EmployeeEvent(Action.SORT, keyword));
    }

    private void performFilter() {
        String keyword = filterByRole.getSelectedValue();
        GWT.log("fire event thành công khi thay đổi trang thái của listBox lọc theo quyền truy cập");
        fireEvent(new EmployeeEvent(EmployeeEvent.Action.FILTER, keyword));
    }

    private void performSearchEmail(){
        String keyword = txtSearchEmail.getText().trim();
        fireEvent(new EmployeeEvent(Action.SEARCH_EMAIL, keyword));
    }

    private void fireEvent(EmployeeEvent event) {
        if (eventBus != null) {
            eventBus.fireEvent(event);
        }
    }

    public void showMessage(String message) {
        Window.alert(message);
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public String getSearch() {
        return txtSearch.getText().trim();
    }

    public void setSearch(String keyword) {
        txtSearch.setText(keyword != null ? keyword : "");
    }

    public String getValueInListBox() {
        return filterByRole.getSelectedItemText();
    }

    public String getValueInSort() {
        return sortByName.getSelectedItemText();
    }

    public String getSearchEmail(){
        return txtSearchEmail.getText().trim();
    }

    public String getSearchPhoneNumber(){
        return txtSearchPhoneNumber.getText().trim();
    }

    public void setSearchEmail(String keyword){
        txtSearchEmail.setText(keyword != null ? keyword : "");
    }

    public void setSearchPhoneNumber(String keyword){
        txtSearchPhoneNumber.setText(keyword != null ? keyword : "");
    }

}