package com.jiyou.nm.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具类
 */
public class QrCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String fileName = "temp.jpg";
        createQrCode(url, path, fileName);

    }


    /**
     * 创建二维码
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage createQrCodeBufferedImage(String url, Integer width, Integer height) {
        BufferedImage image = null;
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, "1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            image = toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 创建二维码-指定hints
     * @param url
     * @param width
     * @param height
     * @param hints
     * @return
     */
    public static BufferedImage createQrCodeBufferedImage(String url, Integer width, Integer height,Map<EncodeHintType, String> hints) {
        BufferedImage image = null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            image = toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * 生成二维码
     *
     * @param url-包含内容
     * @param path-输出文件路径
     * @param fileName-输出文件名
     * @return
     */
    public static String createQrCode(String url, String path, String fileName) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, "1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 150, 150, hints);
            File file = new File(path, fileName);
            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
                writeToFile(bitMatrix, "jpg", file);
                System.out.println("搞定：" + file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成二维码-关联码图片格式码包专用
     *
     * @param url-包含内容
     * @param path-输出文件路径
     * @param fileName-输出文件名
     * @return
     */
    public static void createQrCode4Cpd(String url, Path path, String fileName) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, "1");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, hints);
            Path filePath = path.resolve(fileName);
            File file = filePath.toFile();
            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
                writeToFile(bitMatrix, "png", file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出二维码BitMatrix到文件
     *
     * @param matrix
     * @param format-文件格式
     * @param file-输出文件
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    /**
     * 输出二维码BitMatrix到流
     *
     * @param matrix
     * @param format-文件格式
     * @param stream-输出流
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 二维码matrix转BufferedImage
     *
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

}
