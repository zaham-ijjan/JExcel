package org.zaham.jexcel.core;

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

@Component
@Slf4j
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

    Function<List<T>,List<T>> initSheetName = entities ->{
        if (entities != null && !entities.isEmpty()) {
            String defaultSheetName = clazz.getSimpleName();
            if (clazz.isAnnotationPresent(ExcelEntity.class)) {
                ExcelEntity excelEntity = clazz.getAnnotation(ExcelEntity.class);
                sheet = !excelEntity.sheetName().equals("") ? workbook.createSheet(excelEntity.sheetName()) : workbook.createSheet(defaultSheetName);
            }
        }
        sheet = workbook.createSheet();
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



    @Override
    public  Sheet initSheetName(List<T> entities, Workbook workbook) {
        if (entities != null && !entities.isEmpty()) {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            String defaultSheetName = clazz.getSimpleName();
            if (clazz.isAnnotationPresent(ExcelEntity.class)) {
                ExcelEntity excelEntity = clazz.getAnnotation(ExcelEntity.class);
                return !excelEntity.sheetName().equals("") ? workbook.createSheet(excelEntity.sheetName()) : workbook.createSheet(defaultSheetName);
            }
        }
        return workbook.createSheet();
    }

    @SneakyThrows
    @Override
    public void writeEntityToFile(List<T> entities, String path, boolean enable, ExcelType excelType) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
       // Class<T> clazz = (Class<T>) entities.get(0).getClass();
       // String fileName = path + clazz.getSimpleName() + "." + excelType.getType();
       // Sheet sheet = initSheetName(entities);
       // mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
       // OutputStream fileOutputStream = new FileOutputStream(fileName);
       // workbook.write(fileOutputStream);
       // fileOutputStream.close();
        initSheet
                .andThen(initSheetName)
                .andThen(writeEntityToFile)
                .apply(entities,path,enable,excelType)

    }

    @Override
    @SneakyThrows
    public OutputStream writeEntityToByteArray(List<T> entities, ExcelType excelType) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        Class<T> clazz = (Class<T>) entities.get(0).getClass();
        String fileName = clazz.getSimpleName() + "." + excelType.getType();
        Sheet sheet = initSheetName(entities);
        mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }
}
