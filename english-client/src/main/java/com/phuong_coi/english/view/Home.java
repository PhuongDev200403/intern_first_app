package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Home extends Composite{
    interface HomeViewUiBinder extends UiBinder<Widget, Home>{};
    public HomeViewUiBinder homeViewUiBinder = GWT.create(HomeViewUiBinder.class);

    @UiField Button btnAdd;
    @UiField Button btnView;
    public Home(){
        initWidget(homeViewUiBinder.createAndBindUi(this));
    }

    //Các phương thức get cho 2 button
    public HasClickHandlers getBtnAdd(){
        return btnAdd;
    }

    public HasClickHandlers getBtnView(){
        return btnView;
    }
}
