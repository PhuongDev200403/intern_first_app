package com.phuong_coi.english.event;

//import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.phuong_coi.english.model.UserDTO;

public class UserAddedEvent extends GwtEvent<UserAddedEvent.Handler>{
    public interface Handler extends EventHandler{
        void onUserAdded(UserAddedEvent event);
    }

    public static final Type<Handler> TYPE = new Type<>();

    private final UserDTO userDTO;
    //public final List<UserDTO> users;

    public UserAddedEvent(UserDTO user){
        this.userDTO = user;
        //this.users = users;
    }

    public UserDTO getUser() { return userDTO; }

    

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onUserAdded(this);
    }
}
