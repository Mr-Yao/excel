package me.ly.tools.excel.imp.entity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import me.ly.tools.excel.imp.utils.FileUtil;

/**
 * excel
 *
 * @author Created by LiYao on 2017-05-19 11:35.
 */
public class ProgressInfo {

    private static final NumberFormat NF = NumberFormat.getPercentInstance();
    private String sheetName;
    private int totalRowNum;// 总行数
    private int processed;// 已处理行数
    private boolean finish;
    private int maxCol;
    private long fileSize;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "YYYY-MM-dd HH:mm:ss")
    private Date startDate;// 导入时间
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "YYYY-MM-dd HH:mm:ss")
    private Date endDate;// 完成时间

    private List<ImportError> errorList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getTotalRowNum() {
        return totalRowNum;
    }

    public void setTotalRowNum(int totalRowNum) {
        this.totalRowNum = totalRowNum;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public synchronized void processedPlus1() {
        this.processed++;
    }

    public String getProgress() {
        NF.setMinimumFractionDigits(2);
        return NF.format(processed * 1.0 / totalRowNum);
    }

    public int getErrorRowNum() {
        return this.errorList.size();
    }

    public int getSuccessRowNum() {
        return getProcessed() - getErrorRowNum();
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void initErrorList() {
        this.errorList = new CopyOnWriteArrayList<>();
    }

    public List<ImportError> getErrorList() {
        return this.errorList;
    }

    public void addError(ImportError error) {
        this.errorList.add(error);
    }

    public void addAllError(List<ImportError> list) {
        this.errorList.addAll(list);
    }

    public int getMaxCol() {
        return maxCol;
    }

    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public String getFileSizeDesc() {
        double fileSize = this.fileSize;
        return FileUtil.fileSizeDesc(fileSize);
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
