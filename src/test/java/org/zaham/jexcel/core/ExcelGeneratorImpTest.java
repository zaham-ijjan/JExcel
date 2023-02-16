package org.zaham.jexcel.core;

import com.google.gson.Gson;
import lombok.SneakyThrows;
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
        excelGenerator.setMapEntityToFile(mapEntityToFile);
    }

    @Test
    void writeEntityToFile() {
        //given
        String employees = getResourceFileAsString();
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;

        //when
        doNothing().when(mapEntityToFile).mapEntityToFile(any(),any(),any());
        excelGenerator.writeEntityToFile(employeesList,excelType,path,enableColumn);

        //then
        verify(mapEntityToFile,times(1)).mapEntityToFile(any(),any(),any());

    }

    @Test
    void writeEntityToByteArray() {
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