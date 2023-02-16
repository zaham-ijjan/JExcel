package org.zaham.jexcel;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.core.ExcelGenerator;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class JExcelFactoryTest {


    @Mock
    private ExcelGenerator<Employees> excelGenerator;

    @InjectMocks
    private JExcelFactory<Employees> jExcelFactory;

    @Test
    @SneakyThrows
    void writeEntity() {
        //given
        String employees = getResourceFileAsString();
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;
        jExcelFactory.excelFileGenerator(employeesList,excelType,enableColumn,path);
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

    @Test
    void writeEntityToFile() {
    }

    @Test
    void writeEntityToByteArray() {
    }
}