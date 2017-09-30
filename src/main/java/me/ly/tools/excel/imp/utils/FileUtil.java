package me.ly.tools.excel.imp.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.AccessController;

import org.apache.commons.io.FileUtils;
import sun.security.action.GetPropertyAction;

/**
 * 文件工具类
 *
 * @author Created by LiYao on 2017-07-15 16:16.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUtil extends FileUtils {

    private static final File TMP_DIR = new File(AccessController.doPrivileged(new GetPropertyAction("user.home")));

    private static final String SEPARATOR = File.separator;
    private static final String SUFFIX = ".tmp";
    private static final String PATH = SEPARATOR + "excel-import" + SEPARATOR;

    public static File tempDir() {
        return TMP_DIR;
    }

    public static File createTempFile(String fileName) throws Exception {
        File file = new File(tempDir() + PATH + fileName + SUFFIX);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            return file;
        }
        file.createNewFile();
        return file;
    }

    public static File getTempFile(String fileName) throws Exception {
        return new File(tempDir() + PATH + fileName + SUFFIX);
    }

    public static void deleteTempFile(String fileName) throws Exception {
        File file = createTempFile(fileName);
        file.delete();
    }

    public static void closeResource(Closeable... closeable) {
        try {
            for (Closeable c : closeable) {
                if (c != null) {
                    c.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fileSizeDesc(double fileSize) {
        if (fileSize < 1024) {
            return fileSize + "B";
        }
        fileSize /= 1024;
        if (fileSize < 1024) {
            return String.format("%.2fKB", fileSize);
        }
        fileSize /= 1024;
        if (fileSize < 1024) {
            return String.format("%.2fMB", fileSize);
        }
        fileSize /= 1024;
        if (fileSize < 1024) {
            return String.format("%.2fGB", fileSize);
        }
        fileSize /= 1024;
        return String.format("%.2fTB", fileSize);
    }
}
