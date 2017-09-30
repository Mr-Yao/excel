package me.ly.tools.excel.entity;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * Style
 *
 * @author Created by LiYao on 2017-05-03 14:00.
 */
@SuppressWarnings("WeakerAccess")
public class Style {

    private String bgcolor;

    private String font = "宋体";
    private int fontSize;
    private String fontColor;
    private boolean bold;// 加粗
    private boolean underline;// 下划线
    private boolean italic;// 斜体
    private String align = "CENTER";// LEFT/RIGHT/CENTER
    private String vertical = "CENTER";// TOP/BOTTOM/CENTER

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Style style = (Style) o;

        if (fontSize != style.fontSize)
            return false;
        if (bold != style.bold)
            return false;
        if (underline != style.underline)
            return false;
        if (italic != style.italic)
            return false;
        if (bgcolor != null ? !bgcolor.equals(style.bgcolor) : style.bgcolor != null)
            return false;
        if (font != null ? !font.equals(style.font) : style.font != null)
            return false;
        if (fontColor != null ? !fontColor.equals(style.fontColor) : style.fontColor != null)
            return false;
        //noinspection SimplifiableIfStatement
        if (align != null ? !align.equals(style.align) : style.align != null)
            return false;
        return vertical != null ? vertical.equals(style.vertical) : style.vertical == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bgcolor, font, fontSize, fontColor, bold, underline, italic, align, vertical);
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        if (StringUtils.isNotEmpty(font)) {
            this.font = font;
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontSize(String fontSize) {
        if (StringUtils.isNotBlank(fontSize)) {
            this.setFontSize(Integer.parseInt(fontSize));
        }
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setBold(String bold) {
        this.setBold(Boolean.parseBoolean(bold));
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void setUnderline(String underline) {
        this.setUnderline(Boolean.parseBoolean(underline));
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setItalic(String italic) {
        this.setItalic(Boolean.parseBoolean(italic));
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        if (StringUtils.isNotBlank(align)) {
            this.align = align;
        }
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        if (StringUtils.isNotBlank(vertical)) {
            this.vertical = vertical;
        }
    }
}
