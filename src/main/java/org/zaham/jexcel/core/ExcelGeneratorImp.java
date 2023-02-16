package org.zaham.jexcel.core;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.annotation.inject.Autowired;
import org.zaham.jexcel.annotation.inject.Component;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.functional.FourthFunction;
import org.zaham.jexcel.mapper.MapEntityToFile;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Component
@Slf4j
@Setter
public class ExcelGeneratorImp<T> implements ExcelGenerator<T>{

    @Autowired
    private MapEntityToFile<T> mapEntityToFile;
    private Class<T> clazz ;
    private ExcelType excelType;
    private String path ;
    private  Workbook workbook;
    private Sheet sheet;
    private boolean enableColumn = true;

    FourthFunction<List<T>, String,Boolean, ExcelType,List<T>> initSheet = (ts, path, enable,type) -> {
        this.clazz =(Class<T>) ts.get(0).getClass();
        this.enableColumn = enable;
        this.excelType = type;
        this.workbook = WorkBookFactory.buildWorkBook(excelType,null);
        this.path = path;
        return ts;
    };

    UnaryOperator<List<T>> initSheetName = entities ->{
        if (entities != null && !entities.isEmpty()) {
            String defaultSheetName = clazz.getSimpleName();
            if (clazz.isAnnotationPresent(ExcelEntity.class)) {
                ExcelEntity excelEntity = clazz.getAnnotation(ExcelEntity.class);
                sheet = !excelEntity.sheetName().equals("") ? workbook.createSheet(excelEntity.sheetName()) : workbook.createSheet(defaultSheetName);
            }
        }
        return entities;
    };


    Function<List<T>,Void> writeEntityToFile =  entities -> {
        String fileName = path + clazz.getSimpleName() + "." + excelType.getType();
        mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    };

    Function<List<T>,ByteArrayOutputStream> writeEntityToOuputStream = entities ->{
        mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream;
    };



    @SneakyThrows
    @Override
    public void writeEntityToFile(List<T> entities,ExcelType excelType, String path, boolean enable ) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        initSheet
                .andThen(initSheetName)
                .andThen(writeEntityToFile)
                .apply(entities,path,enable,excelType);

    }

    @Override
    @SneakyThrows
    public OutputStream writeEntityToByteArray(List<T> entities, ExcelType excelType, boolean enable ) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        initSheet
                .andThen(initSheetName)
                .andThen(writeEntityToOuputStream)
                .apply(entities,path,enable,excelType);
        return null;
    }
}
