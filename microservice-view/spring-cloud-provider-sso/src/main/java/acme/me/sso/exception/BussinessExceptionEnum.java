package acme.me.sso.exception;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:11
 * @Description: microservice-view
 */
public enum BussinessExceptionEnum {
    ERROR_00_01("参数不合法");
    private String message;

    public String getMessage() {
        return message;
    }

    BussinessExceptionEnum(String e) {
        this.message = e;
    }
}
