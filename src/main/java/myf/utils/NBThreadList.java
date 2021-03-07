package myf.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池
 */

public class NBThreadList {
    public static void main(String[] string) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        /**有返回值的线程*/
        Map<String,String> resultMap = new HashMap<>();
        for (int i=0;i<7;i++){
            int finalI = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
                System.err.println("213");
                return Thread.currentThread().getName()+"执行"+String.valueOf(finalI +1);
            },service);
            resultMap.put(String.valueOf(i),future.get());
        }
        resultMap.forEach((key,valie)->{
            System.err.println(key+":"+valie);
        });
        service.shutdown();
    }
}
