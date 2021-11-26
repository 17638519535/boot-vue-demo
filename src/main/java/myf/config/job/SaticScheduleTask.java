package myf.config.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@Slf4j
@ConditionalOnProperty(prefix = "scheduling",name="enabled",havingValue = "true")
public class SaticScheduleTask {

    private List<String> listData;

       @Scheduled(cron = "${task.timed-task-date}")
       @Async("taskExecutor")
       public void threadCheduling() throws InterruptedException {
           log.info("当前运行的线程名称：" + Thread.currentThread().getName()+"---");
           Thread.sleep(40000);
       }


    //
    @Scheduled(cron = "${task.timed-task-date}")
    public  void configureTasks() throws InterruptedException, ExecutionException {
        System.out.println("执行静态定时任务时间: " + LocalDateTime.now());
        Map<String, CompletableFuture> resultMap = new HashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i=0;i<this.getData().size();i++){
            int finalI = i;
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(()->{
                try {
                    return threadData(this.getData().get(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            },service);
            resultMap.put(String.valueOf(i),future);
        }
        for (Map.Entry<String,CompletableFuture>  entry: resultMap.entrySet()){
            boolean is = (boolean) entry.getValue().join();
            log.info(String.valueOf(is));
        }
    }

    private List<String> getData(){
        List<String> listData = new ArrayList<>();
        for (int i=0;i<20;i++){
            listData.add("张三"+i);
        }
         return listData;
    }
    public boolean threadData(String data) throws InterruptedException {
        Thread.sleep(20000);
        System.err.println("当前运行的线程名称：" + Thread.currentThread().getName()+"---"+data);
        return true;
    }
}


