import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import pipeline.HandlerPipeline;
import pipeline.LoggingHandler;
import pipeline.RouterHandler;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.Selector;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start()throws Exception{
        Selector sl = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port));
        server.register(sl, server.validOps()); // Register the channel with the server’s selector so it can listen and react to I/O events.

        HandlerPipeline pipeline = new HandlerPipeline();
        pipeline.addLast(new LoggingHandler());
        pipeline.addLast(new RouterHandler());

        pipeline.addLast(new LoggingHandler());
        pipeline.addLast(new RouterHandler());
    }
}
