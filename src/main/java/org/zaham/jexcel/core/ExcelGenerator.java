package org.zaham.jexcel.core;

import org.apache.poi.ss.usermodel.Sheet;

import java.nio.file.Path;
import java.util.List;

public interface ExcelGenerator<T> {

     void writeEntityToFile(List<T> entities , String path);
     byte[]  writeEntityToByteArray(List<T> entities);
     Sheet initSheetName(List<T> entities);
}
