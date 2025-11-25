package com.phuong_coi.english.view;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.presenter.FormPresenter;
import com.phuong_coi.english.presenter.UserDetailPresenter;
import com.google.gwt.user.client.History;

public class UserListView extends Composite {

    private VerticalPanel main = new VerticalPanel();
    private FlexTable table = new FlexTable();
    private FormPresenter formPresenter;
    private UserDetailPresenter detailPresenter;

    public UserListView() {
        main.setSpacing(10);
        table.setCellSpacing(10);
        table.addStyleName("table table-striped table-hover table-bordered shadow-sm");

        // Header
        table.setText(0, 0, "Họ tên");
        table.setText(0, 1, "Số ĐT");
        table.setText(0, 2, "Phòng ban");
        table.setText(0, 3, "Chức vụ");
        table.setText(0, 4, "Ngày gia nhập");

        main.add(table);
        initWidget(main);
    }

    public void showUsers(List<User> users) {
        // Xóa dữ liệu cũ
        for (int i = table.getRowCount() - 1; i > 0; i--) {
            table.removeRow(i);
        }
    
        DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
        int row = 1;
    
        table.addClickHandler(event -> {
            int clickedRow = table.getCellForEvent(event).getRowIndex();
            if (clickedRow > 0 && clickedRow < table.getRowCount()) { 
                User selected = users.get(clickedRow - 1);
                // if (detailPresenter != null) {
                //     detailPresenter.showDetail(selected);
                // }
                formPresenter.setUserForEdit(selected);
                History.newItem("add-user");
            }
        });
    
        for (User u : users) {
            table.setText(row, 0, u.getFullName());
            table.setText(row, 1, u.getSoDienThoai());
            table.setText(row, 2, u.getPhongBan());
            table.setText(row, 3, u.getChucVu());
            table.setText(row, 4, u.getNgayVao() != null ? fmt.format(u.getNgayVao()) : "");
    
            table.getRowFormatter().addStyleName(row, "clickable-row cursor-pointer");
            row++;
        }
    }

    public void setFormPresenter(FormPresenter presenter) {
        this.formPresenter = presenter;
    }

    public void setDetailPopupPresenter(UserDetailPresenter detailPresenter){
        this.detailPresenter = detailPresenter;
    }

    public Widget asWidget() {
        return this;
    }
}
