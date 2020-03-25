package sample;

import java.lang.Runnable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Threads {
    public static void start() throws InterruptedException {

        BlockingQueue<Item> produceQueue = new LinkedBlockingQueue<>(100);
        BlockingQueue<Item> consumeQueue = new LinkedBlockingQueue<>(100);

        for (int i = 0; i < 100; i++)
            produceQueue.add(new Item());


        Runnable producer = () -> {
            while (!produceQueue.isEmpty()) {
                Item item = null;
                item = produceQueue.poll();
                item.produceMe();
                consumeQueue.add(item);
            }
        };

        Runnable consumer = () -> {
            while(!produceQueue.isEmpty() || !consumeQueue.isEmpty()) {
                try {
                    consumeQueue.take().consumeMe();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Thread producer1 = new Thread(producer, "producer1");
        Thread producer2 = new Thread(producer, "producer2");
        Thread producer3 = new Thread(producer, "producer3");
        Thread producer4 = new Thread(producer, "producer4");

        Thread consumer1 = new Thread(consumer, "consumer1");
        Thread consumer2 = new Thread(consumer, "consumer2");
        Thread consumer3 = new Thread(consumer, "consumer3");

        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();

        producer1.join();
        producer2.join();
        producer3.join();
        producer4.join();
        consumer1.join();
        consumer2.join();
        consumer3.join();
    }

}
