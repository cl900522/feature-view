package acme.me.j2ee.jmx;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2019/11/20 13:42
 */
public interface ServerInfoMBean {
    int getExecutedSqlCmdCount();

    void printMessage(String message);
}
