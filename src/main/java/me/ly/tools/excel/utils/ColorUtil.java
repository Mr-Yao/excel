package me.ly.tools.excel.utils;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * ColorUtil
 *
 * @author Created by LiYao on 2017-05-03 14:46.
 */
@SuppressWarnings("WeakerAccess")
public class ColorUtil {

    private static Map<String, XSSFColor> colorMap = new ConcurrentHashMap<>();

    static {
        getColor(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
        getColor(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue());
        getColor(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue());
        getColor(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue());
    }

    public static XSSFColor getColorByRGBOrHex(String color) {
        if (StringUtils.isBlank(color)) {
            return null;
        }
        if (color.startsWith("#")) {
            return getColorByHex(color);
        }
        if (color.contains(",")) {
            return getColor(color);
        }
        return null;
    }

    public static XSSFColor getColor(String rgb) {
        if (StringUtils.isBlank(rgb)) {
            return null;
        }
        String[] rgbs = rgb.split(",");
        if (rgbs.length < 3) {
            return null;
        }
        int r = StringUtils.isBlank(rgbs[0]) ? 0 : Integer.parseInt(rgbs[0]);
        int g = StringUtils.isBlank(rgbs[1]) ? 0 : Integer.parseInt(rgbs[1]);
        int b = StringUtils.isBlank(rgbs[2]) ? 0 : Integer.parseInt(rgbs[2]);
        return getColor(r, g, b);
    }

    public static XSSFColor getColor(int r, int g, int b) {
        return getColorByHex(RGBToHex(r, g, b));
    }

    public static XSSFColor getColorByHex(String hexColor) {
        hexColor = hexColor.substring(1);
        XSSFColor color = colorMap.get(hexColor);
        if (color != null) {
            return color;
        }
        color = new XSSFColor(new Color(Integer.parseInt(hexColor, 16)));
        colorMap.put(hexColor, color);
        return color;
    }

    public static String RGBToHex(int r, int g, int b) {
        StringBuilder su = new StringBuilder();
        String red = Integer.toHexString(r);
        String green = Integer.toHexString(g);
        String blue = Integer.toHexString(b);
        red = red.length() == 1 ? "0" + red : red;
        green = green.length() == 1 ? "0" + green : green;
        blue = blue.length() == 1 ? "0" + blue : blue;
        su.append("#");
        su.append(red.toUpperCase());
        su.append(green.toUpperCase());
        su.append(blue.toUpperCase());
        return su.toString();

    }

}
