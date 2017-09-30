package me.ly.tools.excel.imp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.ly.tools.core.utils.ApplicationContextUtil;
import me.ly.tools.excel.imp.constants.ImpRecord;
import me.ly.tools.excel.imp.entity.ImportRecord;
import me.ly.tools.excel.imp.entity.ProgressInfo;
import me.ly.tools.excel.utils.ExcelUtil;
import me.ly.tools.mybatis.base.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 导入记录工具类
 *
 * @author Created by LiYao on 2017-07-19 15:35.
 */
public class ImpRecordUtil {

    private static BaseService baseService = (BaseService) ApplicationContextUtil.getBean("baseServiceImpl");

    public static ImportRecord createNewRecord(String loginName, String fileName, long fileSize, Integer impType) {
        ImportRecord importRecord = new ImportRecord();
        importRecord.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        importRecord.setFileName(fileName);
        importRecord.setImpUser(loginName);
        importRecord.setFileSize(fileSize);
        importRecord.setImpState(ImpRecord.ING.state());
        importRecord.setImpDate(new Date());
        importRecord.setImpType(impType);
        importRecord.setHaveErrorFile(false);
        baseService.insert(importRecord);
        return importRecord;
    }

    public static List<ImportRecord> recordList(String loginNane, String fileName, String startDate, String endDate, String impState,
            Integer impType) {
        StringBuilder dynamicWhere = new StringBuilder("imp_type = ").append(impType);

        if (StringUtils.isNotBlank(loginNane)) {
            dynamicWhere.append(" AND imp_user = '").append(loginNane).append("'");
        }

        if (StringUtils.isNotBlank(impState)) {
            dynamicWhere.append(" AND imp_state = ").append(impState);
        }

        if (StringUtils.isNotBlank(startDate)) {
            startDate += " 00:00:00";
            dynamicWhere.append(" AND imp_date >= STR_TO_DATE('").append(startDate).append("', '%Y-%m-%d %H:%i:%s')");
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate += " 23:59:59";
            dynamicWhere.append(" AND imp_date <= STR_TO_DATE('").append(endDate).append("', '%Y-%m-%d %H:%i:%s')");
        }
        if (StringUtils.isNotBlank(fileName)) {
            dynamicWhere.append(" AND file_name LIKE '%").append(fileName).append("%'");
        }
        dynamicWhere.append(" ORDER BY imp_date DESC");
        return baseService.selectAll(ImportRecord.class, dynamicWhere.toString());
    }

    public static void sheetEnd(String recordId, String progressKey) throws Exception {
        ImportRecord importRecord = baseService.selectById(ImportRecord.class, recordId);
        if (importRecord == null) {
            return;
        }
        String fileName = importRecord.getFileName();

        boolean isFinish = true;
        boolean haveError = false;
        boolean recordHaveError = importRecord.getImpState().equals(ImpRecord.FAIL.state());
        Map<String, ProgressInfo> excelProgress = ProgressUtil.getExcelProgress(progressKey, fileName);
        for (Map.Entry<String, ProgressInfo> entry : excelProgress.entrySet()) {
            ProgressInfo progress = entry.getValue();
            if (isFinish) {
                isFinish = progress.isFinish();
            }
            haveError = !CollectionUtils.isEmpty(progress.getErrorList());
        }

        if (haveError) {
            importRecord.setImpState(ImpRecord.FAIL.state());
            File file = FileUtil.createTempFile(importRecord.getId());
            OutputStream out = new FileOutputStream(file);
            ExcelUtil.generateErrorMarkFile(progressKey, fileName, out);
            importRecord.setHaveErrorFile(true);

        }
        if (isFinish && !haveError && !recordHaveError) {
            importRecord.setImpState(ImpRecord.OK.state());
        }
        baseService.update(importRecord);

        if (isFinish) {
            // 把进度信息从缓存里面去了
            ProgressUtil.removeExcelProgress(progressKey, fileName);
            FileUtil.deleteTempFile(fileName);
        }
    }

    public static void errorFileDownloaded(String recordId) throws Exception {
        ImportRecord importRecord = new ImportRecord();
        importRecord.setHaveErrorFile(true);
        importRecord.setId(recordId);
        importRecord.setRemark("报错文件已下载");
        baseService.update(importRecord);
    }

    public static void appearError(String recordId, String msg) {
        ImportRecord importRecord = new ImportRecord();
        importRecord.setId(recordId);
        importRecord.setImpState(ImpRecord.FAIL.state());
        importRecord.setHaveErrorFile(false);
        importRecord.setRemark(msg);
        baseService.update(importRecord);
    }

    public static void serverShutdown() {
        baseService.executeCUD("UPDATE import_record SET imp_state = ?, remark = '服务器关闭，导致中断' WHERE imp_state = ?", ImpRecord.FAIL.state(),
                ImpRecord.ING.state());
    }

}
