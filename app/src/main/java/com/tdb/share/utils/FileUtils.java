package com.tdb.share.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/9/25.
 */
public class FileUtils {
    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取文件保存点
     */
    public static File getSaveFile(String fileNmae) {
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/" + fileNmae);
        } catch (IOException e) {
        }
        return file;
    }

    /**
     * 从指定文件夹获取文件
     */
    public static File getSaveFile(String folder, String fileNmae) {
        File file = new File(getSavePath(folder), fileNmae);
        return file;
    }

    /**
     * 获取文件保存路径
     */
    public static String getSavePath(String folder) {
        return Environment.getExternalStorageDirectory() + "/" + folder;
    }
}
