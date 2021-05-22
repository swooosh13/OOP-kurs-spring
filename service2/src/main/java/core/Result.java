package core;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;


public class Result<T> {
  private int code;
  private String message;
  private T data;

  public Result setCode(ResultCode resultCode) {
    this.code = resultCode.code();
    return this;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public Result setMessage(String message) {
    this.message = message;
    return this;
  }

  public Result setData(T data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    Gson gsong = new GsonBuilder().setPrettyPrinting().create();
    return gsong.toJson(this);
  }
}
