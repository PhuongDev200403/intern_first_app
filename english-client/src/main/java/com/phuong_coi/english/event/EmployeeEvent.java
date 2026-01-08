package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.EmployeeDTO;

public class EmployeeEvent extends GwtEvent<EmployeeEvent.Handler>{

    public interface Handler extends EventHandler{
        void onEmployeeEvent(EmployeeEvent event);
    }

    public enum Action{
        CREATE, UPDATE, DELETE, DELETE_MULTIPLE, SEARCH, CLICK, FILTER, SORT, SEARCH_EMAIL, SEARCH_PHONE_NUMBER
    }

    public static Type<Handler> TYPE = new Type<>();

    private Action action;
    private EmployeeDTO employee;
    private String keyword;
    private Object data; 
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    public EmployeeEvent(Action action, Object data){
        this.action = action;
        this.data = data;
    }

    public EmployeeEvent(Action action, EmployeeDTO employee){
        this.action = action;
        this.employee = employee;
    }
    public EmployeeEvent(Action action, String keyword){
        this.action = action;
        this.keyword = keyword;
    }

    public EmployeeEvent(Action action){
        this.action = action;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onEmployeeEvent(this);
    }
    
    public Action getAction(){
        return action;
    }

    public String getKeyword(){
        return keyword;
    }

    public EmployeeDTO getEmployee(){
        return employee;
    }

    public Object getData(){
        return data;
    }
}
