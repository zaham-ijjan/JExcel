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
import java.util.concurrent.atomic.AtomicInteger;

public class MapFileToEntityImpl implements MapFileToEntity {

    @SneakyThrows
    public <T> void mapFileToObject(List<Cell> cells, Class<T> clazz) {
        T object = clazz.newInstance();

    }

    @SneakyThrows
    @SuppressWarnings("java:S3011")
    @Override
    public <T> void mapEntityToFile(Sheet sheet,List<T> entities,boolean firstRowHeader){
        AtomicInteger rowIndex = new AtomicInteger();
        entities.forEach( entity ->{
            Row row = sheet.createRow(rowIndex.getAndIncrement());
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            AtomicInteger numberOfCells = new AtomicInteger();
            Arrays.stream(declaredFields)
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            Cell cell = row.createCell(numberOfCells.get());
                            CellsFactory.setCellFactry(cell,value);
                            numberOfCells.getAndIncrement();
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
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
}
