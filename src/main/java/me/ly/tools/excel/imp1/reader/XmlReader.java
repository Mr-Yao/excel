package me.ly.tools.excel.imp1.reader;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * XmlReader
 *
 * @author Created by LiYao on 2017-09-21 15:39.
 */
public interface XmlReader extends Reader {

    Document getDocument();

    Element getRootElement();
}
