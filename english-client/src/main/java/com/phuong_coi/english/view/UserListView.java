package com.phuong_coi.english.view;

import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.model.User;
import com.google.gwt.user.client.History;

public class UserListView extends Composite {

    private VerticalPanel main = new VerticalPanel();
    private FlexTable table = new FlexTable();
    private CwFormView formView;

    public UserListView() {
        main.setSpacing(10);
        table.setCellSpacing(10);
        table.addStyleName("table table-striped table-hover table-bordered shadow-sm");

        // header
        table.setText(0, 0, "Họ tên");
        table.setText(0, 1, "Số ĐT");
        table.setText(0, 2, "Phòng ban");
        table.setText(0, 3, "Chức vụ");
        table.setText(0, 4, "Ngày vào");

        main.add(table);
        initWidget(main);
    }

    public void showUsers(List<User> users) {

        int rows = table.getRowCount();
        for (int i = rows - 1; i > 0; i--) {
            table.removeRow(i);
        }

        int row = 1;
        DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");

        for (User u : users) {
            final int currentRow = row;
            final User currentUser = u;
            
            table.setText(row, 0, u.getFullName());
            table.setText(row, 1, u.getSoDienThoai());
            table.setText(row, 2, u.getPhongBan());
            table.setText(row, 3, u.getChucVu());

            String dateStr = u.getNgayVao() != null
                    ? fmt.format(u.getNgayVao())
                    : "";
            table.setText(row, 4, dateStr);
            
            // Thêm click handler cho row
            table.getRowFormatter().addStyleName(currentRow, "clickable-row");
            for (int col = 0; col < 5; col++) {
                final int column = col;
                table.addClickHandler(event -> {
                    int cellIndex = table.getCellForEvent(event).getCellIndex();
                    int rowIndex = table.getCellForEvent(event).getRowIndex();
                    
                    if (rowIndex == currentRow && formView != null) {
                        formView.fillUserData(currentUser);
                        History.newItem("add-user");
                    }
                });
            }

            row++;
        }
    }

    public void setFormView(CwFormView formView) {
        this.formView = formView;
    }

    public Widget asWidget() {
        return this;
    }
}
