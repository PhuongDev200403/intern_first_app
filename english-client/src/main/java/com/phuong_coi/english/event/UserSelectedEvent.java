package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.UserDTO;

public class UserSelectedEvent extends GwtEvent<UserSelectedEvent.Handler>{
    public interface Handler extends EventHandler {
        void onSelectedRow(UserSelectedEvent event);
    }

    public static final Type<Handler> TYPE = new Type<>();

    private final UserDTO selectedUser;

    public UserSelectedEvent(UserDTO selectedUser){
        this.selectedUser = selectedUser;
    }

    public UserDTO getSelectedUser(){
        return selectedUser;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onSelectedRow(this);
    }
}
