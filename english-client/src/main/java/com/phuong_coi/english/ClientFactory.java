package com.phuong_coi.english;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.phuong_coi.english.service.AuthServiceAsync;
import com.phuong_coi.english.service.EmployeeServiceAsync;
import com.phuong_coi.english.view.EmployeeDetail;
import com.phuong_coi.english.view.EmployeeTableView;
import com.phuong_coi.english.view.Form;
import com.phuong_coi.english.view.FormLoginView;
import com.phuong_coi.english.view.FormRegisterView;
import com.phuong_coi.english.view.Home;
import com.phuong_coi.english.view.TableView;

public interface ClientFactory {
    public PlaceController getPlaceController();
    public EventBus getEventBus();
    public FormLoginView getFormLogin();
    public FormRegisterView getFormRegister();
    public Form getForm();
    public TableView getTableView();
    public EmployeeTableView getEmployeeTableView();
    public EmployeeDetail getEmployeeDetail();
    public Home getHome();

    public AuthServiceAsync getAuthService();
    public EmployeeServiceAsync getEmployeeService();

    
}
