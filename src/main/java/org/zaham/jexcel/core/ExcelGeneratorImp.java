package org.zaham.jexcel.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.mapper.MapFileToEntity;
import org.zaham.jexcel.mapper.MapFileToEntityImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
public class ExcelGeneratorImp implements ExcelGenerator{

    private final MapFileToEntity mapFileToEntityImpl = new MapFileToEntityImpl();

    public <T> void writeEntity(ExcelType excelType, List<T> entities){
        log.info("starting generation of ExcelFile With Type: {}",excelType);
        try{
            String fileName = entities.get(0).getClass().getName();
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Workbook workbook = WorkBookFactory.buildWorkBook(fileName,fileInputStream);
            Sheet sheet = buildSheetName(workbook,entities);
            mapFileToEntityImpl.mapEntityToFile(sheet,entities);
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }catch (Exception e){
            log.error("error",e);
        }
    }

    public <T> Sheet buildSheetName(Workbook workbook , List<T> entities){
        if(entities !=null && !entities.isEmpty()){
            T entity = entities.get(0);
            Class<?> type = entity.getClass();
            if(type.isAnnotationPresent(ExcelEntity.class)){
                ExcelEntity excelEntity = type.getAnnotation(ExcelEntity.class);
                return !excelEntity.sheetName().equals("") ?  workbook.createSheet(excelEntity.sheetName()) : workbook.createSheet();
            }
        }
        return workbook.createSheet();
    }
}
