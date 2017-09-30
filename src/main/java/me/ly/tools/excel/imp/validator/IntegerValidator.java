package me.ly.tools.excel.imp.validator;

import me.ly.tools.excel.imp.entity.Cell;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 整数验证器
 *
 * @author Created by LiYao on 2017-07-27 10:38.
 */
public class IntegerValidator extends NumberValidator {

    private static final Pattern PATTERN = Pattern.compile("^0|^-?[1-9]\\d*$");

    public IntegerValidator(Cell cell) {
        super(cell);
    }

    @Override
    protected String errorMsg() {
        return "只能为整数";
    }

    @Override
    protected boolean isNumber(String str) {
        return PATTERN.matcher(str).matches();
    }

    @Override
    protected DecimalFormat createDecimalFormat() {
        DecimalFormat numberFormat = new DecimalFormat("0");
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return numberFormat;
    }
}
