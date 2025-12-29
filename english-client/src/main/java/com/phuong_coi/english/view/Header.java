package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Header extends Composite{
    public interface HeaderViewUiBinder extends UiBinder<Widget, Header>{};
    public HeaderViewUiBinder headerViewUiBinder = GWT.create(HeaderViewUiBinder.class);

    @UiField Label lblWelcome;

    public Header(){
        initWidget(headerViewUiBinder.createAndBindUi(this));
        lblWelcome.setText("Đây là phần header của website");
    }

    public void setLblWelcome(String welcomeText){
        lblWelcome.setText(welcomeText);
    }

    public Label getLblWelcome(){
        return lblWelcome;
    }
}
