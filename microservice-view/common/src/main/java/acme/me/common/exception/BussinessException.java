package acme.me.common.exception;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:11
 * @Description: microservice-view
 */
public class BussinessException extends Exception {
    private BussinessExceptionEnum error;

    public BussinessException(BussinessExceptionEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public BussinessException(BussinessExceptionEnum error, String bussinessInfo) {
        super(error.getMessage() + "->" + bussinessInfo);
        this.error = error;
    }
}
