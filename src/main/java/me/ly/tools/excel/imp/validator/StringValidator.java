package me.ly.tools.excel.imp.validator;

import me.ly.tools.excel.imp.entity.Cell;

/**
 * 字符串验证器
 *
 * @author Created by LiYao on 2017-05-16 13:59.
 */
public class StringValidator extends AbstractValidator {

    public StringValidator(Cell cell) {
        super(cell);
    }

    @Override
    protected boolean validateFormat() {
        return true;
    }

    @Override
    protected String errorMsg() {
        return "字符格式错误";
    }
}
