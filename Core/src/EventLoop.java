import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class EventLoop {
    private Selector sl;
    private HandlerPipeline hp;

    public EventLoop(Selector sl, HandlerPipeline hp) {
        this.sl = sl;
        this.hp = hp;
    }
    public void run()throws Exception{
        while(true){
            sl.select();
            Iterator<SelectionKey> keys =
                    sl.selectedKeys().iterator(); // Iterates over the set of channels that are ready for I/O operations after selector.select()
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (key.isAcceptable()) accept(key);
                else if (key.isReadable()) read(key);
                else if (key.isWritable()) write(key);
            }
        }
    }
    public void accept()throws Exception{

    }
}
