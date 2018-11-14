package club.ufdeen.uChat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * Description: 处理消息的handler
 *  TextWebSocketFrame:在netty中，用于为websocket专门处理文本的对象，frame是消息的载体
 * User: uf.deen
 * Date: 2018-11-06
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //记录管理所有客户端的channels
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        System.out.println(content);

        //向所有客户端发送消息
        for(Channel channel : clients){
            channel.writeAndFlush(new TextWebSocketFrame("[服务器接收到的消息：date:"+ LocalDateTime.now()
                    + " 消息为：" + content + "]"));
        }
        //OR  以下这种方法和上边的循环方式都可
       /*clients.writeAndFlush(new TextWebSocketFrame("[服务器接收到的消息：date:"+ LocalDateTime.now()
                + " 消息为：" + content + "]"));*/

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved时，channelGroup会自动移除对应客户端的channel
        //clients.remove(ctx.channel());
        System.out.println("客户端断开时，对应channel的长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开时，对应channel的短id为：" + ctx.channel().id().asShortText());
    }
}
