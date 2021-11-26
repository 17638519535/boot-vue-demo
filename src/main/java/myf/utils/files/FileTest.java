package myf.utils.files;

import lombok.extern.java.Log;

import java.io.*;

/**
 * User: mayongfu
 * Date: 2019/10/15
 * Time: 16:28
 * Description: No Description
 */

/**
 *
 * OutputStream和InputStream 字节流
 * Writer和Reader是最大的祖先 字符流
 */
@Log
public class FileTest {
    private static final int BTES = 10*1024*1024;
    public static void main(String[] asg){
        System.out.println("系统默认编码为："+ System.getProperty("file.encoding"));
        //字节流
       // fileByte("G://config/123.txt","G://config/456.txt");
        // 字符流
       fileChar("G://config/123.jpg","G://config/txt/456.jpg");
    }

    /**
     * 字节流缓存去处理文件
     * BufferedInputStream  写出
     * BufferedOutputStream  写入
     */

    public static void fileByte(String path, String path2){

        try {
            long startTm = System.currentTimeMillis();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path2,false));
            int len;
            String jsonStr=null;
            byte[] by = new byte[1024];
            while((len=in.read())!=-1){
                /**解决字节流 中文乱码 放一个大的数组*/
                //System.err.println(URLEncoder.encode(new String(by,0,len),"utf-8"));
                //jsonStr = new String(bytes, 0, len,"UTF-8");//加上编码格式
                System.err.println(new String(by,0,len,"gbk"));
                out.write(by,0,len);
            }
            long end = System.currentTimeMillis();
            System.err.println("运行时间"+(end-startTm)+"ms");
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *字符缓冲区复制文件
     * @param src
     * @param desc
     * @throws Exception
     */
     public static void fileChar(String src, String desc) {
         try {
             long start = System.currentTimeMillis();
            //建立字符缓冲流输入（读）对象，并绑定数据源
             BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(src),"UTF-8"));
             //建立字符缓冲流输出（写）对象，并绑定目的地
             BufferedWriter out=new BufferedWriter(new FileWriter(desc,false),BTES);
             String len="";
             //将读到的内容遍历出来，然后在通过字符写入
             StringBuffer stringBuffer;
             while((len=in.readLine())!=null){
                 stringBuffer = new StringBuffer();
                 String namesds  = stringBuffer.append(len+"\r\n").toString();
                 out.write(namesds);             //边读取边输出流
                }
             /*运行的程序主体*/
             long end = System.currentTimeMillis();
             System.err.println("程序运行时间："+(end-start)+"ms");
             //资源访问过后关闭，先创建的后关闭，后创建的先关闭14
             out.flush();
             out.close();
             in.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

}
