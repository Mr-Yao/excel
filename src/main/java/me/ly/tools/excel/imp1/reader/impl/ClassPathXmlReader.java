package me.ly.tools.excel.imp1.reader.impl;

import me.ly.tools.core.utils.Assert;
import me.ly.tools.excel.imp1.reader.XmlReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ClassPathXmlReader
 *
 * @author Created by LiYao on 2017-09-21 15:47.
 */
@SuppressWarnings("WeakerAccess")
public class ClassPathXmlReader implements XmlReader {

    private Document document;

    public ClassPathXmlReader(String templatePath) throws DocumentException {
        Assert.notBlank(templatePath, "config location argument is required,it must not be null or empty");
        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        saxReader.setIncludeExternalDTDDeclarations(false);
        saxReader.setIncludeInternalDTDDeclarations(false);
        document = saxReader.read(this.getClass().getResourceAsStream(templatePath));
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public Element getRootElement() {
        return document.getRootElement();
    }
}
