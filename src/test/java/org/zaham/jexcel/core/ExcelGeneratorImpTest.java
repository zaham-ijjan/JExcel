package org.zaham.jexcel.core;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.JExcelFactory;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcelGeneratorImpTest {

    private List<Employees> employeesList;

    @Mock
    private ExcelGenerator<Employees> excelGenerator;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        this.employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
    }

    @Test
    @SneakyThrows
    void writeEntity() {
        //given
        String path = "excel";
        JExcelFactory<Employees> employeesJExcelFactory = new JExcelFactory<>();

        //when
        doNothing().when(excelGenerator).writeEntityToFile(employeesList,path);
        employeesJExcelFactory.excelFileGenerator(employeesList,ExcelType.XLSX,true,path);
        verify(excelGenerator,times(1)).writeEntityToFile(employeesList,path);
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