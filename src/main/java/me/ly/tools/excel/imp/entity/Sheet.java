package me.ly.tools.excel.imp.entity;

import java.util.List;

import me.ly.tools.excel.entity.Style;
import me.ly.tools.excel.imp.collection.CustomArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 * Sheet
 *
 * @author Created by LiYao on 2017-04-20 21:33.
 */
public class Sheet {

    private String name;
    private Style style;

    // int startRow = 0;// 起始行
    // int startCol = 0;// 起始列

    private int readStartRow = 1;
    private int readStartCol = 1;

    private Class<?> entityClass;
    private Class<?> handlerClass;

    private List<Cell> cellList = new CustomArrayList<>();
    private List<Row> rowList = new CustomArrayList<>();
    private List<Column> columnList = new CustomArrayList<>();
    private List<RowHeader> rowHeaderList = new CustomArrayList<>();

    public RowHeader createRowHeader() {
        RowHeader rowHeader = new RowHeader(this);
        rowHeaderList.add(rowHeader);
        return rowHeader;
    }

    public Row createRow() {
        int rowNum = this.rowList.size() + 1;
        return this.createRow(rowNum);
    }

    public Row createRow(int rowNum) {
        Row row = new Row(this);
        row.setRowNumber(rowNum);
        this.addRow(row);
        return row;
    }

    public Column createColumn() {
        int colNum = this.columnList.size() + 1;
        return this.createColumn(colNum);
    }

    public Column createColumn(int colNum) {
        Column column = new Column(this);
        column.setColNumber(colNum);
        this.addColumn(column);
        return column;
    }

    public Cell createCell(int rowNum, int colNum) {
        if (rowNum <= 0 || colNum <= 0) {
            return null;
        }
        Cell cell = new Cell(this);
        cell.setRowNumber(rowNum);
        cell.setColNumber(colNum);
        this.addCell(cell);
        return cell;
    }

    public void addCell(Cell cell) {
        // int index = cellList.indexOf(cell);
        // if (index > -1) {
        // cellList.set(index, cell);
        // } else {
        // cellList.add(cell);
        // }
        cellList.add(cell);
    }

    public Cell[] getAllCell() {
        return this.cellList.toArray(new Cell[0]);
    }

    public Cell getCell(int rowNum, int colNum) {
        Row row = this.getRow(rowNum);
        Column column = this.getColumn(colNum);

        if (row == null || column == null) {
            return null;
        }

        for (Cell cell : cellList) {
            if (cell.getRowNumber() == rowNum && cell.getColNumber() == colNum) {
                return cell;
            }
        }

        return null;
    }

    public void addRow(Row row) {
        if (row == null) {
            return;
        }
        if (row.getRowNumber() <= 0) {
            return;
        }
        this.rowList.set(row.getRowNumber() - 1, row);
    }

    public Row getRow(int rowNum) {
        if (rowNum < 1) {
            return null;
        }
        return this.rowList.get(rowNum - 1);
    }

    public Row[] getAllRow() {
        return this.rowList.toArray(new Row[0]);
    }

    public void addColumn(Column column) {
        if (column == null) {
            return;
        }

        if (column.getColNumber() <= 0) {
            return;
        }
        int index = column.getColNumber() - 1;

        this.columnList.set(index, column);
    }

    public Column getColumn(int colNum) {
        return this.columnList.get(colNum - 1);
    }

    public Column[] getAllColumn() {
        return this.columnList.toArray(new Column[0]);
    }

    public RowHeader[] getAllRowHeader() {
        return this.rowHeaderList.toArray(new RowHeader[0]);
    }

    public void destroy() {
        cellList = null;
        rowList = null;
        cellList = null;
        rowHeaderList = null;
    }

    /**
     * 单元格数量
     * 
     * @return int
     */
    public int cellSize() {
        return this.cellList.size();
    }

    /**
     * 列数量
     * 
     * @return int
     */
    public int colSize() {
        return this.columnList.size();
    }

    /**
     * 行数量
     * 
     * @return int
     */
    public int rowSize() {
        return this.rowList.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public int getReadStartRow() {
        return readStartRow;
    }

    public void setReadStartRow(int readStartRow) {
        this.readStartRow = readStartRow;
    }

    public int getReadStartCol() {
        return readStartCol;
    }

    public void setReadStartCol(int readStartCol) {
        this.readStartCol = readStartCol;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(Class<?> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public void setEntityClass(String className) throws ClassNotFoundException {
        if (StringUtils.isNotBlank(className)) {
            this.entityClass = Class.forName(className);
        }
    }

    public void setHandlerClass(String className) throws ClassNotFoundException {
        if (StringUtils.isNotBlank(className)) {
            this.handlerClass = Class.forName(className);
        }
    }

    public void setReadStartRow(String readStartRow) {
        if (StringUtils.isNotBlank(readStartRow)) {
            this.readStartRow = Integer.parseInt(readStartRow);
        }
    }
}
