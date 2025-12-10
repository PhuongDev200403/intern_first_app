package com.phuong_coi.english.view;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.event.AppEventBus;
import com.phuong_coi.english.event.UserAddedEvent;
import com.phuong_coi.english.event.UserSelectedEvent;
import com.phuong_coi.english.event.UserUpdatedEvent;
import com.phuong_coi.english.model.UserDTO;

public class UserListView extends Composite {

    interface MyUiBinder extends UiBinder<Widget, UserListView> {
    }

    private MyUiBinder binder = GWT.create(MyUiBinder.class);
    private static final DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");

    // private UserServiceAsync userService = GWT.create(UserService.class);

    @UiField
    FlexTable table;

    // private FormPresenter formPresenter;
    //private UserDetailPresenter detailPresenter;
    private int rowIndex;

    public UserListView() {
        initWidget(binder.createAndBindUi(this));

        // Header
        table.setText(0, 0, "ID");
        table.setText(0, 1, "Họ tên");
        table.setText(0, 2, "Số ĐT");
        table.setText(0, 3, "Phòng ban");
        table.setText(0, 4, "Chức vụ");
        table.setText(0, 5, "Ngày gia nhập");
        // table.setText(0, 6, "Thao tác");

        // Click handler đúng 100% cho FlexTable
        table.addClickHandler(event -> {

            HTMLTable.Cell cell = table.getCellForEvent(event);
            if (cell == null || cell.getRowIndex() <= 0)
                return;

            int row = cell.getRowIndex();

            rowIndex = row;

            UserDTO user = (UserDTO) table.getRowFormatter()
                    .getElement(row)
                    .getPropertyObject("userData");

            if (user != null) {
                AppEventBus.get().fireEvent(new UserSelectedEvent(user));
                GWT.log("Phát tín hiệu click vào một user nào đó tại dòng :" + rowIndex);
            }
        });

        AppEventBus.get().addHandler(UserAddedEvent.TYPE, event -> {
            UserDTO userDTO = event.getUser();
            GWT.log("lắng nghe sự kiện tạo một user mới tại userlistView.java");
            addUserToTable(userDTO);
        });

        AppEventBus.get().addHandler(UserUpdatedEvent.TYPE, event -> {
            GWT.log("Bắt tin hiệu cập nhật user tại userListView.java");

            UserDTO userDTO = event.gUserDTO();
            updateARow(rowIndex, userDTO);
        });
    }

    public void showUsers(List<UserDTO> employeeDTOs) {
        // Xóa dữ liệu cũ (giữ header)
        while (table.getRowCount() > 1) {
            table.removeRow(1);
        }

        int row = 1;
        for (UserDTO u : employeeDTOs) {
            table.setText(row, 0, u.getId().toString());
            table.setText(row, 1, u.getFullName() != null ? u.getFullName() : "");
            table.setText(row, 2, u.getSoDienThoai() != null ? u.getSoDienThoai() : "");
            table.setText(row, 3, u.getPhongBan() != null ? u.getPhongBan() : "");
            table.setText(row, 4, u.getChucVu() != null ? u.getChucVu() : "");
            table.setText(row, 5, u.getNgayVao() != null ? fmt.format(u.getNgayVao()) : "");

            // Lưu user + style
            table.getRowFormatter().getElement(row).setPropertyObject("userData", u);
            table.getRowFormatter().addStyleName(row, "clickable-row cursor-pointer");

            row++;
        }
    }

    public void addUserToTable(UserDTO userDTO) {
        int row = table.getRowCount();
        table.setText(row, 0, userDTO.getId().toString());
        table.setText(row, 1, userDTO.getFullName());
        table.setText(row, 2, userDTO.getSoDienThoai());
        table.setText(row, 3, userDTO.getPhongBan());
        table.setText(row, 4, userDTO.getChucVu());
        table.setText(row, 5, userDTO.getNgayVao() != null ? fmt.format(userDTO.getNgayVao()) : "");

        table.getRowFormatter().getElement(row).setPropertyObject("userData", userDTO);
        table.getRowFormatter().addStyleName(row, "clickable-row cursor-pointer");
    }

    public void updateARow(int row, UserDTO userDTO) {
        table.setText(row, 0, userDTO.getId().toString());
        table.setText(row, 1, userDTO.getFullName());
        table.setText(row, 2, userDTO.getSoDienThoai());
        table.setText(row, 3, userDTO.getPhongBan());
        table.setText(row, 4, userDTO.getChucVu());
        table.setText(row, 5, userDTO.getNgayVao() != null ? fmt.format(userDTO.getNgayVao()) : "");

        table.getRowFormatter().getElement(row).setPropertyObject("userData", userDTO);
        table.getRowFormatter().addStyleName(row, "clickable-row cursor-pointer");
    }

    // public void setFormPresenter(FormPresenter p) { this.formPresenter = p; }
    // public void setDetailPopupPresenter(UserDetailPresenter p) {
    //     this.detailPresenter = p;
    // }

    public Widget asWidget() {
        return this;
    }

}
