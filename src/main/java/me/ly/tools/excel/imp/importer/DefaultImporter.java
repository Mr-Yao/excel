package me.ly.tools.excel.imp.importer;

import me.ly.tools.excel.imp.entity.Sheet;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认导入器
 *
 * @author Created by LiYao on 2017-05-17 16:24.
 */
public class DefaultImporter extends AbstractImporter {

    public DefaultImporter(ImportConfiguration conf, Sheet sheet, HttpServletRequest request) {
        super(conf, sheet, request);
    }
}
