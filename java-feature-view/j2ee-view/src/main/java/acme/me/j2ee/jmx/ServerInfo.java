package acme.me.j2ee.jmx;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2019/11/20 13:43
 */
public class ServerInfo implements ServerInfoMBean {
    private static Integer count = 0;

    @Override
    public int getExecutedSqlCmdCount() {
        return count + (int) Math.random() * 100;
    }

    @Override
    public void printMessage(String message) {
        System.out.println("Print>> " + message);
    }
}
