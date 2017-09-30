package me.ly.tools.excel.imp.entity;

import java.util.ArrayList;
import java.util.List;

import me.ly.tools.excel.entity.Style;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 列
 *
 * @author Created by LiYao on 2017-04-20 21:32.
 */
public class Column {

    private String name;
    private String code;
    private String value;
    private String dataType;
    private String format;
    private int width = 0;
    private int colNumber;

    private int minLength = 0;
    private int maxLength = 0;

    private String regex;
    private boolean required;
    private boolean allowSkip;
    private String valueList;
    private Style style;
    private Sheet sheet;

    Column(Sheet sheet) {
        this.sheet = sheet;
    }

    public Cell createCell(int rowNum) {
        if (rowNum <= 0) {
            return null;
        }
        return this.sheet.createCell(rowNum, this.colNumber);
    }

    public Cell createExtendsCell() {
        int rowNum = this.sheet.rowSize();
        return this.createExtendsCell(rowNum);
    }

    public Cell createExtendsCell(int rowNum) {
        if (rowNum <= 0) {
            return null;
        }
        String str = JSON.toJSONString(this);
        Cell cell = JSON.parseObject(str, Cell.class);
        cell.setRowNumber(rowNum);
        cell.setSheet(sheet);
        cell.setStyle(style);
        this.sheet.addCell(cell);
        return cell;
    }

    @JSONField(serialize = false)
    public Cell[] getAllCell() {
        int rowTotal = this.sheet.rowSize();
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < rowTotal; i++) {
            list.add(this.getCell(i));
        }
        return list.toArray(new Cell[0]);
    }

    public Cell getCell(int rowNum) {
        return this.sheet.getCell(rowNum, colNumber);
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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
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

    public void setLength(String length) {
        if (StringUtils.isBlank(length)) {
            return;
        }
        if (!length.contains(",")) {
            this.setMaxLength(Integer.parseInt(length));
            return;
        }
        if (length.endsWith(",")) {
            length += "0";
        }
        if (length.startsWith(",")) {
            length = "0" + length;
        }
        String[] lengthArray = length.split(",");
        if (StringUtils.isNotBlank(lengthArray[0])) {
            this.setMinLength(Integer.parseInt(lengthArray[0]));
        }
        if (StringUtils.isNotBlank(lengthArray[1])) {
            this.setMaxLength(Integer.parseInt(lengthArray[1]));
        }

    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getValueList() {
        return valueList;
    }

    public void setValueList(String valueList) {
        this.valueList = valueList;
    }
}
