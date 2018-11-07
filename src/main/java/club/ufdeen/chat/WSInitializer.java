package club.ufdeen.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Description:
 * User: uf.deen
 * Date: 2018-11-06
 */
public class WSInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());  //http编解码器
        pipeline.addLast(new ChunkedWriteHandler());  //写大数据流的支持
        //对httpmessage进行聚合，聚合成FullHttpResponse,FullHttpRequest
        //几乎在netty中的编程，都会用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));


        /***
         * todo:websocket 服务器处理的协议，用于指定给客户端连接访问的路由 :/ws
         * todo:WebSocketServerProtocolHandler会帮你处理一些繁重复杂的事
         * todo:会帮你处理握手动作：handshaking(close,ping,pong)
         * todo:对于websocket来说，都是以frames来进行传输的，不同数据类型对应的frames也不同
         * */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new ChatHandler());
    }
}
