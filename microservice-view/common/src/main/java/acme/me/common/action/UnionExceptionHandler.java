package acme.me.common.action;

import acme.me.common.exception.BussinessException;
import acme.me.common.model.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UnionExceptionHandler {
    /**
     * 拦截捕捉自定义异常 BussinessException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BussinessException.class)
    public Response businessErrorHandler(BussinessException ex) {
        return Response.fail(ex.getMessage());
    }

    /**
     * 全局异常 Exception.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response errorHandler(Exception ex) {
        return Response.fail("系统错误：" + ex.getClass().getSimpleName() + "->" + ex.getMessage());
    }

}
