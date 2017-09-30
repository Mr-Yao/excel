package me.ly.tools.excel.imp.entity;

import me.ly.tools.excel.entity.Style;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 单元格
 *
 * @author Created by LiYao on 2017-04-20 21:34.
 */
public class Cell {

    private String name;
    private String code;
    private String value;
    private String dataType;
    private String format;
    private String errMsg;
    private int width = 0;
    private int height = 0;

    private int minLength = 0;
    private int maxLength = 0;

    private String regex;
    private int rowNumber;
    private int colNumber;
    private boolean required;
    private boolean allowSkip;
    private String valueList;
    private Style style;
    private Sheet sheet;

    Cell(Sheet sheet) {
        this.sheet = sheet;
    }

    Cell() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (errMsg != null ? errMsg.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + minLength;
        result = 31 * result + maxLength;
        result = 31 * result + (regex != null ? regex.hashCode() : 0);
        result = 31 * result + rowNumber;
        result = 31 * result + colNumber;
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (allowSkip ? 1 : 0);
        result = 31 * result + (valueList != null ? valueList.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (sheet != null ? sheet.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Cell) {
            Cell tmp = (Cell) obj;
            return tmp.getColNumber() == this.getColNumber() && tmp.getRowNumber() == this.getRowNumber();
        }
        return false;
    }

    public Style getStyle() {
        if (this.style != null || this.sheet == null) {
            return style;
        }
        Row row = this.sheet.getRow(this.getRowNumber());
        if (row != null && row.getStyle() != null) {
            this.style = row.getStyle();
            return this.style;
        }
        Column column = this.sheet.getColumn(this.getColNumber());
        if (column != null && column.getStyle() != null) {
            this.style = column.getStyle();
            return this.style;
        }
        this.style = this.sheet.getStyle();
        return this.style;

    }

    public Sheet getSheet() {
        return sheet;
    }

    void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        if (StringUtils.isBlank(dataType)) {
            dataType = "string";
        }
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setWidth(String width) {
        if (StringUtils.isNotBlank(width)) {
            this.setWidth(Integer.parseInt(width));
        }
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        if (StringUtils.isNotBlank(rowNumber)) {
            this.setRowNumber(Integer.parseInt(rowNumber));
        }
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public void setColNumber(String colNumber) {
        if (StringUtils.isNotBlank(colNumber)) {
            this.setColNumber(Integer.parseInt(colNumber));
        }
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setRequired(String required) {
        this.setRequired(Boolean.parseBoolean(required));
    }

    public boolean isAllowSkip() {
        return allowSkip;
    }

    public void setAllowSkip(boolean allowSkip) {
        this.allowSkip = allowSkip;
    }

    public void setAllowSkip(String allowSkip) {
        this.setAllowSkip(Boolean.parseBoolean(allowSkip));
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHeight(String height) {
        if (StringUtils.isNotBlank(height)) {
            this.setHeight(Integer.parseInt(height));
        }
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getValueList() {
        return valueList;
    }

    public void setValueList(String valueList) {
        this.valueList = valueList;
    }
}
