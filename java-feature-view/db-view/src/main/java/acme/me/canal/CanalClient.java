package acme.me.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.List;

public class CanalClient {
    private Logger log = Logger.getLogger(CanalClient.class);

    private String host;
    private Integer port = 11111;
    private String destination = "example";

    private String userName = "canal";
    private String password = "canal";


    public CanalClient(String host) {
        this.host = host;
    }

    public CanalClient(String host, Integer port, String destination) {
        this.host = host;
        this.port = port;
        this.destination = destination;
    }

    public void init() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        CanalConnector connector = CanalConnectors.newSingleConnector(inetSocketAddress, destination, userName, password);
        try {
            // 连接canal，获取数据
            connector.connect();
            connector.subscribe();
            connector.rollback();
            log.info("数据同步工程启动成功，开始获取数据");
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(1000);
                // 数据批号
                long batchId = message.getId();
                // 获取该批次数据的数量
                int size = message.getEntries().size();
                // 无数据
                if (batchId == -1 || size == 0) {
                    // 等待1秒后重新获取
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error(e);
                        Thread.currentThread().interrupt();
                    }
                    // 提交确认
                    connector.ack(batchId);
                    // 数据存在，执行方法
                } else {
                    try {
                        // 处理数据
                        List<CanalEntry.Entry> entries = message.getEntries();
                        HandleData.handle(entries);
                        // 提交确认
                        connector.ack(batchId);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        log.error(e1);
                        // 提交确认
                        connector.rollback(batchId);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 断开连接
            connector.disconnect();
        }
    }
}
