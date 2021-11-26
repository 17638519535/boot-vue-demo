package myf.utils.files;

import lombok.extern.java.Log;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * User: mayongfu
 * Date: 2019/10/14
 * Time: 14:28
 * Description: No Description
 * author：mauubics
 */
@Log
public class DownloadFile {
    /** txt 文件下载工具类*/
    public static void downloadFile(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String fileName){
        File file = new File(fileName);
        if(file.exists()){ log.info(file.exists()+"文件存在"); }else{return;}
        ServletOutputStream os = null;
        FileInputStream in = null;
        String name = "笨笨.txt";
        try {
            String memeType = request.getServletContext().getMimeType(fileName);
            log.info(file.getName());
            //设置下载的文件名
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("笨笨.txt", "UTF-8"));
            response.setContentType(memeType);
            os = response.getOutputStream();
            in = new FileInputStream(file);
            int len = 0;
            byte[] body = new byte[1024];
            while ((len = in.read(body)) != -1){
                os.write(body,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                os.close();
                log.info("下载成功");
            } catch (IOException e) {
                e.printStackTrace();
                log.info("关闭流异常");
            }
        }


    }

}
