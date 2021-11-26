package myf.test.singletion;

/**
 * 单例模式
 * 懒汉模式:类加载时不初始化
 */
public class Singletion {
    //定义一个私有的静态变量
    private static Singletion ingnum = null;
    //私有构造方法防止实例化
    private Singletion(){}
    //惊天工厂方法 返回 ingnum实例
    public static synchronized Singletion getSingletion(){
        if(ingnum == null){
            ingnum =  new  Singletion();
        }
        return ingnum;
    }
}
