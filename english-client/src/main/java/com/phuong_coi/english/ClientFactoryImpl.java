package com.phuong_coi.english;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.phuong_coi.english.service.AuthService;
import com.phuong_coi.english.service.AuthServiceAsync;
import com.phuong_coi.english.service.EmployeeService;
import com.phuong_coi.english.service.EmployeeServiceAsync;
import com.phuong_coi.english.view.EmployeeDetail;
import com.phuong_coi.english.view.Form;
import com.phuong_coi.english.view.FormLoginView;
import com.phuong_coi.english.view.FormRegisterView;
import com.phuong_coi.english.view.TableView;

public class ClientFactoryImpl implements ClientFactory{
    private PlaceController placeController;
    private EventBus eventBus;
    private FormLoginView formLogin;
    private FormRegisterView formRegister;
    private Form form;
    private TableView tableView;
    private EmployeeDetail employeeDetail;

    private AuthServiceAsync authService = GWT.create(AuthService.class);
    private EmployeeServiceAsync employeeService = GWT.create(EmployeeService.class);

    public ClientFactoryImpl(){
        eventBus = new SimpleEventBus();
        placeController = new PlaceController(eventBus);
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public EventBus getEventBus() {
       return eventBus;
    }

    @Override
    public FormLoginView getFormLogin() {
        formLogin = new FormLoginView();
        return formLogin;
    }

    @Override
    public FormRegisterView getFormRegister() {
        formRegister = new FormRegisterView();
        return formRegister;
    }

    @Override
    public AuthServiceAsync getAuthService() {
        return authService;
    }

    @Override
    public EmployeeServiceAsync getEmployeeService(){
        return employeeService;
    }

    @Override
    public Form getForm() {
        form = new Form();
        return form;
    }

    @Override
    public TableView getTableView() {
        tableView = new TableView();
        return tableView;
    }

    @Override
    public EmployeeDetail getEmployeeDetail() {
        employeeDetail = new EmployeeDetail();
        return employeeDetail;
    }
    
}
