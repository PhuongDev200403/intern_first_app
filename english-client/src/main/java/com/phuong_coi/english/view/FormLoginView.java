package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FormLoginView extends Composite{
    interface FormLoginUiBinder extends UiBinder<Widget, FormLoginView>{};
    private FormLoginUiBinder formLoginUiBinder = GWT.create(FormLoginUiBinder.class);

    @UiField TextBox txtEmail;
    @UiField PasswordTextBox txtPassword;
    @UiField Button btnLogin;
    @UiField Button btnRegister;
    @UiField Anchor linkRegister;

    public FormLoginView(){
        initWidget(formLoginUiBinder.createAndBindUi(this));
    }

    // lần lượt là các hành vi khác của form login để activities có thể gọi
    public void showMessageError(String message){
        Window.alert(message);
    }

    public HasClickHandlers getBtnLogin(){
        return btnLogin;
    }

    public HasClickHandlers getBtnRegister(){
        return btnRegister;
    }

    public String getEmail(){
        return txtEmail.getText().trim();
    }

    public String getPassword(){
        return txtPassword.getText().trim();
    }

    public void setEmail(String email){
        this.txtEmail.setText(email);
    }

    public void setPassword(String password){
        this.txtPassword.setText(password);
    }

    public void clearForm(){
        txtEmail.setText("");
        txtPassword.setText("");
        txtEmail.setFocus(true);
    }
}
