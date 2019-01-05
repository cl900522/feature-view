package acme.me.canal;

import com.alibaba.otter.canal.common.utils.AddressUtils;
import org.junit.Test;

public class CanalView {
    @Test
    public void test1() {
        String host = AddressUtils.getHostIp();
        CanalClient canalClient = new CanalClient("192.168.100.200");
        canalClient.init();
    }
}
