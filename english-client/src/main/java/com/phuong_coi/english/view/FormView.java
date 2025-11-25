package com.phuong_coi.english.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.presenter.FormPresenter;

public class FormView extends Composite implements FormPresenter.Display {

    private final CwConstantsList constants = GWT.create(CwConstantsList.class);

    private TextBox txtFullName = new TextBox();
    private TextBox txtSoDienThoai = new TextBox();
    private ListBox lbDepartment = new ListBox(false);
    private ListBox lbRole = new ListBox(false);
    private DateBox dateBox = new DateBox();
    private Button btnAdd = new Button("Thêm mới");
    private Button btnUpdate = new Button("Cập nhật");

    public FormView() {
        FlexTable layout = createLayout();
        DecoratorPanel panel = new DecoratorPanel();
        panel.setWidth("23%");
        panel.addStyleName("bg-light");
        panel.add(layout);
        initWidget(panel);
    }

    private FlexTable createLayout() {
        FlexTable table = new FlexTable();
        table.setCellSpacing(10);
        FlexCellFormatter fmt = table.getFlexCellFormatter();
        table.setSize("400px", "400px");

        table.setHTML(0, 0, "<h4 class='text-center'>Quản lý người dùng</h4>");
        fmt.setColSpan(0, 0, 2);
        fmt.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);


        table.setWidget(1, 0, new Label("Họ tên"));
        table.setWidget(1, 1, txtFullName);
        txtFullName.addStyleName("form-control");

        table.setWidget(2, 0, new Label("Số điện thoại"));
        table.setWidget(2, 1, txtSoDienThoai);
        txtSoDienThoai.addStyleName("form-control");

        table.setWidget(3, 0, new Label("Phòng ban"));
        table.setWidget(3, 1, lbDepartment);
        lbDepartment.addStyleName("form-select");

        table.setWidget(4, 0, new Label("Chức vụ"));
        table.setWidget(4, 1, lbRole);
        lbRole.addStyleName("form-select");

        table.setWidget(5, 0, new Label("Ngày gia nhập"));
        table.setWidget(5, 1, dateBox);
        dateBox.addStyleName("form-control");


        // Buttons
        table.setWidget(6, 0, btnAdd);
        table.setWidget(6, 1, btnUpdate);

        // Load data
        for (String d : constants.cwConstantsDepartment())
            lbDepartment.addItem(d);
        for (String r : constants.cwConstantsRole())
            lbRole.addItem(r);

        dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        dateBox.addStyleName("form-control");
        txtFullName.addStyleName("form-control");
        txtSoDienThoai.addStyleName("form-control");
        lbDepartment.addStyleName("form-select");
        lbRole.addStyleName("form-select");

        return table;
    }


    @Override
    public HasClickHandlers getAddButton() {
        return btnAdd;
    }

    @Override
    public HasClickHandlers getUpdateButton() {
        return btnUpdate;
    }

    @Override
    public String getFullName() {
        return txtFullName.getText().trim();
    }

    @Override
    public String getSoDienThoai() {
        return txtSoDienThoai.getText().trim();
    }

    @Override
    public String getPhongBan() {
        return lbDepartment.getSelectedValue();
    }

    @Override
    public String getChucVu() {
        return lbRole.getSelectedValue();
    }

    @Override
    public Date getNgayVao() {
        return dateBox.getValue();
    }

    @Override
    public void setPhoneNumberEnabled(boolean enabled) {
        txtSoDienThoai.setEnabled(enabled);
    }

    @Override
    public void fillUserData(User user) {
        if (user == null) {
            clearForm();
            return;
        }
        txtFullName.setText(user.getFullName() != null ? user.getFullName() : "");
        txtSoDienThoai.setText(user.getSoDienThoai() != null ? user.getSoDienThoai() : "");
        setSelected(lbDepartment, user.getPhongBan());
        setSelected(lbRole, user.getChucVu());
        dateBox.setValue(user.getNgayVao());
        txtSoDienThoai.setEnabled(false);
    }

    private void setSelected(ListBox lb, String value) {
        if (value == null) {
            lb.setSelectedIndex(0);
            return;
        }
        for (int i = 0; i < lb.getItemCount(); i++) {
            if (lb.getItemText(i).equals(value)) {
                lb.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public void clearForm() {
        txtFullName.setText("");
        txtSoDienThoai.setText("");
        txtSoDienThoai.setEnabled(true);
        lbDepartment.setSelectedIndex(0);
        lbRole.setSelectedIndex(0);
        dateBox.setValue(null);
    }

    @Override
    public void setFullName(String fullName) {
        txtFullName.setText(fullName != null ? fullName : "");
    }

    @Override
    public void setSoDienThoai(String soDienThoai) {
        txtSoDienThoai.setText(soDienThoai != null ? soDienThoai : "");
    }

    @Override
    public void setPhongBan(String phongBan) {
        for (int i = 0; i < lbDepartment.getItemCount(); i++) {
            if (lbDepartment.getItemText(i).equals(phongBan)) {
                lbDepartment.setSelectedIndex(i);
                return;
            }
        }
        // Nếu không tìm thấy thì để mặc định index 0
        lbDepartment.setSelectedIndex(0);
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
        dateBox.setValue(ngayVao); // mày đặt tên là dateBox hay checkInDate thì sửa cho khớp nhé
    }

    @Override
    public void showMessage(String message) {
        Window.alert(message);
    }
}
