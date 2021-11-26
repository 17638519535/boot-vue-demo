package myf.utils.files.ftp;

import com.jcraft.jsch.*;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
     * 类说明 sftp工具类
     * admin caiz  SFTP的登录上传和下载
     */

    public class SFTPUtil {
        private org.slf4j.Logger log = LoggerFactory.getLogger(SFTPUtil.class);
        private ChannelSftp sftp;

        private com.jcraft.jsch.Session session;
        /**
         * SFTP 登录用户名
         */
        private String username;
        /**
         * SFTP 登录密码
         */
        private String password;
        /**
         * 私钥
         */
        private String privateKey;
        /**
         * SFTP 服务器地址IP地址
         */
        private String host;
        /**
         * SFTP 端口
         */
        private int port;


        /**
         * 构造基于密码认证的sftp对象
         */
        public SFTPUtil(String username, String password, String host, int port) {
            this.username = username;
            this.password = password;
            this.host = host;
            this.port = port;
        }

        /**
         * 构造基于秘钥认证的sftp对象
         */
        public SFTPUtil(String username, String host, int port, String privateKey) {
            this.username = username;
            this.host = host;
            this.port = port;
            this.privateKey = privateKey;
        }

        public SFTPUtil() {
        }


        /**
         * 连接sftp服务器
         */
        public  boolean login() {
            try {
                JSch jsch = new JSch();
                if (privateKey != null) {
                    jsch.addIdentity(privateKey);// 设置私钥
                }

                session = jsch.getSession(username, host, port);

                if (password != null) {
                    session.setPassword(password);
                }
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");

                session.setConfig(config);
                session.connect();

                Channel channel = session.openChannel("sftp");
                channel.connect();

                sftp = (ChannelSftp) channel;
                return true;
            } catch (JSchException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 关闭连接 server
         */
        public void logout() {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                }
            }
            if (session != null) {
                if (session.isConnected()) {
                    session.disconnect();
                }
            }
        }


        /**
         * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
         *
         * @param basePath     服务器的基础路径
         * @param directory    上传到该目录
         * @param sftpFileName sftp端文件名
         * @param input         输入流
         */
        public void upload(String basePath, String directory, String sftpFileName, InputStream input) throws SftpException {
            try {
                sftp.cd(basePath);
                sftp.cd(directory);
            } catch (SftpException e) {
                //目录不存在，则创建文件夹
                String[] dirs = directory.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    try {
                        sftp.cd(tempPath);
                    } catch (SftpException ex) {
                        sftp.mkdir(tempPath);
                        sftp.cd(tempPath);
                    }
                }
            }
            sftp.put(input, sftpFileName);  //上传文件
        }


        /**
         * 下载文件。
         *
         * @param directory    下载目录
         * @param downloadFile 下载的文件
         * @param saveFile     存在本地的路径
         */
        public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
            //后缀名
            String[] allowTypes = new String[] {".txt"};

            //获取本地目录
//        String path= FtpConfig.getYqtBaseInfoLocalDir();

//            String[] fileNames =GetInfomation.getFileName(directory, allowTypes);
//
//            for (int i = 0; i < fileNames.length; i++) {
//                System.out.println("fileNames");
//            }

            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            File file = new File(saveFile);
            if(!file.exists()){
                    file.mkdirs();
            }

            sftp.get(downloadFile, new FileOutputStream(file));
        }

        /**
         * 下载文件
         *
         * @param directory    下载目录
         * @param downloadFile 下载的文件名
         * @return 字节数组
         */
//        public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
//        if (directory != null && !"".equals(directory)) {
//            sftp.cd(directory);
//        }
//        InputStream is = sftp.get(downloadFile);
//
//            byte[] fileData = new byte[0];
//            try {
//                fileData = IOUtils.toByteArray(is);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return fileData;
//    }


        /**
         * 删除文件
         *
         * @param directory  要删除文件所在目录
         * @param deleteFile 要删除的文件
         */
        public void delete(String directory, String deleteFile) throws SftpException {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        }


        /**
         * 列出目录下的文件
         *
         * @param directory 要列出的目录
         * @param
         */
        public Vector<?> listFiles(String directory) throws SftpException {
            return sftp.ls(directory);
        }

        //上传文件测试
        public static void main(String[] args) throws SftpException, IOException {
            SFTPUtil sftp = new SFTPUtil("用户名", "密码", "ip地址", 22);
            sftp.login();
            File file = new File("D:\\图片\\t0124dd095ceb042322.jpg");
            InputStream is = new FileInputStream(file);

            sftp.upload("基础路径", "文件路径", "test_sftp.jpg", is);
            sftp.logout();
        }
    /**
     * 上传单个文件
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName, boolean del)
    {
        FileInputStream in = null;
        try
        {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            if (del)
            {
                //删除本地的文件
                deleteFile(localPath+localFileName);

            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    /**
     * 创建目录
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath)
    {
        try
        {
            if (isDirExist(createpath))
            {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry)
            {
                if (path.equals(""))
                {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString()))
                {
                    sftp.cd(filePath.toString());
                }
                else
                {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
            return true;
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory)
    {
        boolean isDirExistFlag = false;
        try
        {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e)
        {
            if (e.getMessage().toLowerCase().equals("no such file"))
            {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }
    /**
     * 如果目录不存在就创建目录
     * @param path
     */
    public void mkdirs(String path)
    {
        File f = new File(path);

        String fs = f.getParent();

        f = new File(fs);

        if (!f.exists())
        {
            f.mkdirs();
        }
    }

    /**
     * 批量上传文件
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath,
                                   boolean del)
    {
        try
        {
//            connect();
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isFile()
                        && files[i].getName().indexOf("bak") == -1)
                {
                    if (this.uploadFile(remotePath, files[i].getName(), localPath, files[i].getName(),false) && del)
                    {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }
            if (log.isInfoEnabled())
            {
                log.info("upload file is success:remotePath=" + remotePath
                        + "and localPath=" + localPath + ",file size is "
                        + files.length);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        finally
//        {
//            this.disconnect();
//        }
        return false;

    }
        /**
         * 批量下载文件
         * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
         * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
         * @param ：下载文件格式(以特定字符开头,为空不做检验)
         * @param ：下载文件格式(文件格式)
         * @param del：下载后是否删除sftp文件
         * @return
         */
        public List<String> batchDownLoadFile(String remotePath, String localPath, boolean del)
        {
            List<String> filenames = new ArrayList<String>();
            try
            {
                // connect();
                Vector v = listFiles(remotePath);
                 sftp.cd(remotePath);
                 //判断下载的文件时不是两个
                if (v.size()==4)
                {
                    System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
                    Iterator it = v.iterator();
                    while (it.hasNext())
                    {
                        ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                        String filename = entry.getFilename();
                        log.info("文件名："+filename);
                        SftpATTRS attrs = entry.getAttrs();
                        if (!attrs.isDir())
                        {
                            boolean flag = false;
                            String localFileName = localPath + filename;
                            // 三种情况
                         flag = downloadFile(remotePath, filename,localPath, filename);
                                if (flag)
                                {
                                    filenames.add(localFileName);
                                    if (flag && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }

                            }
                        }
                    }else{
                    log.error("EZTDG文件数量不对");

                }

                if (log.isInfoEnabled())
                {
                    log.info("download file is success:remotePath=" + remotePath
                            + "and localPath=" + localPath + ",file size is"
                            + v.size());
                }
            }
            catch (SftpException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // this.disconnect();
            }
            return filenames;
        }



        public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName)
        {
            FileOutputStream fieloutput = null;
            try
            {
                // sftp.cd(remotePath);
                File file = new File(localPath +localFileName);
//                if(!file.exists()){
//                    file.mkdirs();
//                }
                fieloutput = new FileOutputStream(file);
                sftp.get(remotePath + remoteFileName, file.toString());
                if (log.isInfoEnabled())
                {
                    log.info("===DownloadFile:" + remoteFileName + " success from sftp.to"+file);
                }
                return true;
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (SftpException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (null != fieloutput)
                {
                    try
                    {
                        fieloutput.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    /**
     * 删除stfp文件
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     * @param
     */
    public void deleteSFTP(String directory, String deleteFile)
    {
        try
        {
            // sftp.cd(directory);
            sftp.rm(directory + deleteFile);
            if (log.isInfoEnabled())
            {
                log.info("delete file success from sftp."+directory + deleteFile);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 删除本地文件
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            return false;
        }

        if (!file.isFile())
        {
            return false;
        }
        boolean rs = file.delete();
        if (rs && log.isInfoEnabled())
        {
            log.info("delete file success from local.");
        }
        return rs;
    }
    }
