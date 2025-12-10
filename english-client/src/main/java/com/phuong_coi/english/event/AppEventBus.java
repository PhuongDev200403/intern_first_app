package com.phuong_coi.english.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class AppEventBus {
    private static final EventBus EVENT_BUS = new SimpleEventBus();

    private AppEventBus() {}

    public static EventBus get() { 
        return EVENT_BUS;
    }
}
