package org.zaham.jexcel.factory;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.UnsupportedFileException;

import java.io.FileInputStream;

@UtilityClass
public class WorkBookFactory {

    @SneakyThrows
    public static Workbook buildWorkBook(ExcelType excelType , FileInputStream fileInputStream){
        switch (excelType) {
            case XLSX : return fileInputStream !=null ? new XSSFWorkbook(fileInputStream) : new XSSFWorkbook();
            case XLS : return  fileInputStream !=null ? new HSSFWorkbook(fileInputStream) : new HSSFWorkbook();
            default : throw new UnsupportedFileException("unsupported extension of :" + "extension");
        }
    }
}
