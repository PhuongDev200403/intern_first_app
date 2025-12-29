package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.constants.CwConstantsList;

public class Form extends Composite{
    interface FormViewUiBinder extends UiBinder<Widget, Form>{};
    public FormViewUiBinder formViewUiBinder = GWT.create(FormViewUiBinder.class);

    private CwConstantsList cwConstantsList = GWT.create(CwConstantsList.class);

    @UiField TextBox txtFullName;
    @UiField TextBox txtEmail;
    @UiField TextBox txtSoDienThoai;
    @UiField TextBox txtPassword;
    @UiField ListBox lbRole;

    @UiField Button btnAdd;


    public Form (){
        initWidget(formViewUiBinder.createAndBindUi(this));

        for (String item : cwConstantsList.cwConstantsAuth()) {
            lbRole.addItem(item);
        }
    }
    // các phương thức khác trong form
    public HasClickHandlers getBtnAdd(){
        return btnAdd;
    }

    public String getFullName(){
        return txtFullName.getText().trim();
    }

    public String getSoDienThoai(){
        return txtSoDienThoai.getText().trim();
    }

    public String getEmail(){
        return txtEmail.getText().trim();
    }

    public String getPassword(){
        return txtPassword.getText().trim();
    }

    public String getRole(){
        return lbRole.getSelectedValue();
    }

    public void setRole(String role){
        for(int i = 0; i < lbRole.getItemCount(); i++){
            if(lbRole.getItemText(i).equals(role)){
                lbRole.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    public void setFullName(String fullName){
        txtFullName.setText( fullName == null ? "" : fullName);
    }

    public void setEmail(String email){
        txtEmail.setText( email == null ? "" : email);
    }

    public void setPassword(String password){
        txtPassword.setText( password == null ? "" : password);
    }

    public void setSoDienThoai(String soDienThoai){
        txtSoDienThoai.setText(soDienThoai == null ? "" : soDienThoai);
    }

    public void clearForm(){
        txtFullName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtSoDienThoai.setText("");
        lbRole.setSelectedIndex(0);
    }

    public void showMessage(String message){
        Window.alert(message);
    }
}
