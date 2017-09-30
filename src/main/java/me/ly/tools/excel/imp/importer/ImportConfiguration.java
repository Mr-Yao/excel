package me.ly.tools.excel.imp.importer;

import java.util.Date;
import java.util.List;

import me.ly.tools.excel.imp.entity.Excel;
import me.ly.tools.excel.imp.entity.Sheet;
import me.ly.tools.excel.imp.utils.ExcelTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel 导入信息
 *
 * @author Created by LiYao on 2017-04-18 21:19.
 */
public class ImportConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportConfiguration.class);
    /** 默认处理批次 */
    public static final int DEFAULT_BATCHES = 1000;
    // 总行数
    private String templateName;

    private String excelKey;
    private String excelName;
    private String fileName;
    private String recordId;
    private long fileSize;
    private Date importDate;

    private List<Sheet> sheetList;
    private Excel excelEntity;
    // private int batches = DEFAULT_BATCHES;

    public static ImportConfiguration getConfiguration(String templateName, String fileName) throws Exception {
        return new ImportConfiguration(templateName, fileName);
    }

    private ImportConfiguration(String templateName, String fileName) throws Exception {
        this.templateName = templateName;
        this.fileName = fileName;
        this.importDate = new Date();
        this.init();
    }

    private void init() throws Exception {
        this.excelEntity = ExcelTemplateUtil.readExcelTemplate(templateName);
        this.excelKey = excelEntity.getKey();
        this.excelName = excelEntity.getName();
        this.sheetList = excelEntity.getAllSheet();
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExcelKey() {
        return excelKey;
    }

    public String getExcelName() {
        return excelName;
    }

    public List<Sheet> getSheetList() {
        return sheetList;
    }

    public Excel getExcelEntity() {
        return excelEntity;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordId() {
        return recordId;
    }
}
