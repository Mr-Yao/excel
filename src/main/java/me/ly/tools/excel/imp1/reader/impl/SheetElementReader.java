package me.ly.tools.excel.imp1.reader.impl;

import me.ly.tools.core.utils.Assert;
import me.ly.tools.excel.imp1.reader.ElementReader;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SheetElementReader
 *
 * @author Created by LiYao on 2017-09-21 17:21.
 */
public class SheetElementReader implements ElementReader {

    private List<?> sheetElementList;

    private Map<String, Map<String, String>> allPropertyMap;

    public SheetElementReader(String templatePath)
            throws DocumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this(new ExcelElementReader(templatePath).sheetElementList());
    }

    public SheetElementReader(ClassPathXmlReader classPathXmlReader)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this(new ExcelElementReader(classPathXmlReader).sheetElementList());
    }

    public SheetElementReader(ExcelElementReader excelElementReader)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this(excelElementReader.sheetElementList());
    }

    public SheetElementReader(List<?> sheetElementList)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Assert.notNull(sheetElementList, "[sheetElementList] - this argument is required; it must not be null");
        this.sheetElementList = sheetElementList;
        allPropertyMap = new HashMap<>((int) (sheetElementList.size() / 0.75));
        init();
    }

    public List<?> getSheetElementList() {
        return sheetElementList;
    }

    public Map<String, Map<String, String>> getAllProperties() {
        return allPropertyMap;
    }

    public Map<String, String> getProperties(String sheetName) {
        return getAllProperties().get(sheetName);
    }

    private void init() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        for (Object obj : sheetElementList) {
            Element sheetElement = (Element) obj;
            String sheetName = sheetElement.attributeValue("name");
            //noinspection unchecked
            allPropertyMap.put(sheetName, createProperties(sheetElement.elements("property")));
        }

    }

    private Map<String, String> createProperties(List<Element> propertyEles)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
}
