package acme.me.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleData {
    private static Logger log = Logger.getLogger(HandleData.class);

    public static void handle(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            handle(entry);
        }
    }

    private static void handle(CanalEntry.Entry entry) {
        // 获取日志行
        CanalEntry.RowChange rowChage = null;
        try {
            rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (Exception e) {
            log.error(e);
        }
        // 获取执行事件类型
        EventType eventType = rowChage.getEventType();
        // 日志打印，数据明细
        log.info(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s", entry
                .getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(), entry.getHeader()
                .getSchemaName(), entry.getHeader().getTableName(), eventType));
        // 获取表名
        String tableName = entry.getHeader().getTableName();

        // 遍历日志行，执行任务
        for (RowData rowData : rowChage.getRowDatasList()) {
            Map<String, Object> data = new HashMap<>();
            data.put("tableName", tableName);
            data.put("type", eventType);
            // 删除操作
            if (eventType == EventType.DELETE) {
                data.put("before", parse(rowData.getBeforeColumnsList()));
            } else if (eventType == EventType.UPDATE) {
                data.put("before", parse(rowData.getBeforeColumnsList()));
                data.put("after", parse(rowData.getAfterColumnsList()));
            } else {
                data.put("after", parse(rowData.getAfterColumnsList()));
            }

            log.debug(data);
        }
    }

    private static Map<String, Object> parse(List<CanalEntry.Column> columns) {
        Map<String, Object> values = new HashMap<>();
        for (CanalEntry.Column column : columns) {
            values.put(column.getName(), column.getValue());
        }
        return values;
    }
}
