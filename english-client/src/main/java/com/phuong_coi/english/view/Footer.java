package com.phuong_coi.english.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Footer extends Composite{
    interface FooterViewUiBinder extends UiBinder<Widget, Footer>{};
    public FooterViewUiBinder footerViewUiBinder = GWT.create(FooterViewUiBinder.class);

    public Footer(){
        initWidget(footerViewUiBinder.createAndBindUi(this));
    }
}
