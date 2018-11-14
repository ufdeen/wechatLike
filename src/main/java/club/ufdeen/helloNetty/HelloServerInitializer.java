package club.ufdeen.helloNetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Description: 初始化器，channel 注册后，会执行里边相应的初始化方法
 * User: uf.deen
 * Date: 2018-11-05
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        //通过管道，设置handler
        //HttpServerCodec是netty提供的助手类，可以理解为拦截器
        //请求到服务端需解码，响应到客户端需要做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        //添加自定义的助手类，返回 hello netty
        pipeline.addLast("CustomHandler",new CustomHandler());
    }


}
