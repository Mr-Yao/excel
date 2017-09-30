package me.ly.tools.excel;

import java.util.Date;

/**
 * me.ly.tools.excel.Configuration
 *
 * @author Created by LiYao on 2017-09-21 11:39.
 */
public class Configuration {

    private String templateName;
    private String fileName;
    private String recordId;
    private long fileSize;
    private Date importDate;

    public Configuration(String templateName, String fileName) throws Exception {
        this.templateName = templateName;
        this.fileName = fileName;
        this.importDate = new Date();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

}
