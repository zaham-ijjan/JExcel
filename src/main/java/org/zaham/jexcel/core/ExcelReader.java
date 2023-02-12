package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.factory.WorkBookFactory;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.List;

public class ExcelReader {

    @SneakyThrows
    public <T> List<T> readExcel(String path, Class<T> clazz){
        String fileName = Paths.get(path).getFileName().toString();
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Workbook workbook = WorkBookFactory.buildWorkBook(fileName , fileInputStream);

    }
}
