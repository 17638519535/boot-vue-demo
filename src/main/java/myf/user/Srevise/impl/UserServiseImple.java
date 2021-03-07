package myf.user.Srevise.impl;

import myf.user.Srevise.UserSevise;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiseImple  implements UserSevise {
    @Override
    @Async("taskExecutor")
    public void test() {
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Async("taskExecutor")
    public void testThread(String data){
        System.err.println("当前运行的线程名称：" + Thread.currentThread().getName()+"---"+data);
    }
}
