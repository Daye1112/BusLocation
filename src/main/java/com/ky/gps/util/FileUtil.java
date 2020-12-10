package com.ky.gps.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 *
 * @author Daye
 */
public class FileUtil {

    public static List<String> getFileNames(List<File> fileList, String realPath) {
        List<String> fileNames = new ArrayList<>();
        for (File file : fileList) {
            String fileName = file.toString().substring(realPath.length());
            fileNames.add(fileName);
        }
        return fileNames;
    }

    /**
     * 获取文件夹下的所有文件
     *
     * @param filePath 指定文件夹目录
     * @return 返回文件list
     */
    public static List<File> readFiles(String filePath) {
        List<File> fileList = new ArrayList<>();

        File file = new File(filePath);
        if (!file.isDirectory()) {
            fileList.add(file);
        } else {
            String[] fileNameList = file.list();
            for (int i = 0; i < fileNameList.length; i++) {
                File readFile = new File(filePath + "\\" + fileNameList[i]);
                if (!readFile.isDirectory()) {
                    fileList.add(readFile);

                } else if (readFile.isDirectory()) {
                    fileList.addAll(readFiles(filePath + "\\" + fileNameList[i]));
                }
            }
        }
        return fileList;
    }
}
