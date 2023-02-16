package org.zaham.jexcel.factory;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.UnsupportedFileException;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class WorkBookFactoryTest {


    @Test
    @SneakyThrows
    void buildWorkBook() {
        //given
        ExcelType excelType1 = ExcelType.XLSX;
        ExcelType excelType2 = ExcelType.XLS;
        InputStream byteArry = getClass().getClassLoader().getResourceAsStream("classpath:excel/Employees.xlsx");

        //when
        Workbook workbookXlSXNull = WorkBookFactory.buildWorkBook(excelType1, null);
        Workbook workbookXlSXNonNull = WorkBookFactory.buildWorkBook(excelType1, byteArry);
        Workbook workbookXlSNull = WorkBookFactory.buildWorkBook(excelType2, null);
        Workbook workbookXlSNonNull = WorkBookFactory.buildWorkBook(excelType2, byteArry);

        //then
        assertTrue(workbookXlSXNull instanceof XSSFWorkbook);
        assertTrue(workbookXlSXNonNull instanceof XSSFWorkbook);
        assertTrue(workbookXlSNull instanceof HSSFWorkbook);
        assertTrue(workbookXlSNonNull instanceof HSSFWorkbook);
    }
}