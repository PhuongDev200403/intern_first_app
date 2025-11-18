package com.phuong_coi.english.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phuong_coi.english.model.GreetingResponse;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<GreetingResponse> callback);
}
