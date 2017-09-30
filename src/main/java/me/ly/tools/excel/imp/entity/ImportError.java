package me.ly.tools.excel.imp.entity;

/**
 * ImportError
 *
 * @author Created by LiYao on 2017-05-15 17:25.
 */
public class ImportError {

    private String sheetName;
    private volatile int rowNum;
    private volatile int colNum;
    private String msg;

    public ImportError() {
    }

    public ImportError(String sheetName, int rowNum, int colNum, String msg) {
        this.sheetName = sheetName;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.msg = msg;
    }

    public String getFullMsg() {
        return "导入" + sheetName + "第" + rowNum + "行,第" + colNum + "列发生错误:" + msg;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
