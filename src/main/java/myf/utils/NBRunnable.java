package myf.utils;

import lombok.SneakyThrows;

public class NBRunnable  implements Runnable{
    private  static int num =100;
    public static void main(String[] asd){
        NBRunnable nbRunnable = new NBRunnable();
        for (int i=0;i<3;i++){
            new Thread(nbRunnable,"thread"+i).start();

        }
    }
    @SneakyThrows
    @Override
    public void run() {
        while (num>0){
           show();
        }

    }
    public synchronized void show() throws InterruptedException {
        if(this.num>0){
//            Thread.sleep(1000);
            System.err.println(Thread.currentThread().getName()+"抢到"+this.num);
            this.num--;
        }
    }
}
