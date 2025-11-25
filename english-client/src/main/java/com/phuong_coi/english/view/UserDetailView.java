package com.phuong_coi.english.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.presenter.UserDetailPresenter;;

public class UserDetailView implements UserDetailPresenter.DetailPopup {
    private final PopupPanel popup = new PopupPanel(true, true); // autoHide + modal
    private final Button btnClose = new Button("Đóng");
    private final DateBox dateBox = new DateBox();

    private final TextBox txtFullName = new TextBox();
    private final TextBox txtSoDienThoai = new TextBox();
    private final ListBox lbPhongBan = new ListBox(false);
    private final ListBox lbRole = new ListBox(false);

    private final CwConstantsList constants = GWT.create(CwConstantsList.class);

    public UserDetailView() {
        popup.setGlassEnabled(true); // nền mờ
        popup.addStyleName("shadow-lg rounded");

        VerticalPanel content = new VerticalPanel();
        content.setSpacing(15);
        content.addStyleName("p-4 bg-white");

        HTML title = new HTML("<h4 class='text-center'>Chi tiết người dùng</h4>");
        content.add(title);

        FlexTable table = new FlexTable();
        table.setCellSpacing(10);

        table.setWidget(0, 0, new Label("Họ tên"));
        table.setWidget(0, 1, txtFullName);

        table.setWidget(1, 0, new Label("Số điện thoại"));
        table.setWidget(1, 1, txtSoDienThoai);

        table.setWidget(2, 0, new Label("Phòng ban"));
        table.setWidget(2, 1, lbPhongBan);

        table.setWidget(3, 0, new Label("Chức vụ"));
        table.setWidget(3, 1, lbRole);

        table.setWidget(4, 0, new Label("Ngày vào"));
        dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        dateBox.setEnabled(false);
        table.setWidget(4, 1, dateBox);

        content.add(table);

        HorizontalPanel buttons = new HorizontalPanel();
        buttons.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        buttons.setSpacing(10);
        btnClose.addStyleName("btn btn-secondary");
        buttons.add(btnClose);
        content.add(buttons);

        popup.setWidget(content);

        for (String dept : constants.cwConstantsDepartment()) {
            lbPhongBan.addItem(dept);
        }
        for (String role : constants.cwConstantsRole()) {
            lbRole.addItem(role);
        }

        // Disable để chỉ xem
        txtFullName.setEnabled(false);
        txtFullName.addStyleName("form-control");
        txtSoDienThoai.setEnabled(false);
        txtSoDienThoai.addStyleName("form-control");
        lbPhongBan.setEnabled(false);
        lbPhongBan.addStyleName("form-select");
        lbRole.setEnabled(false);
        lbRole.addStyleName("form-select");

        popup.hide();
    }

    @Override
    public HasClickHandlers getCloseButton() {
        return btnClose;
    }

    @Override
    public void setFullName(String fullName) {
        txtFullName.setText(fullName);
    }

    @Override
    public void setSoDienThoai(String soDienThoai) {
        txtSoDienThoai.setText(soDienThoai);
    }

    @Override
    public void setPhongBan(String phongBan) {
        for (int i = 0; i < lbPhongBan.getItemCount(); i++) {
            if (lbPhongBan.getItemText(i).equals(phongBan)) {
                lbPhongBan.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    @Override
    public void setChucVu(String chucVu) {
        for (int i = 0; i < lbRole.getItemCount(); i++) {
            if (lbRole.getItemText(i).equals(chucVu)) {
                lbRole.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    @Override
    public void setNgayVao(Date ngayVao) {
        dateBox.setValue(ngayVao);
    }

    @Override
    public void show() {
        popup.show();
    }

    @Override
    public void hide() {
        popup.hide();
    }

    @Override
    public void center() {
        popup.center();
    }

}
