package com.phuong_coi.english.constants;

import com.google.gwt.i18n.client.Constants;

public interface CwConstantsList extends Constants{
    @DefaultStringArrayValue({"Giám đốc", "Kỹ thuật", "Hành chính", "Marketing"})
    String[] cwConstantsDepartment();

    @DefaultStringArrayValue({"Trưởng phòng", "Nhân viên", "Giám đốc", "Phó giám đốc"})
    String[] cwConstantsRole();

    @DefaultStringArrayValue({"ADMIN", "USER"})
    String[] cwConstantsAuth();
}
