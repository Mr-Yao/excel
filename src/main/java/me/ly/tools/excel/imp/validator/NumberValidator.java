package me.ly.tools.excel.imp.validator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import me.ly.tools.excel.imp.entity.Cell;
import org.apache.commons.lang3.StringUtils;

/**
 * 数值验证器
 *
 * @author Created by LiYao on 2017-05-16 14:02.
 */
public class NumberValidator extends AbstractValidator {

    protected String format;
    private DecimalFormat numberFormat;

    private static final Pattern PATTERN = Pattern.compile("^0|-?([1-9]\\d*|[1-9]\\d*\\.\\d+|0\\.\\d+)$");

    public NumberValidator(Cell cell) {
        super(cell);
        this.format = cell.getFormat();
        if (StringUtils.isBlank(format)) {
            format = "0.###############";
        }
        this.numberFormat = createDecimalFormat();
    }

    @Override
    protected boolean validateFormat() {
        if (this.valueIsBlank) {
            return true;
        }
        if (!isNumber(this.value)) {
            return false;
        }
        try {
            Number num = numberFormat.parse(this.value);
            this.result = numberFormat.format(num);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected String errorMsg() {
        return "数值格式错误";
    }

    protected boolean isNumber(String str) {
        return PATTERN.matcher(str).matches();
    }

    protected DecimalFormat createDecimalFormat() {
        DecimalFormat numberFormat = new DecimalFormat(format);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return numberFormat;
    }
}
