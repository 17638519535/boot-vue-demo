package myf.config.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
    @Async("taskExecutor")
    public void tesTask(String data) throws InterruptedException {
       System.err.println(Thread.currentThread().getName()+"-----"+data);
        Thread.sleep(40000);
    }

}
