package com.jiyou.nm.common.utils.csv;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CsvUtil {

    //分割符号
    private final static String NEW_LINE_SEPARATOR = "\t\n";

    /**
     * 循环写入csv文件
     *
     * @param headers  列头
     * @param data     数据内容
     * @param filePath 创建的csv文件路径
     * @param append   是否追加
     * @throws IOException
     **/
    public static Long writeCsv(String[] headers, List<String[]> data, String filePath, boolean append) throws IOException {
        Long writeQuantity = 0L;
        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        //创建FileWriter对象
        FileWriter fileWriter = new FileWriter(filePath, append);
        /*为了防止ms excel打开utf8文件乱码，需要在文件头写入utf-8 bom
         注意只能写入一次,重复写入会造成文件内容异常*/
        if (!append) {
            fileWriter.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
        }

        //创建CSVPrinter对象
        CSVPrinter printer = new CSVPrinter(fileWriter, formator);

        //有表头写入
        if (headers != null && headers.length > 0) {
            printer.printRecord(headers);
        }
        if (null != data) {
            //循环写入数据
            for (String[] lineData : data) {
                printer.printRecord(lineData);
                writeQuantity++;
            }
        }
        printer.flush();
        fileWriter.flush();
        fileWriter.close();
        printer.close();
        System.out.println("CSV文件创建成功,文件路径:" + filePath);
        return writeQuantity;
    }


    public static void main(String[] args) throws ZipException {
        List<File> files = Arrays.asList(new File("D:\\upload\\cpd\\img"));
        File file = new File("D:\\upload\\cpd\\test.zip");
        doCompressFiles(files, file, "123456");
    }

    public static ZipFile doCompressFiles(List<File> srcFiles, File destFile, String pasvword) throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(destFile);
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setEncryptFiles(true);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword(pasvword);
        for (File srcFile : srcFiles) {
            zipFile.addFile(srcFile, parameters);
        }
        return zipFile;
    }

    public static ZipFile doCompressFolder(List<File> srcFiles, File destFile, String pasvword) throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(destFile);
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setEncryptFiles(true);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword(pasvword);
        parameters.setIncludeRootFolder(true);
        for (File srcFile : srcFiles) {
            zipFile.addFolder(srcFile, parameters);
        }
        return zipFile;
    }
}
