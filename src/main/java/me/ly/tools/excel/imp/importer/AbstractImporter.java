package me.ly.tools.excel.imp.importer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import me.ly.tools.core.utils.ApplicationContextUtil;
import me.ly.tools.excel.imp.entity.*;
import me.ly.tools.excel.imp.handler.ImportHandler;
import me.ly.tools.excel.imp.utils.ImpRecordUtil;
import me.ly.tools.excel.imp.utils.ProgressUtil;
import me.ly.tools.excel.imp.validator.DataValidator;
import me.ly.tools.excel.imp.validator.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 抽象导入器
 *
 * @author Created by LiYao on 2017-05-17 10:57.
 */
public class AbstractImporter implements Importer {

    private Logger logger = LoggerFactory.getLogger(AbstractImporter.class);

    protected ImportConfiguration conf;
    protected HttpServletRequest request;
    protected Sheet sheet;
    protected ImportHandler importHandler;
    protected Map<String, DataValidator> validator;
    protected String progressKey;
    protected ProgressInfo progress;

    public AbstractImporter(ImportConfiguration conf, Sheet sheet, HttpServletRequest request) {
        this.conf = conf;
        this.request = request;
        this.sheet = sheet;
        init();
    }

    private void init() {

        int titleRowIndex = sheet.getReadStartRow() - 1;
        titleRowIndex = titleRowIndex > 0 ? titleRowIndex : 1;

        Cell[] cells = sheet.getRow(titleRowIndex).getAllCell();
        if (cells == null || cells.length <= 0) {
            return;
        }
        this.validator = new HashMap<>();
        for (Cell cell : cells) {
            validator.put(cell.getCode(), ValidatorFactory.createValidator(cell));
        }
    }

    private void initProgress() {
        progress = ProgressUtil.getSheetProgress(progressKey, this.conf.getFileName(), sheet.getName());
        if (progress == null) {
            progress = new ProgressInfo();
        }
        progress.setSheetName(this.sheet.getName());
        progress.setTotalRowNum(this.sheet.rowSize());
        progress.setProcessed(0);
        progress.setMaxCol(this.sheet.colSize());
        progress.setFileSize(this.conf.getFileSize());
        progress.setStartDate(this.conf.getImportDate());
        ProgressUtil.setProgress(progressKey, this.conf.getFileName(), progress);
    }

    public void addError(String msg, int rowNum, int colNum) {
        ImportError error = new ImportError(sheet.getName(), rowNum, colNum, msg);
        progress.addError(error);
    }

    protected void initImportHandler() throws Exception {
        Class<?> clazz = this.sheet.getHandlerClass();
        if (clazz == null) {
            return;
        }
        String objName = StringUtils.uncapitalize(clazz.getSimpleName());
        Component annotation = clazz.getAnnotation(Component.class);
        if (StringUtils.isNotBlank(annotation.value())) {
            objName = annotation.value();
        }
        importHandler = (ImportHandler) ApplicationContextUtil.getBean(objName);
        importHandler.init(conf, sheet, request);
        importHandler.setAbstractImporter(this);
    }

    public List<ImportError> errorList() {
        return progress.getErrorList();
    }

    @Override
    @SuppressWarnings("all")
    public void importData() {
        logger.debug("import data start......");

        try {
            initImportHandler();

            if (this.importHandler == null) {
                return;
            }
            // 进度信息
            this.progressKey = importHandler.getProgressKey(this.request);
            initProgress();
            this.progress.setFinish(false);
            this.progress.initErrorList();
            this.progress.setProcessed(sheet.getReadStartRow() - 1);
            this.importHandler.start(this.request);

            Row[] rows = this.sheet.getAllRow();

            for (int i = sheet.getReadStartRow(); i <= rows.length; i++) {
                final int rowNum = i;
                // 进度信息
                progress.processedPlus1();
                // progress.setProcessed(rowNum);
                Row row = rows[rowNum - 1];

                importHandler.setRowNumber(rowNum);

                Map<String, String> rowMap = new HashMap<>();
                boolean rowHaveError = false;

                Cell[] cells = row.getAllCell();
                for (Cell cell : cells) {
                    // 在读取时出现异常
                    if (StringUtils.isNotBlank(cell.getErrMsg())) {
                        addError(cell.getErrMsg(), cell.getRowNumber(), cell.getColNumber());
                        rowHaveError = true;
                        continue;
                    }

                    DataValidator tmp = validator.get(cell.getCode());

                    ValidateCell tmpCell = new ValidateCell(cell);
                    if (!tmp.validate(tmpCell)) {
                        rowHaveError = true;
                        progress.addAllError(tmpCell.errorList());
                    } else {
                        rowMap.put(cell.getCode(), tmpCell.getValue());
                    }
                }
                if (rowHaveError) {
                    continue;
                }

                try {
                    importHandler.startRow(rowMap);

                    boolean isPass = importHandler.validateRow(rowMap);

                    if (!isPass) {
                        continue;
                    }

                    Object obj = null;
                    if (sheet.getEntityClass() != null) {
                        obj = JSON.parseObject(JSON.toJSONString(rowMap), sheet.getEntityClass());
                    }
                    importHandler.endRow(rowMap, obj);
                } catch (Exception e) {
                    if (this.importHandler != null) {
                        this.importHandler.exception(e);
                    } else {
                        logger.error("", e);
                    }
                }

                rowMap = null;
            }

            this.importHandler.end();
        } catch (Exception e) {
            if (this.importHandler != null) {
                this.importHandler.exception(e);
            } else {
                logger.error("", e);
            }
        } finally {
            this.progress.setEndDate(new Date());
            this.progress.setFinish(true);
            try {
                ImpRecordUtil.sheetEnd(this.conf.getRecordId(), this.progressKey);
            } catch (Exception e) {
                logger.error("处理【{}-{}】结束时出现异常！", this.conf.getFileName(), this.sheet.getName(), e);
            }
            this.sheet.destroy();
            logger.debug("import data end......");
            System.gc();
        }

    }
}
