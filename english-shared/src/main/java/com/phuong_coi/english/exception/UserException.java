package com.phuong_coi.english.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserException extends Exception implements IsSerializable {
    private static final long serialVersionUID = 1L;

    // Constructor không tham số bắt buộc cho GWT RPC
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }
}
