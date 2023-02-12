package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.enums.ExcelType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.zaham.jexcel.enums.ExcelType.XLSX;

@Slf4j
public class ExcelGeneratorImp implements ExcelGenerator{

    @SneakyThrows
    public <T> void writeEntity(String fileName, ExcelType excelType, List<T> entities){
        try{
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Workbook workbook = (excelType == XLSX) ? new XSSFWorkbook() : new HSSFWorkbook();
            Sheet sheet = buildSheetName(workbook,entities);
            int rowIndex = 0;
            //entities.forEach(entity->{
            //    Row row =
            //});

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

    public void scanEntity(){
        Field[] fields =
    }
}
