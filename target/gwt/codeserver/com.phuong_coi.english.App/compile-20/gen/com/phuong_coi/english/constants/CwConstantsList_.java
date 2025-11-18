package com.phuong_coi.english.constants;

public class CwConstantsList_ implements com.phuong_coi.english.constants.CwConstantsList {
  
  public java.lang.String[] cwConstantsDepartment() {
    String args[] = (String[]) cache.get("cwConstantsDepartment");
    if (args == null) {
      String [] writer= {
        "Giám đốc",
        "Kỹ thuật",
        "Hành chính",
        "Marketing",
      };
      cache.put("cwConstantsDepartment", writer);
      return writer;
    } else {
      return args;
    }
  }
  
  public java.lang.String[] cwConstantsRole() {
    String args[] = (String[]) cache.get("cwConstantsRole");
    if (args == null) {
      String [] writer= {
        "Trưởng phòng",
        "Nhân viên",
        "Giám đốc",
        "Phó giám đốc",
      };
      cache.put("cwConstantsRole", writer);
      return writer;
    } else {
      return args;
    }
  }
  java.util.Map cache = new java.util.HashMap();
}
