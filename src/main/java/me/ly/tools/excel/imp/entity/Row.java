package me.ly.tools.excel.imp.entity;

import java.util.ArrayList;
import java.util.List;

import me.ly.tools.excel.entity.Style;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 行
 *
 * @author Created by LiYao on 2017-04-20 21:33.
 */
public class Row {

	private String name;
	private String code;
	private String value;
	private String type;
	private String format;
	private int height = 400;
	private boolean required;
	private boolean allowSkip;
	private int rowNumber;
	private int length = 0;
	private String regex;
	private Sheet sheet;
	private Style style;

	Row(Sheet sheet) {
		this.sheet = sheet;
	}

	public Cell createCell(int colNum) {
		if (colNum <= 0) {
			return null;
		}
		return this.sheet.createCell(this.rowNumber, colNum);
	}

	@JSONField(serialize = false)
	public Cell[] getAllCell() {
		int colTotal = this.sheet.colSize();
		List<Cell> list = new ArrayList<>();
		for (int i = 1; i <= colTotal; i++) {
			list.add(this.getCell(i));
		}
		return list.toArray(new Cell[0]);
	}

	public Cell getCell(int colNum) {
		return this.sheet.getCell(rowNumber, colNum);
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isAllowSkip() {
		return allowSkip;
	}

	public void setAllowSkip(boolean allowSkip) {
		this.allowSkip = allowSkip;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setLength(String length) {
		if (StringUtils.isNotBlank(length)) {
			this.setLength(Integer.parseInt(length));
		}
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
