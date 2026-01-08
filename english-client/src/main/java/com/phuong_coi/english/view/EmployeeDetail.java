package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.model.EmployeeDTO;

public class EmployeeDetail extends Composite{
    interface EmployeeDetailViewUiBinder extends UiBinder<Widget, EmployeeDetail>{};
    public EmployeeDetailViewUiBinder employeeDetailViewUiBinder = GWT.create(EmployeeDetailViewUiBinder.class);

    @UiField TextBox txtId;
    @UiField TextBox txtFullName;
    @UiField TextBox txtSoDienThoai;
    @UiField TextBox txtEmail;
    @UiField ListBox lbRole = new ListBox(false);
    @UiField Button btnClose;
    @UiField Button btnUpdate;

    private PopupPanel popup;
    private final CwConstantsList cwConstantsList = GWT.create(CwConstantsList.class);

    public EmployeeDetail(){
        Widget content = employeeDetailViewUiBinder.createAndBindUi(this);

        popup = new PopupPanel(true, true);
        popup.setGlassEnabled(true);
        popup.addStyleName("shadow-lg");
        popup.setWidget(content);
        hide();

        for(String item : cwConstantsList.cwConstantsAuth()){
            lbRole.addItem(item);
        }
        txtId.setEnabled(false);
        setEnablePhoneNumber();

        btnClose.addClickHandler(event ->{
            hide();
        });

    }

    //các phương thức có trong popup panel
    public void showPopup(){
        popup.show();
    }

    public void hide(){
        popup.hide();
    }

    public void centerPopup(){
        popup.center();
    }

    //các phương thức setEnable không cho cập nhật
    public void setEnablePhoneNumber(){
        txtSoDienThoai.setEnabled(false);
    }

    public void setEnableEmail(){
        txtEmail.setEnabled(false);
    }

    //các phương thức get set cho các trường dữ liệu
    public HasClickHandlers getBtnClose(){
        return btnClose;
    }

    public HasClickHandlers getBtnUpdate(){
        return btnUpdate;
    }

    public String getId(){
        return txtId.getText().trim();
    }

    public void setId(String id){
        txtId.setText(id);
    }

    public String getFullName(){
        return txtFullName.getText().trim();
    }
    public void setFullName(String fullName) {
        txtFullName.setText(fullName);
    }

    public String getSoDienThoai() {
        return txtSoDienThoai.getText().trim();
    }

    public void setSoDienThoai(String soDienThoai) {
        txtSoDienThoai.setText(soDienThoai);
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public void setEmail(String email) {
        txtEmail.setText(email);
    }

    public String getRole() {
        int idx = lbRole.getSelectedIndex();
        return idx >= 0 ? lbRole.getItemText(idx) : "";
    }

    public void setRole(String role) {
        for (int i = 0; i < lbRole.getItemCount(); i++) {
            if (lbRole.getItemText(i).equals(role)) {
                lbRole.setSelectedIndex(i);
                return;
            }
        }
        lbRole.setSelectedIndex(0);
    }

    //Phương thức show nhân viên 
    public void showDetail(EmployeeDTO employee){
        if(employee == null){
            return;
        }
        setId(employee.getEmployeeId().toString());
        setEmail(employee.getEmail());
        setFullName(employee.getFullName());
        setSoDienThoai(employee.getPhoneNumber());
        setRole(employee.getRole());

        showPopup();
        centerPopup();
    }

    public EmployeeDTO getDataFromForm(){
        EmployeeDTO currentUser = new EmployeeDTO();
        currentUser.setEmployeeId(Long.parseLong(getId()));
        currentUser.setFullName(getFullName());
        currentUser.setEmail(getEmail());
        currentUser.setPhoneNumber(getSoDienThoai());
        currentUser.setRole(getRole());
        return currentUser;
    }
    
}
