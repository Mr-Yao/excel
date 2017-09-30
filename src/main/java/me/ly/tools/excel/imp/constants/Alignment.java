package me.ly.tools.excel.imp.constants;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * 对齐方式
 *
 * @author Created by LiYao on 2017-05-10 9:30.
 */
public enum Alignment {

	GENERAL(HSSFCellStyle.ALIGN_GENERAL),

	ALIGN_LEFT(HSSFCellStyle.ALIGN_LEFT),
	ALIGN_CENTER(HSSFCellStyle.ALIGN_CENTER),
	ALIGN_RIGHT(HSSFCellStyle.ALIGN_RIGHT),

	VERTICAL_TOP(HSSFCellStyle.VERTICAL_TOP),
	VERTICAL_CENTER(HSSFCellStyle.VERTICAL_CENTER),
	VERTICAL_BOTTOM(HSSFCellStyle.VERTICAL_BOTTOM),

	;

	private int code;

	Alignment(int code) {
		this.code = code;
	}

	public int code() {
		return this.code;
	}

	public static int code(String name) {
		if (StringUtils.isBlank(name)) {
			return Alignment.GENERAL.code();
		}
		Alignment[] alignments = Alignment.values();
		for (Alignment alignment : alignments) {
			if (alignment.name().equalsIgnoreCase(name)) {
				return alignment.code();
			}
		}

		return Alignment.GENERAL.code();
	}

}
