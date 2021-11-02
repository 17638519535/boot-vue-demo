package myf.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池
 */
@Slf4j
public class NBThread implements Runnable,Callable<String> {
    public List<String> data;
    public NBThread(){}
    public  NBThread(List<String> data){
        this.data=data;
    }

    public static void main(String[] string) throws ExecutionException, InterruptedException {
        //completableFutureThread();
        // runnableThread();
       // callbaleThread();
        executorThread();
    }

    public static void  executorThread(){
        ExecutorService service =Executors.newFixedThreadPool(3);
        final List<String> data = getData();
        /**主线程统一资源分配*/
        for(int i=0;i<data.size();i++){
            int finalI = i;
            service.submit(new Runnable() {
                @Override
                @SneakyThrows
                public void run() {
                    Thread.sleep(1000);
                    System.err.println(Thread.currentThread().getName()+"-------"+data.get(finalI));
                }
            });
        }
        /**然后主线程执行了*/
        log.error("关闭线程池d");
        service.shutdown();
        log.error("执行一次");
    }

    /**
     * Callable 接口 有返回值的
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void callbaleThread() throws ExecutionException, InterruptedException {
        Map<String,FutureTask<String>> resultMap =new HashMap<>();
        for (int i=0;i<2;i++){
            List<String> listDta =  new ArrayList<>();
            listDta.add("张三"+i);
            listDta.add("李四"+i);
            Callable<String> callable =new  NBThread(listDta);

            FutureTask<String> futureTask = new FutureTask<>(callable);
            Thread thread = new Thread(futureTask,"Thread"+i);
            thread.start();
            resultMap.put("Thread"+i,futureTask);
        }
        for (Map.Entry<String,FutureTask<String>> map:resultMap.entrySet()){
            log.info(map.getValue().get());
        }
    }
    /**
     * Runnable 接口执行
     */
    public static void runnableThread(){

        for (int i=0;i<2;i++){
            List<String> listDta =  new ArrayList<>();
            listDta.add("张三"+i);
            listDta.add("李四"+i);
            NBThread nbThread = new NBThread(listDta);
            Thread thread = new Thread(nbThread,"线程"+i);
            thread.start();
        }
      }
    /**
     * CompletableFuture 线程池
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void completableFutureThread()throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        /**有返回值的线程*/
        Map<String,CompletableFuture<String>> resultMap = new HashMap<>();
        for (int i=0;i<7;i++){
            int finalI = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                log.info(Thread.currentThread().getName()+"执行"+String.valueOf(finalI +1));
                return Thread.currentThread().getName()+"执行"+String.valueOf(finalI +1);
            },service);
            resultMap.put(String.valueOf(i),future);
        }
        /**
         * 此处如果get 取出返回值就是线程同步 ，
         * 底层会调用 weit（）方法
         */
       for (Map.Entry<String,CompletableFuture<String>> map: resultMap.entrySet()){
           log.info(map.getValue().join());
       }
        log.info("关闭线程池");
        service.shutdown();
    }

    /**
     * Runnable
     */
    @Override
    public void run() {
        this.data.forEach((value)->{
            try {
                Thread.sleep(100);
                log.info(Thread.currentThread().getName()+"--"+value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 是有返回值的 线程
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        this.data.forEach((value)->{
            try {
                Thread.sleep(100);
                log.info(Thread.currentThread().getName()+"--"+value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        return data.get(0);
    }

    public static List<String> getData(){
        List<String> listDta =  new ArrayList<>();
        listDta.add("张三");
        listDta.add("李四");
        listDta.add("王五");
        listDta.add("马六");
        listDta.add("田七");
        return listDta;

    }
}
