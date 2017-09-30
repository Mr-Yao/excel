package me.ly.tools.excel.imp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * excel
 *
 * @author Created by LiYao on 2017-06-26 14:49.
 */
public class ValidateCell {

	private String value;
	private int rowNumber;
	private int colNumber;
	private String sheetName;
	private String valueList;
	private List<ImportError> errorList = new ArrayList<>(3);

	public ValidateCell(Cell cell) {
		this.value = cell.getValue();
		this.valueList = cell.getValueList();
		this.rowNumber = cell.getRowNumber();
		this.colNumber = cell.getColNumber();
		this.sheetName = cell.getSheet().getName();

	}

	public void addError(String msg) {
		ImportError error = new ImportError(sheetName, rowNumber, colNumber, msg);
		errorList.add(error);
	}

	public List<ImportError> errorList() {
		return this.errorList;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColNumber() {
		return colNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	public String getValueList() {
		return valueList;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
}
