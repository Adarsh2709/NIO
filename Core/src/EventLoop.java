import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.Iterator;

public class EventLoop {

    // Selector is kernel level event multiplexer
    // It monitors many channels (sockets) using one thread
    private Selector sl;

    // Pipeline is request processing chain
    // Logging -> Router -> Business logic etc
    private HandlerPipeline hp;

    public EventLoop(Selector sl, HandlerPipeline hp) {
        this.sl = sl;
        this.hp = hp;
    }
    public void run()throws Exception{
        while(true){

            // Blocks until at least one registered channel is ready
            // Ready means ACCEPT / READ / WRITE possible
            sl.select();

            // Get all ready events (SelectionKeys)
            Iterator<SelectionKey> keys =
                    sl.selectedKeys().iterator(); // Iterates over the set of channels that are ready for I/O operations after selector.select()
            while(keys.hasNext()){
                SelectionKey key = keys.next();

                // Remove key so we don’t process same event again
                keys.remove();

                // New client connection arrived
                if(key.isAcceptable()){
                    accept(key);
                }
                // Client send some data
                else if(key.isReadable()){
                    read(key);
                }

                //socket ready to send response
                else if(key.isWritable()){
                    write(key);
                }
            }
        }
    }
    public void accept(SelectionKey key)throws Exception{

        // Get server listening socket
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

        // Accept new TCP connection
        SocketChannel sc = ssc.accept();

        // Make client socket non blocking
        sc.configureBlocking(false);

        // Create connection context object
        // Stores buffers + state + channel
        Connection cc = new Connection(sc);

        // Register this client socket with selector
        // Now selector will notify when client sends data
        sc.register(sl,SelectionKey.OP_READ,cc);

    }
    public void read(SelectionKey key) throws Exception{
        Iter
    }


}
