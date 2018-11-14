package club.ufdeen.uChat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * Description: websocket 服务器
 * User: uf.deen
 * Date: 2018-11-06
 */
@Component
public class WSServer {

    /**
     * Description: 使用此内部类来保证wsserver单例
     */
    private static class SingletionWSServer {
        static final WSServer wsServer = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletionWSServer.wsServer;
    }


    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;


    public WSServer() {
        this.mainGroup = new NioEventLoopGroup();
        this.subGroup = new NioEventLoopGroup();
        this.server = new ServerBootstrap();
        this.server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSInitializer());
    }

    public void start() {
        this.future = server.bind(8088);
        System.err.println("netty WSServer 启动完毕。。");
    }
}
