package me.ly.tools.excel.imp.utils;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ly.tools.excel.imp.entity.RowHeader;
import me.ly.tools.excel.entity.Style;
import me.ly.tools.excel.imp.entity.*;
import me.ly.tools.excel.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * excel模版工具类
 *
 * @author Created by LiYao on 2017-05-08 17:42.
 */
@SuppressWarnings("JavaDoc")
public class ExcelTemplateUtil {

    private static final String IMPORT_PATH = "/import/";

    public static void createTemplate(Excel excelEntity, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();

        List<Sheet> sheetList = excelEntity.getAllSheet();

        for (Sheet sheet : sheetList) {

            XSSFSheet xssfSheet = wb.createSheet(sheet.getName());

            RowHeader[] rowHeaderList = sheet.getAllRowHeader();

            Column[] columnList = sheet.getAllColumn();

            // 行号
            int rowNum = xssfSheet.getLastRowNum();

            for (RowHeader rowHeader : rowHeaderList) {
                rowNum = ExcelUtil.createSetMergeCell(xssfSheet, rowHeader);
            }
            // 存在合并区域
            if (xssfSheet.getNumMergedRegions() > 0 || sheet.getRow(rowNum) != null) {
                rowNum++;
            }
            // 创建Excel每列的列名
            XSSFRow row = xssfSheet.createRow(rowNum);
            row.setHeight((short) 400);
            for (int i = 0; i < columnList.length; i++) {
                Column column = columnList[i];

                XSSFCellStyle cellStyle = ExcelUtil.createCellStyle(wb, column.getStyle());
                // cellStyle.setWrapText(true);
                // 创建一个Excel的单元格
                org.apache.poi.ss.usermodel.Cell cell = CellUtil.getCell(row, i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(column.getValue());
                // 列宽
                if (column.getWidth() > 0) {
                    xssfSheet.setColumnWidth(i, column.getWidth());
                } else {
                    // 列宽自适应
                    xssfSheet.autoSizeColumn(i, true);
                }
            }
        }

        try {
            wb.write(out);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * 读取 EXCEL XML模版
     * 
     * @param templateName 名称
     * @return Excel
     * @throws Exception
     */
    public static Excel readExcelTemplate(String templateName) throws Exception {

        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        saxReader.setIncludeExternalDTDDeclarations(false);
        saxReader.setIncludeInternalDTDDeclarations(false);
        Document excelXml = saxReader.read(ExcelTemplateUtil.class.getResourceAsStream(IMPORT_PATH + templateName));

        Excel excelEntity = new Excel();
        Element excelElement = excelXml.getRootElement();

        excelEntity.setKey(excelElement.attributeValue("key"));
        excelEntity.setName(excelElement.attributeValue("name"));

        List<?> xmlSheetList = excelElement.elements("sheet");
        for (Object obj : xmlSheetList) {
            Element sheetElement = (Element) obj;
            Sheet sheetEntity = excelEntity.createSheet();

            @SuppressWarnings("unchecked")
            Map<String, String> propertyMap = createProperties(sheetElement.elements("property"));

            setSheetEntity(sheetEntity, sheetElement);
            createRowHeader(sheetEntity, sheetElement.element("rowHeader"), propertyMap);
            createColumn(sheetEntity, sheetElement.element("columns"), propertyMap);

            // Element rowFooterEle = sheetElement.element("rowFooter");

        }
        return excelEntity;

    }

    private static Map<String, String> createProperties(List<Element> propertyEles) throws Exception {
        Map<String, String> map = new HashMap<>();

        for (Element propertyEle : propertyEles) {
            String key = propertyEle.attributeValue("name");
            if (StringUtils.isBlank(key)) {
                continue;
            }

            String value = propertyEle.attributeValue("value");
            if (StringUtils.isNotBlank(value)) {
                map.put(key, value);
                continue;
            }
            String fullMethodName = propertyEle.attributeValue("value-ref");
            int lastPointIndex = fullMethodName.lastIndexOf(".");
            String fullClazz = fullMethodName.substring(0, lastPointIndex);
            String methodName = fullMethodName.substring(lastPointIndex + 1, fullMethodName.length());

            Class<?> clazz = Class.forName(fullClazz);
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            String str = (String) method.invoke(null);
            map.put(key, str);

        }
        return map;
    }

    private static void createColumn(Sheet sheetEntity, Element columnsEle, Map<String, String> propertyMap) {
        List<?> xmlColumnList = columnsEle.elements("column");
        int i = 1;
        sheetEntity.createRow();
        for (Object obj : xmlColumnList) {
            Element columnEle = (Element) obj;

            Column columnEntity = sheetEntity.createColumn(i++);
            Style style = createStyle(columnEle);
            columnEntity.setStyle(style);

            columnEntity.setName(columnEle.attributeValue("name"));
            columnEntity.setCode(columnEle.attributeValue("code"));
            columnEntity.setDataType(columnEle.attributeValue("dataType"));
            columnEntity.setColNumber(columnEle.attributeValue("colNumber"));
            columnEntity.setValue(attributeValue("value", columnEle, propertyMap));
            columnEntity.setFormat(columnEle.attributeValue("format"));
            columnEntity.setWidth(columnEle.attributeValue("width"));
            columnEntity.setRequired(columnEle.attributeValue("required"));
            columnEntity.setAllowSkip(columnEle.attributeValue("allowSkip"));
            columnEntity.setLength(columnEle.attributeValue("length"));
            columnEntity.setRegex(attributeValue("regex", columnEle, propertyMap));
            columnEntity.setValueList(attributeValue("list", columnEle, propertyMap));

            if (StringUtils.isBlank(columnEntity.getValue())) {
                columnEntity.setValue(columnEntity.getName());
            }
            columnEntity.createExtendsCell();
        }
    }

    private static String attributeValue(String name, Element columnEle, Map<String, String> propertyMap) {
        String value = columnEle.attributeValue(name);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        String key = columnEle.attributeValue(name + "-ref");
        return propertyMap.get(key);

    }

    private static void createRowHeader(Sheet sheetEntity, Element columnsEle, Map<String, String> propertyMap) {
        if (columnsEle == null) {
            return;
        }
        List<?> xmlColumnList = columnsEle.elements("column");
        for (Object obj : xmlColumnList) {
            Element columnEle = (Element) obj;
            RowHeader rowHeaderEntity = sheetEntity.createRowHeader();

            Style style = createStyle(columnEle);

            rowHeaderEntity.setStyle(style);
            rowHeaderEntity.setValue(attributeValue("value", columnEle, propertyMap));
            rowHeaderEntity.setHeight(columnEle.attributeValue("height"));

            String mfr = columnEle.attributeValue("mergeFirstRow");
            String mlr = columnEle.attributeValue("mergeLastRow");
            String mfc = columnEle.attributeValue("mergeFirstCol");
            String mlc = columnEle.attributeValue("mergeLastCol");
            rowHeaderEntity.merge(mfr, mlr, mfc, mlc);

        }
    }

    private static Style createStyle(Element columnEle) {
        Style style = new Style();
        style.setBgcolor(columnEle.attributeValue("bgColor"));
        style.setFont(columnEle.attributeValue("font"));
        style.setFontSize(columnEle.attributeValue("fontSize"));
        style.setFontColor(columnEle.attributeValue("fontColor"));
        style.setBold(columnEle.attributeValue("bold"));
        style.setUnderline(columnEle.attributeValue("underline"));
        style.setItalic(columnEle.attributeValue("italic"));
        style.setAlign(columnEle.attributeValue("align"));
        style.setVertical(columnEle.attributeValue("vertical"));
        return style;
    }

    private static void setSheetEntity(Sheet sheetEntity, Element sheetElement) throws Exception {
        sheetEntity.setEntityClass(sheetElement.attributeValue("entity"));
        sheetEntity.setHandlerClass(sheetElement.attributeValue("importHandler"));
        sheetEntity.setName(sheetElement.attributeValue("name"));
        sheetEntity.setReadStartRow(sheetElement.attributeValue("startRow"));
    }
}
