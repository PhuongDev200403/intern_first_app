package com.phuong_coi.english.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.presenter.UserDetailPresenter;
import com.phuong_coi.english.presenter.UserListPresenter;;

public class UserDetailView implements UserDetailPresenter.DetailPopup {
    interface UserDetailBinder extends UiBinder<Widget, UserDetailView>{};
    private UserDetailBinder userDetailBinder = GWT.create(UserDetailBinder.class);

    private UserListPresenter userListPresenter;

    private PopupPanel popup;
    @UiField Button btnClose;
    @UiField Button btnUpdate;
    @UiField DateBox dateBox;
    @UiField TextBox txtFullName;
    @UiField TextBox txtSoDienThoai;
    @UiField ListBox lbDepartment;
    @UiField ListBox lbRole;

    private final CwConstantsList constants = GWT.create(CwConstantsList.class);

    public UserDetailView() {
        
        Widget content = userDetailBinder.createAndBindUi(this);

        popup = new PopupPanel(true, true);
        popup.setGlassEnabled(true);
        popup.addStyleName("shadow-lg");
        popup.setWidget(content);
        popup.hide();

        dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));

        for (String d : constants.cwConstantsDepartment()) lbDepartment.addItem(d);
        for (String r : constants.cwConstantsRole()) lbRole.addItem(r);
        txtSoDienThoai.setEnabled(false);
    }

    public void setUserListPresenter(UserListPresenter userListPresenter){
        this.userListPresenter = userListPresenter;
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
    public String getFullName() {
        return txtFullName.getText();
    }

    @Override
    public void setSoDienThoai(String soDienThoai) {
        txtSoDienThoai.setText(soDienThoai);
    }

    @Override
    public String getSoDienThoai() {
        return txtSoDienThoai.getText();
    }

    @Override
    public void setPhongBan(String phongBan) {
        for (int i = 0; i < lbDepartment.getItemCount(); i++) {
            if (lbDepartment.getItemText(i).equals(phongBan)) {
                lbDepartment.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    @Override
    public String getPhongBan() {
        int idx = lbDepartment.getSelectedIndex();
        return idx >= 0 ? lbDepartment.getItemText(idx) : "";
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
    public String getChucVu() {
        int idx = lbRole.getSelectedIndex();
        return idx >= 0 ? lbRole.getItemText(idx) : "";
    }

    @Override
    public void setNgayVao(Date ngayVao) {
        dateBox.setValue(ngayVao);
    }

    @Override
    public Date getNgayVao() {
        return dateBox.getValue();
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

    @Override
    public HasClickHandlers getUpdateButton() {
        return btnUpdate;
    }

    @Override
    public void showMessage(String message) {
        Window.alert(message);
    }

}
