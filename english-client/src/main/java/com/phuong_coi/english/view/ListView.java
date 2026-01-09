package com.phuong_coi.english.view;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.phuong_coi.english.model.EmployeeDTO;

public class ListView extends Composite{
    interface CellTableViewUiBinder extends UiBinder<Widget, ListView>{}
    private static CellTableViewUiBinder cellTableViewUiBinder = GWT.create(CellTableViewUiBinder.class);

    @UiField 
    TextBox txtSearch;
    @UiField 
    ListBox filterByRole;
    @UiField 
    ListBox sortByName;
    @UiField (provided = true)
    CellTable<EmployeeDTO> cellTable;
    @UiField 
    SimplePager pager;
    @UiField 
    HTMLPanel emptyMessagePanel;

    private EventBus eventBus;
    private ListDataProvider<EmployeeDTO> dataProvider;
    private MultiSelectionModel<EmployeeDTO> selectionModel;

    private static final ProvidesKey<EmployeeDTO> KEY_PROVIDER = new ProvidesKey<EmployeeDTO>() {
        @Override
        public Object getKey(EmployeeDTO object) {
            return object == null ? null : object.getEmployeeId();
        }
    };

    public ListView(){
    selectionModel = new MultiSelectionModel<>(KEY_PROVIDER);
    cellTable = new CellTable<>(15, null, KEY_PROVIDER);
    cellTable.setSelectionModel(selectionModel);

    initWidget(cellTableViewUiBinder.createAndBindUi(this));
    cellTable.setWidth("100%", true);

    dataProvider = new ListDataProvider<>();
    dataProvider.addDataDisplay(cellTable);

    pager.setDisplay(cellTable);

    // Cấu hình ListBox
    String[] roles = {"All", "ADMIN", "USER"};
    for (String string : roles) {
        filterByRole.addItem(string);
    }

    String[] sort = {"All", "A - Z", "Z - A"};
    for (String string : sort) {
        sortByName.addItem(string);
    }

    txtSearch.getElement().setAttribute("placeholder", "Tìm kiếm theo tên, email, số điện thoại...");

    }

    private void initTableColumns(){
        //Cột checkbox
        Column<EmployeeDTO, Boolean> checkColumn = new Column<EmployeeDTO,Boolean>(
            new CheckboxCell(true, false)
        ) {
            @Override
            public Boolean getValue(EmployeeDTO object) {
                return selectionModel.isSelected(object);
            }
        };

        cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<input type='checkbox' class='select-all'/>"));
        cellTable.setColumnWidth(checkColumn, "15%");

        //Cột ID
        Column<EmployeeDTO, String> idColumn = new Column<EmployeeDTO, String>(new TextCell()) {
            @Override
            public String getValue(EmployeeDTO object) {
                return object.getEmployeeId() != null ? object.getEmployeeId().toString() : "";
            }
        };

        cellTable.addColumn(idColumn, "ID");
        cellTable.setColumnWidth(idColumn, "15%");

        //Cột họ tên
        Column<EmployeeDTO, String> fullNameColumn = new Column<EmployeeDTO,String>(new TextCell()) {

            @Override
            public String getValue(EmployeeDTO object) {
                return object.getFullName() == null ? " " : object.getFullName().toString();
            }
        };

        cellTable.addColumn(fullNameColumn, "Họ và tên");
        cellTable.setColumnWidth(fullNameColumn, "15%");

        // Cột email 
        Column<EmployeeDTO, String> emailColumn = new Column<EmployeeDTO,String>(new TextCell()) {

            @Override
            public String getValue(EmployeeDTO object) {
                return object.getEmail() == null ? " " : object.getEmail().toString();
            }
        };

        cellTable.addColumn(emailColumn, "email");
        cellTable.setColumnWidth(emailColumn, "20%");

        //Cột số điện thoại
        Column<EmployeeDTO, String> phoneColumn = new Column<EmployeeDTO,String>(new TextCell()) {

            @Override
            public String getValue(EmployeeDTO object) {
                return object.getPhoneNumber() == null ? " " : object.getPhoneNumber().toString();
            }
        };

        cellTable.addColumn(phoneColumn, "Số điện thoại");
        cellTable.setColumnWidth(phoneColumn, "15%");

        //Cột quyền truy cập
        Column<EmployeeDTO, String> roleColumn = new Column<EmployeeDTO,String>(new TextCell()) {

            @Override
            public String getValue(EmployeeDTO object) {
                return object.getRole() == null ? " " : object.getRole().toString();
            }
        };

        cellTable.addColumn(roleColumn, "Quyền ttruy cập");
        cellTable.setColumnWidth(roleColumn, "10%");

        //Cột hành động
        Column<EmployeeDTO, String> Column = new Column<EmployeeDTO,String>(new TextCell()) {

            @Override
            public String getValue(EmployeeDTO object) {
                return object.getFullName() == null ? " " : object.getFullName().toString();
            }
        };

        cellTable.addColumn(fullNameColumn, "Họ và tên");
        cellTable.setColumnWidth(fullNameColumn, "15%");
    }
}