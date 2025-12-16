package com.phuong_coi.english.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.model.UserDTO;
import com.phuong_coi.english.presenter.FormPresenter;

public class FormView extends Composite implements FormPresenter.Display {

    interface FormUiBinder extends UiBinder<Widget, FormView>{};
    private FormUiBinder formUiBinder = GWT.create(FormUiBinder.class);

    private final CwConstantsList constants = GWT.create(CwConstantsList.class);

    @UiField TextBox txtFullName;
    @UiField TextBox txtSoDienThoai;
    @UiField ListBox lbDepartment;
    @UiField ListBox lbRole;
    @UiField DateBox dateBox;
    @UiField Button btnAdd;
    //@UiField Button btnUpdate;

    public FormView() {

        initWidget(formUiBinder.createAndBindUi(this));

        // Cấu hình DateBox
        dateBox.setFormat(new DateBox.DefaultFormat(
            DateTimeFormat.getFormat("dd/MM/yyyy")));

        // Load dữ liệu cho ListBox
        for (String d : constants.cwConstantsDepartment())
            lbDepartment.addItem(d);
        for (String r : constants.cwConstantsRole())
            lbRole.addItem(r);
    }

    @Override
    public HasClickHandlers getAddButton() {
        return btnAdd;
    }

    // @Override
    // public HasClickHandlers getUpdateButton() {
    //     return btnUpdate;
    // }

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
    public void fillUserData(UserDTO user) {
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
