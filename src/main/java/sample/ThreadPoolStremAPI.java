package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolStremAPI {

    public static void  start(){
        List<Item> produceQueue = new ArrayList<>();
        BlockingQueue<Item> consumeQueue = new LinkedBlockingQueue<>(100);

        for (int i = 0; i < 100; i++)
            produceQueue.add(new Item());

        produceQueue.parallelStream().forEach(item -> {
            item.produceMe();
            consumeQueue.add(item);
            try {
                consumeQueue.take();
                item.consumeMe();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
