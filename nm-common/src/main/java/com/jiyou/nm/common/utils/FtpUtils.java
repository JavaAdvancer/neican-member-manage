package com.jiyou.nm.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FtpUtils {

    //ftp服务器地址(本地)
//    public String hostname = "192.168.30.91";
    //交运测试服务器地址,内网链接使用，外网无法使用，具体原因应该跟云主机网关有关,原因未查明
//    public String hostname = "192.168.5.32";
    //阿里云内网地址--正式库使用
    public String hostname = "172.31.77.50";
    //ftp服务器端口号默认为21
    public Integer port = 4449 ;
    //ftp登录账号
    public String username = "jiaoyun";
    //ftp登录密码
    public String pasvword = "000000";

    public FTPClient ftpClient = null;

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, pasvword); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+this.hostname+":"+this.port);
            }
            System.out.println("connect successfu...ftp服务器:"+this.hostname+":"+this.port);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param  pathname ftp服务保存地址
     * @param  fileName 上传到ftp的文件名
     * @param  originfilename 待上传文件的名称（绝对地址）*
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,String originfilename){
        boolean flag = false;
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(new File(originfilename));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            createDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile(String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");
            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean createDirecroty(String remote) throws Exception {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws Exception {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");
            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /** 下载文件 *
     * @param filename 文件名称 *
     * @return
     * */
    public byte[] downloadFile(String filename){
        InputStream in = null;
        byte[] b = null;
        try {
            initFtpClient();
            FTPFile[] ftpFiles = ftpClient.listFiles();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    in = ftpClient.retrieveFileStream(filename);
                    b = FileCopyUtils.copyToByteArray(in);
                    in.close();
                    ftpClient.completePendingCommand();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return b;
    }

    /** * 删除文件 *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return */
    public boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        try {
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

//    public static void main(String[] args) throws Exception {
//        FtpUtils ftp =new FtpUtils();
////        ftp.uploadFile("123.txt", new FileInputStream(new File("E://123.txt")));
//        ftp.downloadFile("123.txt");
////        ftp.deleteFile("ftpFile/data", "123.docx");
//        System.out.println("ok");
//    }
}
