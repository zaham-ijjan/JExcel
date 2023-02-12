package org.zaham.jexcel.enums;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;


public enum ExcelCellType {

	/**
	 * General Format
	 */
	GENERAL("General"),

	/**
	 * Number with no decimal digits.
	 */
	INTEGER("0"),

	/**
	 * Number with 2 decimal points.
	 */
	DECIMAL("0.00"),

	/**
	 * Number with a precision of 9 decimal points
	 */
	PRECISE("0.000000000"),

	/**
	 * Use this to display currency digits
	 */
	CURRENCY("#,##0.00"),

	/**
	 * The excel date format displayed as MM/DD/YYYY
	 */
	DATE("m/d/yyyy"),

	/**
	 * The excel timestamp format displayed as MM/DD/YYYY HH:MM:SS
	 */
	DATETIME("m/d/yyyy h:mm:ss AM/PM"),

	/**
	 * Numeric percentages upto 2 decimal places
	 */
	PERCENT("0.00%"),

	/**
	 * Auto-format
	 */
	DEFAULT("");

	private String format;

	ExcelCellType(String format) {
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public short getDataFormat(Workbook wb) {
		return wb.createDataFormat().getFormat(this.format);
	}

	public CellStyle getCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(this.getDataFormat(wb));
		return cellStyle;
	}
}
