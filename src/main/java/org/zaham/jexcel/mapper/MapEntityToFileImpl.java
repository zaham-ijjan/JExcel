package org.zaham.jexcel.mapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.zaham.jexcel.annotation.ExcelIgnore;
import org.zaham.jexcel.annotation.ExcelProperty;
import org.zaham.jexcel.factory.CellsFactory;
import org.zaham.jexcel.functional.Function;
import org.zaham.jexcel.functional.TriFunction;
import org.zaham.jexcel.functional.UnaryOperator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class MapEntityToFileImpl<T> implements MapEntityToFile<T> {

    private AtomicInteger rowIndex;
    private AtomicBoolean enableColumnName;
    private AtomicReference<Row> row;
    private Sheet sheet;


    TriFunction<List<T>, Sheet,Boolean, List<T>> initSheet = (ts, sht,enable) -> {
        this.rowIndex = new AtomicInteger();
        this.sheet = sht;
        this.enableColumnName = new AtomicBoolean(enable);
        this.row = new AtomicReference<>(sheet.createRow(rowIndex.getAndIncrement()));
        return ts;
    };

    UnaryOperator<List<T>> buldColmunNames = ts -> {
        if (enableColumnName.get()) {
            Field[] declaredFields = ts.get(0).getClass().getDeclaredFields();
            AtomicInteger columnCells = new AtomicInteger();
            generateExcelColumns(declaredFields)
                    .forEach(s -> {
                        Cell cell = row.get().createCell(columnCells.get());
                        CellsFactory.setCellFactry(cell, s);
                        columnCells.getAndIncrement();
                    });
            enableColumnName.set(false);
        }
        return ts;
    };

    @SuppressWarnings("java:S3011")
    UnaryOperator<List<T>> writeInExcelFile = ts -> {
        for(T entity : ts ){
            row.set(sheet.createRow(rowIndex.getAndIncrement()));
            AtomicInteger numberOfCells = new AtomicInteger();
            List<Field> declaredFields = Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(ExcelIgnore.class))
                    .collect(Collectors.toList());
            for(Field field : declaredFields){
                field.setAccessible(true);
                Object value = field.get(entity);
                Cell cell = row.get().createCell(numberOfCells.get());
                CellsFactory.setCellFactry(cell, value);
                numberOfCells.getAndIncrement();
            }
        }
        return ts;
    };

    Function<List<T>, Void> autoSizeColumns = ts -> {
        sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        return null;
    };

    @SneakyThrows
    @Override
    public void mapEntityToFile(Sheet sheet, List<T> entities, boolean enableColumn) {
        initSheet.andThen(buldColmunNames)
                .andThen(writeInExcelFile)
                .andThen(autoSizeColumns)
                .apply(entities, sheet,enableColumn);
    }

    private List<String> generateExcelColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(ExcelIgnore.class))
                .map(field -> field.isAnnotationPresent(ExcelProperty.class) && !field.getAnnotation(ExcelProperty.class).excelProperty().equals("") ?
                        field.getAnnotation(ExcelProperty.class).excelProperty() : field.getName()
                )
                .collect(Collectors.toList());
    }
}
