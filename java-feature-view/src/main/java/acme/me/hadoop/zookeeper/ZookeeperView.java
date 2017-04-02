package acme.me.hadoop.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZookeeperView {
	private ZooKeeper zk = null;
	
	@Before
	public void init() throws Exception {
		Watcher watcher = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("#New Event:");
				System.out.println(event.toString());
			}
		};
		
		try {
			zk = new ZooKeeper("192.168.100.142:2181", 30000, watcher);
		} catch (Exception e) {
			System.err.println("Connecting error!");
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void connect() throws KeeperException, InterruptedException {
		System.out.println("创建/test1");
		zk.create("/test1", "Data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println("获取/test1数据:");
		byte[] data = zk.getData("/test1", false, null);
		System.out.println(new String(data));
		
		System.out.println("修改/test1数据");
		zk.setData("/test1", "Data2".getBytes(), -1);
		
		System.out.println("删除节点/test1");
		zk.delete("/test1", -1);
		
	}
	
	@After
	public void finish() throws Exception {
		try {
			zk.close();
		} catch (InterruptedException e) {
		}
	}
}
