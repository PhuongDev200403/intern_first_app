package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.EmployeeDTO;

public class EmployeeEvent extends GwtEvent<EmployeeEvent.Handler>{

    public interface Handler extends EventHandler{
        void onEmployeeEvent(EmployeeEvent event);
    }

    public enum Action{
        CREATE, UPDATE, DELETE, SEARCH, CLICK, FILTER
    }

    public static Type<Handler> TYPE = new Type<>();

    private Action action;
    private EmployeeDTO employee;
    private String keyword;
    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
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
        //constructor sử dụng cho phương thức xóa và không cần trả về EmployeeDTO
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
}
