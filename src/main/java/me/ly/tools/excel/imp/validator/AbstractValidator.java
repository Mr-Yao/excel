package me.ly.tools.excel.imp.validator;

import java.util.regex.Pattern;

import me.ly.tools.excel.imp.entity.Cell;
import me.ly.tools.excel.imp.entity.ValidateCell;
import org.apache.commons.lang3.StringUtils;

/**
 * 抽象验证器
 *
 * @author Created by LiYao on 2017-05-16 10:35.
 */
public abstract class AbstractValidator implements DataValidator {

    protected String sheetName;
    protected String name;
    protected String code;
    protected int minLength;
    protected int maxLength;
    protected Pattern regex;
    protected String value;
    protected String valueList;
    protected String result;
    protected boolean allowSkip;
    protected boolean required;
    protected boolean valueIsBlank;

    public AbstractValidator(Cell cell) {
        this.sheetName = cell.getSheet().getName();
        this.name = cell.getName();
        this.code = cell.getCode();
        this.value = cell.getValue();
        this.valueList = cell.getValueList();
        this.minLength = cell.getMinLength();
        this.maxLength = cell.getMaxLength();
        this.allowSkip = cell.isAllowSkip();
        this.required = cell.isRequired();
        if (StringUtils.isNotBlank(cell.getRegex())) {
            this.regex = Pattern.compile(cell.getRegex());
        }
    }

    protected boolean validateRequired() {
        return !this.required || !this.valueIsBlank;

    }

    protected boolean validateLength() {
        if (minLength == 0 && maxLength == 0) {
            return true;
        }
        if (valueIsBlank) {
            return false;
        }
        if (minLength > 0 && maxLength == 0) {
            return value.length() >= minLength;
        }
        if (minLength == 0 && maxLength > 0) {
            return value.length() <= maxLength;
        }
        return minLength > 0 && maxLength > 0 && minLength <= value.length() && value.length() <= maxLength;
    }

    protected boolean validateRegex() {
        return this.regex == null || !this.valueIsBlank && this.regex.matcher(this.value).matches();
    }

    protected abstract boolean validateFormat();

    protected abstract String errorMsg();

    @Override
    public boolean validate(ValidateCell cell) throws Exception {
        this.result = null;
        String value = cell.getValue();
        // 允许忽略
        if (this.allowSkip) {
            return true;
        }

        this.value = value.trim();
        this.valueIsBlank = StringUtils.isBlank(this.value);
        this.valueList = cell.getValueList();

        if (!validateRequired()) {
            cell.addError(this.name + " 该值是必须的");
            return false;
        }

        if (valueIsBlank) {
            cell.setValue("");
            return true;
        }

        boolean flag = true;
        if (!validateLength()) {
            flag = false;
            String msg = "";
            if (minLength != 0) {
                msg = " 最小长度应为：" + minLength;
            }
            if (maxLength != 0) {
                msg = " 最大长度应为：" + maxLength;
            }
            if (minLength != 0 && maxLength != 0) {
                msg = " 长度区间应为：" + minLength + "~" + maxLength;
            }
            cell.addError(this.name + msg);
        }
        if (!validateRegex()) {
            flag = false;
            cell.addError(this.name + " 格式错误");
        }
        if (!validateFormat()) {
            flag = false;
            cell.addError(this.name + " " + errorMsg());
        }

        if (!validateValueList(this.value, this.valueList)) {
            flag = false;
            cell.addError(this.name + " 应从[" + this.valueList + "]中取值");
        }

        cell.setValue(this.result == null ? this.value : this.result);
        return flag;
    }

    private boolean validateValueList(String value, String valueList) {
        if (StringUtils.isBlank(valueList)) {
            return true;
        }
        String[] values = valueList.split(",");

        for (String v : values) {
            if (value.equals(v)) {
                return true;
            }
        }
        return false;
    }

}
