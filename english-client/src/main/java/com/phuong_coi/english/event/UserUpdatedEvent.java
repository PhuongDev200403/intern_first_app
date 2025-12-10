package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.UserDTO;

public class UserUpdatedEvent extends GwtEvent<UserUpdatedEvent.Handler>{
    public interface Handler extends EventHandler{
        void onUserUpdated(UserUpdatedEvent event);
    }

    public static final Type<Handler> TYPE = new Type<>();

    private final UserDTO userDTO;

    public UserUpdatedEvent(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public UserDTO gUserDTO(){
        return userDTO;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onUserUpdated(this);
    }
}
