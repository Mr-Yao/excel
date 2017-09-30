package me.ly.tools.excel.imp.entity;

import me.ly.tools.excel.entity.Style;
import me.ly.tools.excel.imp.entity.Cell;
import me.ly.tools.excel.imp.entity.Row;
import me.ly.tools.excel.imp.entity.Sheet;
import org.apache.commons.lang3.StringUtils;

/**
 * RowHeader
 *
 * @author Created by LiYao on 2017-05-08 11:10.
 */
@SuppressWarnings("WeakerAccess")
public class RowHeader {

    private Sheet sheet;

    private String value;
    private int height = 400;
    private Style style;

    private int mergeFirstRow = 0;
    private int mergeLastRow = 0;
    private int mergeFirstCol = 0;
    private int mergeLastCol = 0;

    public RowHeader(Sheet sheet) {
        this.sheet = sheet;
    }

    public void merge(int mergeFirstRow, int mergeLastRow, int mergeFirstCol, int mergeLastCol) {
        this.mergeFirstRow = mergeFirstRow;
        this.mergeLastRow = mergeLastRow;
        this.mergeFirstCol = mergeFirstCol;
        this.mergeLastCol = mergeLastCol;

        for (int i = mergeFirstRow; i <= mergeLastRow; i++) {
            Row row = this.sheet.getRow(mergeLastRow);
            if (row == null) {
                row = this.sheet.createRow();
            }
            for (int j = mergeFirstCol; j <= mergeLastCol; j++) {
                Cell cell = row.createCell(j);
                cell.setValue(value);
            }

        }
        for (int j = mergeFirstCol; j <= mergeLastCol; j++) {
            this.sheet.createColumn(j);
        }
        // this.sheet.startRow = mergeLastRow;

    }

    public void merge(String mergeFirstRow, String mergeLastRow, String mergeFirstCol, String mergeLastCol) {
        int mfr = StringUtils.isBlank(mergeFirstRow) ? 0 : Integer.parseInt(mergeFirstRow);
        int mlr = StringUtils.isBlank(mergeLastRow) ? 0 : Integer.parseInt(mergeLastRow);
        int mfc = StringUtils.isBlank(mergeFirstCol) ? 0 : Integer.parseInt(mergeFirstCol);
        int mlc = StringUtils.isBlank(mergeLastCol) ? 0 : Integer.parseInt(mergeLastCol);
        this.merge(mfr, mlr, mfc, mlc);

    }

    public int getMergeFirstRow() {
        return mergeFirstRow;
    }

    public int getMergeLastRow() {
        return mergeLastRow;
    }

    public int getMergeFirstCol() {
        return mergeFirstCol;
    }

    public int getMergeLastCol() {
        return mergeLastCol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        if (style != null && StringUtils.isBlank(style.getAlign())) {
            style.setAlign("CENTER");
        }
        this.style = style;
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
}
