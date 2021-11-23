package myf.utils.file;

import java.nio.file.*;

/**
 * User: mayongfu
 * Date: 2019/11/8
 * Time: 23:12
 * Description: No Description
 */

public class FilesPaths {
    public static void main(String[] asd)throws Exception  {
        /**Files.copy复制文件*/
        //copy("G://config/config.properties","G://config/txt/123.txt");
        /**追加合并文件*/
        filesAppend("G:\\config\\txt\\list.txt","G:\\config\\append");
    }

    /**
     *
     * @param filePath1  被复制的文件路径
     * @param filePath2  复制到的文件路径
     * @throws Exception
     */
    public static void copy(String filePath1, String filePath2) throws Exception {
        Files.copy(Paths.get(filePath1), Paths.get(filePath2), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 文件合并
     * @param filePath 目标文件的路径
     * @param decres    存放块文件的目录路径
     */
    public static void filesAppend(String filePath, String decres)throws Exception {
        Path path = Paths.get(filePath);
        //不存在目标文件就创建
        if(!Files.exists(path)){
            Files.createFile(path);
        }
        Files.list(Paths.get(decres)).sorted((a1, b1)->{
            System.err.println(a1.getFileName().toString()+"\n"+b1.getFileName().toString());
            String fileName1 = a1.getFileName().toString();
            String fileName2 = b1.getFileName().toString();
            Integer num1 = Integer.valueOf(fileName1.substring(1,fileName1.indexOf("."))) ;
            Integer num2= Integer.valueOf(fileName2.substring(1,fileName2.indexOf("."))) ;
            return Integer.compare(num1,num2);
        }).forEach((path1) -> {
            try {
                appenFile(filePath.toString(),path1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 追加文件
     * @param filePath  目标文件
     * @param path      每块文件
     * @throws Exception
     */
    public static void appenFile(String filePath, Path path)throws Exception {
        Files.write(Paths.get(filePath), Files.readAllBytes(path), StandardOpenOption.APPEND);
        //删除临时块文件
      //  Files.delete(path);

    }
}
