package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.constants.CwConstantsList;

public class FormRegisterView extends Composite {
    interface FormRegisterUiBinder extends UiBinder<Widget, FormRegisterView>{};
    public FormRegisterUiBinder formRegisterUiBinder = GWT.create(FormRegisterUiBinder.class);

    private final CwConstantsList cwConstantsList = GWT.create(CwConstantsList.class);

    @UiField TextBox txtFullName;
    @UiField TextBox txtEmail;
    @UiField PasswordTextBox txtPassword;
    @UiField Button btnRegister;
    @UiField TextBox txtPhoneNumber;
    @UiField ListBox lbRole = new ListBox(isVisible());

    public FormRegisterView(){
        initWidget(formRegisterUiBinder.createAndBindUi(this));

        //Set up cho list box
        String[] roles = cwConstantsList.cwConstantsAuth();
        for (String role : roles) {
            lbRole.addItem(role);
        }
    }

    //Các hành vi khác có trong form đăng ký để activities có thể gọi qua view
    //click nút đăng ký
    public HasClickHandlers getBtnRegister(){
        return btnRegister;
    }

    public void setFullName(String fullName){
        this.txtFullName.setText(fullName == null ? "" : fullName);
    }

    public void setPhoneNumber(String phoneNumber){
        this.txtPhoneNumber.setText(phoneNumber == null ? "" : phoneNumber);
    }

    public void setEmail(String email){
        this.txtEmail.setText(email == null ? "" : email);
    }

    public void setRole(String role){
        for (int i = 0; i < lbRole.getItemCount(); i++) {
            if (lbRole.getItemText(i).equals(role)) {
                lbRole.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    public void setPassword(String password){
        this.txtPassword.setText(password == null ? "" : password);
    }

    public String getFullName() {
        return txtFullName.getText().trim();
    }

    public String getPhoneNumber() {
        return txtPhoneNumber.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPassword() {
        return txtPassword.getText().trim();
    }

    public String getRole() {
        return lbRole.getSelectedValue();
    }

    public void clearForm() {
        txtFullName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPhoneNumber.setText("");
        lbRole.setSelectedIndex(0);
        txtFullName.setFocus(true); // focus vào field đầu
    }

    public void focusFullName() {
        txtFullName.setFocus(true);
    }

    public void showMessage(String message){
        Window.alert(message);
    }
}
