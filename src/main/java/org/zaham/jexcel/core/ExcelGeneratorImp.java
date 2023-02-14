package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.mapper.MapFileToEntity;
import org.zaham.jexcel.mapper.MapFileToEntityImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Slf4j
public class ExcelGeneratorImp<T> implements ExcelGenerator<T>{

    private final Class<T> clazz;
    private final ExcelType excelType;
    private final MapFileToEntity<T> mapFileToEntity;
    private final Workbook workbook;
    private  boolean enableColumn = true;
    public ExcelGeneratorImp(ExcelType excelType, Class<T> type) {
        this.clazz = type;
        this.excelType = excelType;
        this.workbook = WorkBookFactory.buildWorkBook(excelType,null);
        this.mapFileToEntity = new MapFileToEntityImpl<>(false,type);
    }

    public ExcelGeneratorImp(ExcelType excelType, Class<T> type,boolean enableColumn) {
        this.clazz = type;
        this.excelType = excelType;
        this.workbook = WorkBookFactory.buildWorkBook(excelType,null);
        this.mapFileToEntity = new MapFileToEntityImpl<>(enableColumn,type);
        this.enableColumn = enableColumn;
    }

    @Override
    public  Sheet initSheetName(List<T> entities) {
        if (entities != null && !entities.isEmpty()) {
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
    public void writeEntityToFile(List<T> entities, String path) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        String fileName = path + clazz.getSimpleName() + "." + excelType.getType();
        Sheet sheet = initSheetName(entities);
        mapFileToEntity.mapEntityToFile(sheet, entities, enableColumn);
        OutputStream fileOutputStream = new FileOutputStream(fileName);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Override
    @SneakyThrows
    public byte[] writeEntityToByteArray(List<T> entities) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        String fileName = clazz.getSimpleName() + "." + excelType.getType();
        Sheet sheet = initSheetName(entities);
        mapFileToEntity.mapEntityToFile(sheet, entities, enableColumn);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
