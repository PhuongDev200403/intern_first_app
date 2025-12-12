package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.UserDTO;

public class UserRemoveEvent extends GwtEvent<UserRemoveEvent.Handler>{
    public interface Handler extends EventHandler {
        void onUserRemoved(UserRemoveEvent event);
    }

    public static final Type<Handler> TYPE = new Type<>();

    private UserDTO userDTO;

    public UserRemoveEvent(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public UserDTO getUser(){
        return userDTO;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onUserRemoved(this);
    }
}
