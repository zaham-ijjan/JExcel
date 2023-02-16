package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.functional.FourthFunction;
import org.zaham.jexcel.functional.Function;
import org.zaham.jexcel.functional.UnaryOperator;
import org.zaham.jexcel.mapper.MapEntityToFile;

import java.io.*;
import java.util.List;

@Slf4j
public class ExcelGeneratorImp<T> implements ExcelGenerator<T>{


    private MapEntityToFile<T> mapEntityToFile;


    public ExcelGeneratorImp(MapEntityToFile<T> mapEntityToFile) {
        this.mapEntityToFile = mapEntityToFile;
    }

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


    Function<List<T>,File> writeEntityToFile = entities -> {
        String fileName = path + clazz.getSimpleName() + "." + excelType.getType();
        mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
        OutputStream fileOutputStream = null;
        File file = new File(fileName);
        fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        return file;
    };

    Function<List<T>,ByteArrayOutputStream> writeEntityToOuputStream = entities ->{
        mapEntityToFile.mapEntityToFile(sheet, entities, enableColumn);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    };



    @SneakyThrows
    @Override
    public File writeEntityToFile(List<T> entities,ExcelType excelType, String path, boolean enable ) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        return initSheet
                .andThen(initSheetName)
                .andThen(writeEntityToFile)
                .apply(entities,path,enable,excelType);

    }

    @Override
    @SneakyThrows
    public OutputStream writeEntityToByteArray(List<T> entities, ExcelType excelType, boolean enable ) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        return initSheet
                .andThen(initSheetName)
                .andThen(writeEntityToOuputStream)
                .apply(entities,path,enable,excelType);
    }
}
