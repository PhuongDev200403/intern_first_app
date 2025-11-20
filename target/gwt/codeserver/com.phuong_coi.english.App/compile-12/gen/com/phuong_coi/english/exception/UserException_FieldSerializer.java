package com.phuong_coi.english.exception;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class UserException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, com.phuong_coi.english.exception.UserException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.phuong_coi.english.exception.UserException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.phuong_coi.english.exception.UserException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.phuong_coi.english.exception.UserException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.phuong_coi.english.exception.UserException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.phuong_coi.english.exception.UserException_FieldSerializer.deserialize(reader, (com.phuong_coi.english.exception.UserException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.phuong_coi.english.exception.UserException_FieldSerializer.serialize(writer, (com.phuong_coi.english.exception.UserException)object);
  }
  
}
