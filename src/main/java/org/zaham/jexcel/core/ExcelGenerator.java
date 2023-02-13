package org.zaham.jexcel.core;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface ExcelGenerator<T> {

     void writeEntity(List<T> entities);
     Sheet initSheetName(List<T> entities);
}
