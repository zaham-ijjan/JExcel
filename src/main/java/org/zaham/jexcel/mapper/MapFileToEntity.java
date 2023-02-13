package org.zaham.jexcel.mapper;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface MapFileToEntity<T> {
    void mapEntityToFile(Sheet sheet, List<T> entities, boolean firstRowHeader);
}