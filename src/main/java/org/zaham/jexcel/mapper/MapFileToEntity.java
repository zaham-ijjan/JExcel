package org.zaham.jexcel.mapper;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface MapFileToEntity {
    <T> void mapEntityToFile(Sheet sheet, List<T> entities);
    <T> List<T> map(Workbook workbook, Class<T> clazz);
}
