package me.ly.tools.excel.imp1.entity;

import me.ly.tools.excel.Configuration;
import me.ly.tools.core.utils.Assert;

import java.util.Date;

/**
 * ImpExcel
 *
 * @author Created by LiYao on 2017-09-21 14:15.
 */
public class ImpExcel {

    private String excelKey;
    private String excelName;
    private String templateName;
    private String fileName;
    private String recordId;
    private long fileSize;
    private Date importDate;

    public ImpExcel(String templateName) {
        this(templateName, null, 0, null);
    }

    public ImpExcel(String templateName, String fileName, long fileSize, String recordId) {
        Assert.notBlank(templateName, "[templateName] argument is required; it must not be null");
        this.templateName = templateName;
        this.fileName = fileName;
        this.recordId = recordId;
        this.fileSize = fileSize;
        this.importDate = new Date();

        init();

    }

    private void init(){

    }


    public String getExcelKey() {
        return excelKey;
    }

    public String getExcelName() {
        return excelName;
    }
}
