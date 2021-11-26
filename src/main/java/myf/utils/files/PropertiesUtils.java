package myf.utils.files;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 当前目录下的/config目录
 * 当前目录
 * classpath里的/config目录
 * classpath 跟目录
 *
 * 引入外部配置信息
 * java –jar -Dspring.config.location=xxx/xxx/xxxx.properties xxxx.jar
 *
 *    运行时指定 配置文件
 * java -jar -Dspring.config.location=D:\config\config.properties 打成的jar包/.jar
 */
public class PropertiesUtils {
       private Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    public static void getProperties() {
        BufferedReader reader = null;
        OutputStream out = null;
        //获取配置文件的路径
        Properties pr = null;
        File file = new File("G:/configxml/ccc.properties");  //读写的路径


        boolean f = false;
        if (file.isFile() && file.exists()) {
            System.err.println(file.exists());
            f = true;
        }
        if (f) {
            try {
                pr = new Properties();
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
                // out= new BufferedWriter(new FileWriter(outFile));
                pr.load(reader);
                //   String schedulingvalue = pr.getProperty("scheduleds");   //获取配置文件的单个  配置信息的value
                System.err.println(pr.get("scheduleds"));     //获取 value值
                System.err.println("是所有的key和value" + pr);
                Enumeration en = pr.propertyNames(); //得到配置文件的名字
                //后去所有的key和vlue 遍历
                while (en.hasMoreElements()) {
                    String Key = (String) en.nextElement();
                    String value = pr.getProperty(Key);
                    System.out.println(Key + "=" + value);
                    pr.setProperty(Key, value);
                }


                out = new FileOutputStream("application.properties", false);//true表示追加打开
                pr.setProperty("phone", "10086");
                try {
                    //把配置文件的信息写入项目的配置文件中去
                    pr.store(out, "8888888");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
