package acme.me.common.exception;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:11
 * @Description: microservice-view
 */
public enum BussinessExceptionEnum {
    ERROR_00_01("参数不合法"),
    ERROR_00_02("数据已存在"),
    ERROR_00_03("格式错误"),
    ERROR_01_01("算法错误");

    private String message;

    public String getMessage() {
        return message;
    }

    BussinessExceptionEnum(String e) {
        this.message = e;
    }
}
