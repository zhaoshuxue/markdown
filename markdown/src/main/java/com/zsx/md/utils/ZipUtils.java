package com.zsx.md.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 */
public class ZipUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);
    // 缓冲区大小
    private static final int BUFFER = 2048;

    public static void main(String[] args) {
    }

    public static List<String> getFileList(String sourceFilePath) {
        ArrayList<String> fileNameList = Lists.newArrayList();

        File sourceFile = new File(sourceFilePath);

        if (!sourceFile.exists()) {
            logger.info("目录不存在");
            return fileNameList;
        }

        File[] files = sourceFile.listFiles();
        if (files == null || files.length == 0) {
            logger.info("目录下没有任何文件");
            return fileNameList;
        }

        for (File file : files) {
            getFile(file, "", fileNameList);
        }
        logger.info("文件列表： {} ", JSON.toJSONString(fileNameList, true));
        return fileNameList;
    }

    public static void getFile(File file, String path, List<String> fileNameList) {
        String fileName = file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                getFile(subFile, path + fileName + "/", fileNameList);
            }
        } else {
            fileNameList.add(path + fileName);
        }
    }

    public static void zipFile(String rootPath, String zipPath, String zipFileName) {

        List<String> fileList = getFileList(rootPath);
        if (fileList == null || fileList.size() == 0) {
            return;
        }
        // 结果文件
        String zipFile = zipPath + zipFileName;

        ZipOutputStream zipOutputStream = null;
        BufferedInputStream bis = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(zipFile)));

            for (String file : fileList) {
                ZipEntry zipEntry = new ZipEntry(file);
                zipOutputStream.putNextEntry(zipEntry);

                bis = new BufferedInputStream(new FileInputStream(new File(rootPath + file)));

                int count = 0;
                byte[] bytes = new byte[BUFFER];
                while ((count = bis.read(bytes, 0, BUFFER)) != -1) {
                    zipOutputStream.write(bytes, 0, count);
                }
            }
            zipOutputStream.closeEntry();

            // 冲刷输出流
            zipOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                // 关闭输出流
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("压缩完成");
    }
}
