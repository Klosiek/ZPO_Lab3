package sample;

import java.util.concurrent.*;

public class ThreadPool {
    public static void start() {

        BlockingQueue<Item> produceQueue = new LinkedBlockingQueue<>(100);
        BlockingQueue<Item> consumeQueue = new LinkedBlockingQueue<>(100);

        for (int i = 0; i < 100; i++)
            produceQueue.add(new Item());

        ExecutorService service = Executors.newFixedThreadPool(8);


        Runnable producer = () -> {
            while (!produceQueue.isEmpty()) {
                Item item = produceQueue.poll();
                item.produceMe();
                consumeQueue.add(item);
            }
        };

        Runnable consumer = () -> {
            while (!consumeQueue.isEmpty() || !produceQueue.isEmpty()) {
                try {
                    consumeQueue.take().consumeMe();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        service.execute(producer);
        service.execute(producer);
        service.execute(producer);
        service.execute(producer);


        service.execute(consumer);
        service.execute(consumer);
        service.execute(consumer);
        service.execute(consumer);

        service.shutdown();

        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
