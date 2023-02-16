package org.zaham.jexcel.mapper;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.zaham.jexcel.util.FileUtil.getResourceFileAsString;

@ExtendWith(MockitoExtension.class)
class MapEntityToFileImplTest {



    @Test
    @SneakyThrows
    void mapEntityToFile() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;
        Workbook xssfWorkbook = WorkBookFactory.buildWorkBook(excelType,null);
        Sheet sheet =  xssfWorkbook.createSheet();
        MapEntityToFile<Employees> mapEntityToFile = new MapEntityToFileImpl<>();

        //when
        mapEntityToFile.mapEntityToFile(sheet,employeesList,enableColumn);
        //then
        assertNotNull(
                sheet
        );

    }

    @Test
    @SneakyThrows
    void mapEntityToFileWithoutColumn() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees, Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = false;
        Workbook xssfWorkbook = WorkBookFactory.buildWorkBook(excelType, null);
        Sheet sheet = xssfWorkbook.createSheet();
        MapEntityToFile<Employees> mapEntityToFile = new MapEntityToFileImpl<>();

        //when
        mapEntityToFile.mapEntityToFile(sheet, employeesList, enableColumn);
        //then
        assertNotNull(
                sheet
        );

    }
}