package club.ufdeen;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description: 实现客户端发送请求，服务端返回hello netty
 * User: uf.deen
 * Date: 2018-11-05
 */
public class HelloNetty {
    public static void main(String[] args) throws  Exception{
        //创建主线程组，接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建从线程组，接收boss主线程组的任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty服务器创建，serverBootstrap是启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)  //设置主从线程组
                    .channel(NioServerSocketChannel.class) //设置nio的双向通道
                    .childHandler(new HelloServerInitializer());  //设置子处理器，处理workerGroup

            //启动server,设置启动端口8088，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel,设置同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
