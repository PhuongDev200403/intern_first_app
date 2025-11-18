package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.phuong_coi.english.constants.CwConstantsButton;
import com.phuong_coi.english.constants.CwConstantsList;
import com.phuong_coi.english.model.User;
import com.phuong_coi.english.presenter.UserListPresenter;
import com.phuong_coi.english.service.UserService;
import com.phuong_coi.english.service.UserServiceAsync;

public class CwFormView {
    private TextBox txtFullName = new TextBox();
    private TextBox txtSoDienThoai = new TextBox();
    private ListBox lbDepartment = new ListBox(false);
    private ListBox lbRole = new ListBox(false);
    private DateBox checkIn = new DateBox();

    private final CwConstantsButton cwConstantsButton = GWT.create(CwConstantsButton.class);
    private final CwConstantsList cwConstantsList = GWT.create(CwConstantsList.class);
    private final UserServiceAsync userService = GWT.create(UserService.class);

    private final UserListPresenter userListPresenter;
    
    // Lưu số điện thoại cũ khi đang update
    private String originalPhoneNumber = null;

    // Constructor nhận presenter
    public CwFormView(UserListPresenter userListPresenter) {
        this.userListPresenter = userListPresenter;
    }
    
    public void fillUserData(User user) {
        if (user != null) {
            txtFullName.setText(user.getFullName());
            txtSoDienThoai.setText(user.getSoDienThoai());
            
            // Set department
            String dept = user.getPhongBan();
            for (int i = 0; i < lbDepartment.getItemCount(); i++) {
                if (lbDepartment.getItemText(i).equals(dept)) {
                    lbDepartment.setSelectedIndex(i);
                    break;
                }
            }
            
            // Set role
            String role = user.getChucVu();
            for (int i = 0; i < lbRole.getItemCount(); i++) {
                if (lbRole.getItemText(i).equals(role)) {
                    lbRole.setSelectedIndex(i);
                    break;
                }
            }
            
            checkIn.setValue(user.getNgayVao());
            originalPhoneNumber = user.getSoDienThoai();
        }
    }
    
    // Method để clear form
    public void clearForm() {
        txtFullName.setText("");
        txtSoDienThoai.setText("");
        txtSoDienThoai.setEnabled(true);
        lbDepartment.setSelectedIndex(0);
        lbRole.setSelectedIndex(0);
        checkIn.setValue(null);
        originalPhoneNumber = null;
    }


    public Widget onInitialize() {
        // layout
        FlexTable formLayout = new FlexTable();
        formLayout.setCellSpacing(10);
        FlexCellFormatter formatter = formLayout.getFlexCellFormatter();

        // title
        formLayout.setHTML(0, 0, "User management");
        formatter.setColSpan(0, 0, 2);
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        // fullname
        formLayout.setWidget(1, 0, new Label("Fullname"));
        formLayout.setWidget(1, 1, txtFullName);
        txtFullName.addStyleName("form-control");

        // phone
        formLayout.setWidget(2, 0, new Label("Phone number"));
        formLayout.setWidget(2, 1, txtSoDienThoai);
        txtSoDienThoai.addStyleName("form-control");

        // department
        formLayout.setWidget(3, 0, new Label("Department"));
        String[] departments = cwConstantsList.cwConstantsDepartment();
        if (departments != null) {
            for (String item : departments) {
                lbDepartment.addItem(item);
            }
        }
        lbDepartment.addStyleName("form-select-sm");
        formLayout.setWidget(3, 1, lbDepartment);

        // role
        formLayout.setWidget(4, 0, new Label("Role"));
        String[] roles = cwConstantsList.cwConstantsRole();
        if (roles != null) {
            for (String item : roles) {
                lbRole.addItem(item);
            }
        }
        lbRole.addStyleName("form-select-sm");
        formLayout.setWidget(4, 1, lbRole);

        // check in
        formLayout.setHTML(5, 0, "Check in");
        checkIn.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
        formLayout.setWidget(5, 1, checkIn);
        checkIn.addStyleName("form form-control");

        // buttons
        Button btnAdd = new Button(cwConstantsButton.cwConstantsAdd());
        btnAdd.addStyleName("btn-primary btn");
        formLayout.setWidget(6, 0, btnAdd);

        Button btnUpdate = new Button(cwConstantsButton.cwConstantsUpdate());
        btnUpdate.addStyleName("btn btn-secondary");
        formLayout.setWidget(6, 1, btnUpdate);

        // Add handler
        btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String fullName = txtFullName.getText().trim();
                String phoneNumber = txtSoDienThoai.getText().trim();

                int deptIndex = lbDepartment.getSelectedIndex();
                String department = deptIndex >= 0 ? lbDepartment.getItemText(deptIndex) : "";

                int roleIndex = lbRole.getSelectedIndex();
                String role = roleIndex >= 0 ? lbRole.getItemText(roleIndex) : "";

                java.util.Date checkInDate = checkIn.getValue();

                if (fullName.isEmpty()) {
                    Window.alert("Tên không được bỏ trống nhé !");
                    return;
                }
                if (phoneNumber.isEmpty()) {
                    Window.alert("Số điện thoại phải là số và chứa 10 ký tự");
                    return;
                }

                // build user (use java.util.Date)
                User newUser = new User();
                newUser.setFullName(fullName);
                newUser.setSoDienThoai(phoneNumber);
                newUser.setPhongBan(department);
                newUser.setChucVu(role);
                newUser.setNgayVao(checkInDate);

                
                userService.addUser(newUser, new AsyncCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        Window.alert("Thêm mới người dùng thành công");

                        if (userListPresenter != null) {
                            userListPresenter.loadUsers();
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Thêm thất bại: " + caught.getMessage());
                    }
                });
            }
        });

        btnUpdate.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (originalPhoneNumber == null) {
                    Window.alert("Vui lòng chọn user từ danh sách để cập nhật");
                    return;
                }

                String fullName = txtFullName.getText().trim();
                String phoneNumber = txtSoDienThoai.getText().trim();

                int deptIndex = lbDepartment.getSelectedIndex();
                String department = deptIndex >= 0 ? lbDepartment.getItemText(deptIndex) : "";

                int roleIndex = lbRole.getSelectedIndex();
                String role = roleIndex >= 0 ? lbRole.getItemText(roleIndex) : "";

                java.util.Date checkInDate = checkIn.getValue();

                if (fullName.isEmpty()) {
                    Window.alert("Tên không được bỏ trống nhé!");
                    return;
                }

                // Build user với data mới
                User updatedUser = new User();
                updatedUser.setFullName(fullName);
                updatedUser.setSoDienThoai(phoneNumber);
                updatedUser.setPhongBan(department);
                updatedUser.setChucVu(role);
                updatedUser.setNgayVao(checkInDate);

                userService.updateUser(updatedUser, originalPhoneNumber, new AsyncCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        Window.alert("Cập nhật người dùng thành công");
                        clearForm();
                        if (userListPresenter != null) {
                            userListPresenter.loadUsers();
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Cập nhật thất bại: " + caught.getMessage());
                    }
                });
            }
        });

        DecoratorPanel decoratorPanel = new DecoratorPanel();
        decoratorPanel.setSize("300px", "200px");
        decoratorPanel.addStyleName("bg-light center");
        decoratorPanel.add(formLayout);
        return decoratorPanel;
    }
}
