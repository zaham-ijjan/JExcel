package org.zaham.jexcel;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zaham.jexcel.core.pojo.Employees;
import org.zaham.jexcel.enums.ExcelType;

import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.zaham.jexcel.util.FileUtil.getResourceFileAsString;

@ExtendWith(MockitoExtension.class)
class JExcelTest {


    @Test
    @SneakyThrows
    @Disabled("we dont want to generate the file in hard disk enbale it only in dev")
    void writeEntityWithFile() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        String path = "";
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;
        JExcel<Employees> jExcel = new JExcel<>();
        File File =  jExcel.excelFileGenerator(employeesList,excelType,enableColumn,path).get();
        assertNotNull(File);
    }

    @Test
    @SneakyThrows
    void writeEntityWithOutStream() {
        //given
        String employees = getResourceFileAsString("excel/employees.json");
        Gson gson = new Gson();
        List<Employees> employeesList = Arrays.asList(gson.fromJson(employees,Employees[].class));
        ExcelType excelType = ExcelType.XLSX;
        boolean enableColumn = true;
        JExcel<Employees> jExcel = new JExcel<>();
        OutputStream outputStream =  jExcel.excelByteArrayGenerator(employeesList,excelType,enableColumn).get();
        assertNotNull(outputStream);
    }
}