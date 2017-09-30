package me.ly.tools.excel.imp.constants;

/**
 * wtyh
 *
 * @author Created by LiYao on 2017-07-19 15:48.
 */
public enum ImpRecord {

	BLANK(-1, ""),
	IMP(1, "已上传"),
	ING(2, "处理中"),
	OK(3, "成功"),
	FAIL(4, "失败");

	private int state;
	private String desc;

	ImpRecord(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	public int state() {
		return this.state;
	}

	public String desc() {
		return this.desc;
	}

	public static ImpRecord getRecord(Integer type) {
		if (type == null) {
			return ImpRecord.BLANK;
		}
		ImpRecord[] values = ImpRecord.values();
		for (ImpRecord ir : values) {
			if (type.equals(ir.state())) {
				return ir;
			}
		}
		return ImpRecord.BLANK;
	}

}
