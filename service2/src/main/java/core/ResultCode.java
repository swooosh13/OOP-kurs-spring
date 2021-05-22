package core;

public enum ResultCode {
  SUCCESS(200),
  FAIL(400),
  NOT_FOUND(404),
  INTERNAL_SERVER_ERROR(500);

  private final int code;

  ResultCode(int code) {
    this.code = code;
  }

  public int code() {
    return code;
  }
}
