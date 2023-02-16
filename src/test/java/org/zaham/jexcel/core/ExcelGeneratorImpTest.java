package org.zaham.jexcel.core;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.mapper.MapEntityToFileImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcelGeneratorImpTest {

   @Mock
   private MapEntityToFileImpl<Employees> mapEntityToFile;;
   @InjectMocks
   private ExcelGeneratorImp<Employees> excelGenerator;

    @BeforeEach
    void setUp() {
        mapEntityToFile = mock(MapEntityToFileImpl.class);
        excelGenerator = new ExcelGeneratorImp<>(mapEntityToFile);
    }

    @Test
    @SneakyThrows
    void writeEntityToFile() {
        //given
        String employees = getResourceFileAsString();
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        Sheet sheet =  xssfWorkbook.createSheet();

        //when
        doNothing().when(mapEntityToFile).mapEntityToFile(any(),eq(employeesList) ,eq(enableColumn));
        excelGenerator.writeEntityToFile(employeesList,excelType,path,enableColumn);

        //then
        verify(mapEntityToFile,times(1)).mapEntityToFile(any(),eq(employeesList) ,eq(enableColumn));

    }

    @Test
    @SneakyThrows
    void writeEntityToByteArray() {
        //given
        String employees = getResourceFileAsString();
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;

        //when
        doNothing().when(mapEntityToFile).mapEntityToFile(any(),eq(employeesList) ,eq(enableColumn));
        excelGenerator.writeEntityToByteArray(employeesList,excelType,enableColumn);

        //then
        verify(mapEntityToFile,times(1)).mapEntityToFile(any(),eq(employeesList) ,eq(enableColumn));
    }

    @SneakyThrows
    static String getResourceFileAsString() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("excel/employees.json")) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}