package acme.me.rpc.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * GRPC 服务开发流程
 * 1. 根据官方指导创建项目（或者model），引入jar包和插件。参考：https://github.com/grpc/grpc-java
 * 2. 编写 proto文件，放在{projectRoot}/src/main/proto文件夹
 * 3. 使用 protobuf 的maven插件运行打包命令。生成grpc服务的{ServiceName}Rpc的服务类和出入参对象，并生成jar包
 * 4. 服务端的项目（或者model）引入刚才生成的jar包，首先继承实现{ServiceName}Grpc.{ServiceName}ImplBase，使用类似于 #testServer 方法将服务的实例对象注册到服务中
 * 5. 客户端的项目（或者model）引入刚才生成的jar包，使用类似于 #testClient 方法生成服务的代理对象
 *
 * 服务端和客户端对象的生成可以参考官方的demo文档
 * https://github.com/grpc/grpc-java/tree/master/examples/src/main/java/io/grpc/examples
 *
 * @author cdchenmingxuan
 * @description
 * @since 2019/11/20 15:23
 */
@Slf4j
public class GRpcTest {
    private static class RouteGuideGrpcImp /*extends RouteGuideGrpc.RouteGuideImplBase*/ {
        //TODO 实现RouteGuideImplBase 的对外接口
    }

    @Test
    public void testServer() throws Exception {

        Integer port = 9090;
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(80);
        Server server = serverBuilder.
                //addService(). // TODO 添加服务实现
                        build();

        server.start();
        log.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                server.shutdown();
                System.err.println("*** server shut down");
            }
        });
        server.awaitTermination();
    }

    @Test
    public void testClient() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        // RouteGuideGrpc.RouteGuideStub blockingStub = RouteGuideGrpc.newStub(channel);
        // RouteGuideBlockingStub blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        // RouteGuideFutureStub blockingStub = RouteGuideGrpc.newFutureStub(channel);
        // TODO 创建stub调用服务, 并调用stub的服务
    }
}