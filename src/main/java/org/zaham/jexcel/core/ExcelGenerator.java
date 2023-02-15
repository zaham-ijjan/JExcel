package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.enums.ExcelType;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

public interface ExcelGenerator<T> {

     void writeEntityToFile(List<T> entities , String path);
     OutputStream writeEntityToByteArray(List<T> entities);
     Sheet initSheetName(List<T> entities);

     Sheet initSheetName(List<T> entities, Workbook workbook);

     @SneakyThrows
     void writeEntityToFile(List<T> entities, String path, ExcelType excelType);

     @SneakyThrows
     void writeEntityToFile(List<T> entities, String path, boolean enable, ExcelType excelType);

     @SneakyThrows
     OutputStream writeEntityToByteArray(List<T> entities, ExcelType excelType);
}
