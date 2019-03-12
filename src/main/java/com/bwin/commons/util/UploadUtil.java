package com.bwin.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;

/**
 * 上传工具类
 */
@Slf4j
public class UploadUtil {

    public static void main(String[] args) throws IOException {
        File dir = dirToUpload("D:/Test/upload", "3");
        String dirPath = StringUtils.replace(dir.getAbsolutePath(), "\\", "/");
        log.info(dirPath);
        String fileName = "0.png";
        log.info(FileUtils.getFile(dirPath, fileName).toString());
    }

    /**
     * 根据日期和用户id，创建文件夹以供上传
     * @param basePath 文件保存路径前缀，使用"/"分隔符
     * @param userId 用户id，可选
     * @return 文件夹
     */
    public static File dirToUpload(String basePath, String userId) throws IOException {
        basePath = StringUtils.replace(basePath, "\\", "/");
        String suffix = DateFormatUtils.format(System.currentTimeMillis(), "yyyy/MM/dd/HH/mm/ss");
        suffix = StringUtils.isBlank(userId) ? suffix : String.format("%s/%s", userId, suffix);
        File dir = FileUtils.getFile(basePath, suffix);
        FileUtils.forceMkdir(dir);
        return dir;
    }

}
