package myf.utils.files;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * User: mayongfu
 * Date: 2019/10/17
 * Time: 20:28
 * Description: No Description
 */

public class RandomAccessWrite {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new RuntimeException("请输入路径");
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(args[0], "r");
            System.out.println("RandomAccessFile的文件指针初始位置:" + raf.getFilePointer());
            raf.seek(100);
            byte[] bbuf = new byte[1024];
            int hasRead = 0;
            while ((hasRead = raf.read(bbuf)) > 0) {
                System.out.print(new String(bbuf, 0, hasRead,"gbk"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
