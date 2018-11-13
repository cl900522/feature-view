package acme.me.common.model;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 16:20
 * @Description: microservice-view
 */
public class Response {
    private static final Integer SUCCESS = 0;
    private static final Integer FAIL = 1;

    private Object data;
    private Integer code = 0;
    private String message;

    public Response(Integer code) {
        this.code = code;
    }

    public static Response success(Object data) {
        Response response = new Response(SUCCESS);
        response.setData(data);
        return response;
    }

    public static Response fail(String message) {
        Response response = new Response(FAIL);
        response.setMessage(message);
        return response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
