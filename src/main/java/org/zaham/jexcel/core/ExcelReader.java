package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.mapper.MapFileToEntityImpl;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.List;

public class ExcelReader {

    public final MapFileToEntityImpl mapFileToEntityImpl = new MapFileToEntityImpl();

    @SneakyThrows
    public <T> List<T> readExcel(String path, Class<T> clazz){
        String fileName = Paths.get(path).getFileName().toString();
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Workbook workbook = WorkBookFactory.buildWorkBook(fileName , fileInputStream);
        return mapFileToEntityImpl.map(workbook,clazz);
    }
}
