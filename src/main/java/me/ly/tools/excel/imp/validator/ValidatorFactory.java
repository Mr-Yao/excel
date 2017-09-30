package me.ly.tools.excel.imp.validator;

import me.ly.tools.excel.imp.entity.Cell;

/**
 * 校验器工厂
 *
 * @author Created by LiYao on 2017-05-16 14:44.
 */
public class ValidatorFactory {

    public static final String TYPE_STRING = "string";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_INTEGER = "integer";
    public static final String TYPE_DECIMAL = "decimal";
    public static final String TYPE_DATE = "date";

    public static DataValidator createValidator(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (TYPE_STRING.equalsIgnoreCase(cell.getDataType().trim())) {
            return new StringValidator(cell);
        }

        if (TYPE_DATE.equalsIgnoreCase(cell.getDataType().trim())) {
            return new DateValidator(cell);
        }

        if (TYPE_NUMBER.equalsIgnoreCase(cell.getDataType().trim())) {
            return new NumberValidator(cell);
        }

        if (TYPE_INTEGER.equalsIgnoreCase(cell.getDataType().trim())) {
            return new IntegerValidator(cell);
        }

        if (TYPE_DECIMAL.equalsIgnoreCase(cell.getDataType().trim())) {
            return new DecimalValidator(cell);
        }
        return null;
    }

}
