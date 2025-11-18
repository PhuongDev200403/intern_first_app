package com.phuong_coi.english.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class User_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getChucVu(com.phuong_coi.english.model.User instance) /*-{
    return instance.@com.phuong_coi.english.model.User::chucVu;
  }-*/;
  
  private static native void setChucVu(com.phuong_coi.english.model.User instance, java.lang.String value) 
  /*-{
    instance.@com.phuong_coi.english.model.User::chucVu = value;
  }-*/;
  
  private static native java.lang.String getFullName(com.phuong_coi.english.model.User instance) /*-{
    return instance.@com.phuong_coi.english.model.User::fullName;
  }-*/;
  
  private static native void setFullName(com.phuong_coi.english.model.User instance, java.lang.String value) 
  /*-{
    instance.@com.phuong_coi.english.model.User::fullName = value;
  }-*/;
  
  private static native java.util.Date getNgayVao(com.phuong_coi.english.model.User instance) /*-{
    return instance.@com.phuong_coi.english.model.User::ngayVao;
  }-*/;
  
  private static native void setNgayVao(com.phuong_coi.english.model.User instance, java.util.Date value) 
  /*-{
    instance.@com.phuong_coi.english.model.User::ngayVao = value;
  }-*/;
  
  private static native java.lang.String getPhongBan(com.phuong_coi.english.model.User instance) /*-{
    return instance.@com.phuong_coi.english.model.User::phongBan;
  }-*/;
  
  private static native void setPhongBan(com.phuong_coi.english.model.User instance, java.lang.String value) 
  /*-{
    instance.@com.phuong_coi.english.model.User::phongBan = value;
  }-*/;
  
  private static native java.lang.String getSoDienThoai(com.phuong_coi.english.model.User instance) /*-{
    return instance.@com.phuong_coi.english.model.User::soDienThoai;
  }-*/;
  
  private static native void setSoDienThoai(com.phuong_coi.english.model.User instance, java.lang.String value) 
  /*-{
    instance.@com.phuong_coi.english.model.User::soDienThoai = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.phuong_coi.english.model.User instance) throws SerializationException {
    setChucVu(instance, streamReader.readString());
    setFullName(instance, streamReader.readString());
    setNgayVao(instance, (java.util.Date) streamReader.readObject());
    setPhongBan(instance, streamReader.readString());
    setSoDienThoai(instance, streamReader.readString());
    
  }
  
  public static com.phuong_coi.english.model.User instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.phuong_coi.english.model.User();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.phuong_coi.english.model.User instance) throws SerializationException {
    streamWriter.writeString(getChucVu(instance));
    streamWriter.writeString(getFullName(instance));
    streamWriter.writeObject(getNgayVao(instance));
    streamWriter.writeString(getPhongBan(instance));
    streamWriter.writeString(getSoDienThoai(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.phuong_coi.english.model.User_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.phuong_coi.english.model.User_FieldSerializer.deserialize(reader, (com.phuong_coi.english.model.User)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.phuong_coi.english.model.User_FieldSerializer.serialize(writer, (com.phuong_coi.english.model.User)object);
  }
  
}
