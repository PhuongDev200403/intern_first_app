package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.EmployeeDTO;

public class AuthActionEvent extends GwtEvent<AuthActionEvent.Handler>{
    public interface Handler extends EventHandler{
        void onAuthAction(AuthActionEvent event);
    }

    public enum Action{
        Login, Register
    }

    public static final Type<Handler> TYPE = new Type<>();

    // đối tượng trả về
    private final Action action;
    private final EmployeeDTO employee;

    public AuthActionEvent(Action action, EmployeeDTO employee){
        this.action = action;
        this.employee = employee;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onAuthAction(this);
    }

    public Action getAction(){
        return action;
    }

    public EmployeeDTO getEmployee(){
        return employee;
    }
}
