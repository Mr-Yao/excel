package me.ly.tools.excel.imp.validator;

import me.ly.tools.excel.imp.entity.Cell;

/**
 * 浮点数验证器
 *
 * @author Created by LiYao on 2017-07-27 10:38.
 */
public class DecimalValidator extends NumberValidator {

    public DecimalValidator(Cell cell) {
        super(cell);
    }

    @Override
    protected String errorMsg() {
        return "浮点数格式错误";
    }
}
