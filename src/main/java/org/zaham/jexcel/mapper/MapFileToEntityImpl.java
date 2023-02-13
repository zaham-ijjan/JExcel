package org.zaham.jexcel.mapper;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.factory.CellsFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MapFileToEntityImpl implements MapFileToEntity {

    @SneakyThrows
    @SuppressWarnings("java:S3011")
    @Override
    public <T> void mapEntityToFile(Sheet sheet,List<T> entities,boolean firstRowHeader){
        AtomicInteger rowIndex = new AtomicInteger();
        AtomicBoolean setColumnName = new AtomicBoolean(firstRowHeader);
        AtomicReference<Row> row = new AtomicReference<>(sheet.createRow(rowIndex.getAndIncrement()));
        T e = entities.get(0);
        Field[] declaredFields = e.getClass().getDeclaredFields();
        if(setColumnName.get()){
            AtomicInteger columnCells = new AtomicInteger();
            generateExcelHeader(declaredFields)
                    .forEach(s -> {
                        Cell cell = row.get().createCell(columnCells.get());
                        CellsFactory.setCellFactry(cell,s);
                        columnCells.getAndIncrement();
                    });
            setColumnName.set(false);
            //rowIndex.getAndIncrement();
        }
        entities.forEach( entity ->{
            row.set(sheet.createRow(rowIndex.getAndIncrement()));
            AtomicInteger numberOfCells = new AtomicInteger();
            if(setColumnName.get()){
                generateExcelHeader(declaredFields)
                        .forEach(s -> {
                            Cell cell = row.get().createCell(numberOfCells.get());
                            CellsFactory.setCellFactry(cell,s);
                            numberOfCells.getAndIncrement();
                        });
                setColumnName.set(false);
                sheet.createRow(rowIndex.getAndIncrement());
            }
            Arrays.stream(declaredFields)
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            Cell cell = row.get().createCell(numberOfCells.get());
                            CellsFactory.setCellFactry(cell,value);
                            numberOfCells.getAndIncrement();
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        });
    }

    @Override
    public <T> List<T> map(Workbook workbook, Class<T> clazz) {
        int numberSheet = workbook.getNumberOfSheets();
        for (int i = 0; i < numberSheet; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            sheet.rowIterator()
                    .forEachRemaining(row -> {
                        row.cellIterator()
                                .forEachRemaining(cell -> {
                                    System.out.println("this is cell value :::" + cell.getStringCellValue());
                                });
                    });
        }
        throw new UnsupportedOperationException("method not implemented yet");
    }
    public <T> List<String> generateExcelHeader(Field[] fields){
         return Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
