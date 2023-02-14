package org.zaham.jexcel.core;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.JExcelFactory;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ExcelGeneratorImpTest {


    @Test
    @SneakyThrows
    void writeEntity() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "classpath:resources/excel";
        JExcelFactory.excelFileGenerator(employeesList,ExcelType.XLSX,true,path);
    }

    @SneakyThrows
    static String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}