package org.zaham.jexcel.core;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.enums.ExcelType;

import java.util.List;

public interface ExcelGenerator {
    <T> void writeEntity(ExcelType excelType, List<T> entities);
    <T> Sheet buildSheetName(Workbook workbook , List<T> entities);
}
