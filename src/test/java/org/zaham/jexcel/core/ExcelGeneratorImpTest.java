package org.zaham.jexcel.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExcelGeneratorImpTest {


    @Test
    void writeEntity() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        JExcelFactory.excelGenerator(employeesList,ExcelType.XLSX,true);
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