package org.zaham.jexcel.factory;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zaham.jexcel.exception.UnsupportedFileException;

import java.io.FileInputStream;

@UtilityClass
public class WorkBookFactory {

    @SneakyThrows
    public static Workbook buildWorkBook(String fileName , FileInputStream fileInputStream){
        switch (fileName) {
            case "xlsx" : return new XSSFWorkbook(fileInputStream);
            case "xls" : return new HSSFWorkbook(fileInputStream);
            default : throw new UnsupportedFileException("unsupported file extension for file:" + fileName);
        }
    }
}
