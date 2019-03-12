package com.bwin.commons.util;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Slf4j
public class DownloadUtil {

    /**
     * 从url下载到本地文件
     * @param url 文件url
     * @param targetFile 目标文件
     * @param recover 恢复下载，默认false；如需恢复下载使用{@link DownloadUtil#recoverNIODownload(String, String)}
     * @see <a href="http://www.spring4all.com/article/1782">JavaIO与NIO下载网络文件详解</a>
     */
    public static void NIODownload (String url, String targetFile, boolean recover) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new URL(url).openStream();
            ReadableByteChannel src = Channels.newChannel(inputStream);
            if (recover) {
                outputStream = new FileOutputStream(targetFile, true);
            } else {
                outputStream = new FileOutputStream(targetFile);
            }
            outputStream.getChannel().transferFrom(src, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 恢复下载
     * @param url 文件url
     * @param targetFile 目标文件
     */
    public static void recoverNIODownload(String url, String targetFile) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            long remoteFileSize = connection.getContentLengthLong();
            long localFileSize = new File(targetFile).length();
            if (localFileSize < remoteFileSize) {
                connection.setRequestProperty("Range", "bytes=" + localFileSize + "-" + remoteFileSize);
            }
            NIODownload(url, targetFile, true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        NIODownload("http://192.168.2.112:8080/upload/201901/1547534455896.jpg", "D:\\Repository\\System\\Desktop\\images\\1547534455896.jpg", false);
        recoverNIODownload("http://192.168.2.112:8080/upload/201901/1547534455896.jpg", "D:\\Repository\\System\\Desktop\\images\\1547534455896.jpg");
    }

}
