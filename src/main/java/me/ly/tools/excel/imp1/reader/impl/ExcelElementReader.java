package me.ly.tools.excel.imp1.reader.impl;

import me.ly.tools.core.utils.Assert;
import me.ly.tools.excel.imp1.reader.ElementReader;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.List;

/**
 * ExcelXmlReader
 *
 * @author Created by LiYao on 2017-09-21 16:36.
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class ExcelElementReader implements ElementReader {

    private Element excelElement;

    public ExcelElementReader(String templatePath) throws DocumentException {
        this(new ClassPathXmlReader(templatePath));
    }

    public ExcelElementReader(ClassPathXmlReader classPathXmlReader) {
        Assert.notNull(classPathXmlReader, "[classPathXmlReader] - this argument is required; it must not be null");
        excelElement = classPathXmlReader.getRootElement();
    }

    public Element getExcelElement() {
        return excelElement;
    }

    public String getExcelKey() {
        return excelElement.attributeValue("key");
    }

    public String getExcelName() {
        return excelElement.attributeValue("name");
    }

    public List<?> sheetElementList() {
        return excelElement.elements("sheet");
    }
}
