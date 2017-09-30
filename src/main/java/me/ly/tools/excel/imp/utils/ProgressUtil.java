package me.ly.tools.excel.imp.utils;

import me.ly.tools.excel.imp.entity.ProgressInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 进度信息工具类
 *
 * @author Created by LiYao on 2017-05-19 14:27.
 */
public class ProgressUtil {

    // Map<admin,Map<Test.xlsx,Map<Sheet1,进度信息>>>
    private static final Map<String, Map<String, Map<String, ProgressInfo>>> PROGRESS_MAP = new ConcurrentHashMap<>();

    public static Map<String, Map<String, ProgressInfo>> getAllProgress(String key) {
        // 这里放 excel文件的进度信息
        return PROGRESS_MAP.get(key);
    }

    /**
     * Excel整体进度信息
     * 
     * @param key
     * @param fileName
     * @return
     */
    public static Map<String, ProgressInfo> getExcelProgress(String key, String fileName) {
        // 这里放 excel文件的进度信息
        Map<String, Map<String, ProgressInfo>> excelProgressMap = PROGRESS_MAP.get(key);
        if (excelProgressMap == null) {
            return null;
        }
        return excelProgressMap.get(fileName);
    }

    /**
     * Excel 中单个Sheet的进度信息
     * 
     * @param key
     * @param fileName
     * @param sheetName
     * @return
     */
    public static ProgressInfo getSheetProgress(String key, String fileName, String sheetName) {
        Map<String, ProgressInfo> sheetProgressMap = getExcelProgress(key, fileName);
        if (sheetProgressMap == null) {
            return null;
        }
        return sheetProgressMap.get(sheetName);
    }

    public static void setProgress(String key, String fileName, ProgressInfo p) {
        // 这里放 excel文件的进度信息
        Map<String, Map<String, ProgressInfo>> excelProgressMap = PROGRESS_MAP.get(key);
        if (excelProgressMap == null) {
            excelProgressMap = new ConcurrentHashMap<>();
        }
        // excel文件中有多个Sheet，这里放Sheet的进度信息
        Map<String, ProgressInfo> sheetProgressMap = excelProgressMap.get(fileName);
        if (sheetProgressMap == null) {
            sheetProgressMap = new ConcurrentHashMap<>();
        }
        sheetProgressMap.put(p.getSheetName(), p);
        excelProgressMap.put(fileName, sheetProgressMap);
        PROGRESS_MAP.put(key, excelProgressMap);
    }

    public static void removeExcelProgress(String key, String fileName) {
        // 这里放 excel文件的进度信息
        Map<String, Map<String, ProgressInfo>> excelProgressMap = PROGRESS_MAP.get(key);
        excelProgressMap.remove(fileName);
        PROGRESS_MAP.put(key, excelProgressMap);
    }

    public static String getProgressKey(HttpServletRequest request) {
        return "system";
    }

}
