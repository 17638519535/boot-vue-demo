package myf.test.singletion;

/**
 * 单例模式
 * 饿汉模式：类加载时就已经初始化
 */
public class Singletion1 {
    //定义一个私有的静态变量
    private static  Singletion1 singletion = new Singletion1();
    //私有的构造方法！ 防止被实例化
    private Singletion1(){}
    //定义一个静态工厂方法
    public static  Singletion1 getSingletion(){
        return singletion;
    }
 }
