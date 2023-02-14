package org.zaham.jexcel.mapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.zaham.jexcel.annotation.ExcelProperty;
import org.zaham.jexcel.factory.CellsFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class MapFileToEntityImpl<T> implements MapFileToEntity<T> {

    private Field[] declaredFields;
    private AtomicInteger rowIndex;
    private AtomicBoolean enableColumnName;
    private AtomicReference<Row> row;
    private Sheet sheet;

    public MapFileToEntityImpl(boolean enableColumnName, Class<T> type) {
        this.declaredFields = type.getDeclaredFields();
        this.enableColumnName = new AtomicBoolean(enableColumnName);
    }

    BiFunction<List<T>, Sheet, List<T>> initSheet = (ts, sht) -> {
        this.rowIndex = new AtomicInteger();
        this.sheet = sht;
        this.row = new AtomicReference<>(sheet.createRow(rowIndex.getAndIncrement()));
        return ts;
    };

    UnaryOperator<List<T>> buldColmunNames = ts -> {
        if (enableColumnName.get()) {
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
        ts.forEach(entity -> {
            row.set(sheet.createRow(rowIndex.getAndIncrement()));
            AtomicInteger numberOfCells = new AtomicInteger();
            Arrays.stream(declaredFields)
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(entity);
                            Cell cell = row.get().createCell(numberOfCells.get());
                            CellsFactory.setCellFactry(cell, value);
                            numberOfCells.getAndIncrement();
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        });
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
    public void mapEntityToFile(Sheet sheet, List<T> entities, boolean firstRowHeader) {
        initSheet.andThen(buldColmunNames)
                .andThen(writeInExcelFile)
                .andThen(autoSizeColumns)
                .apply(entities, sheet);
    }

    public List<String> generateExcelColumns(Field[] fields) {
        return Arrays.stream(fields)
                .map(field -> field.isAnnotationPresent(ExcelProperty.class) && !field.getAnnotation(ExcelProperty.class).excelProperty().equals("") ?
                        field.getAnnotation(ExcelProperty.class).excelProperty() : field.getName()
                )
                .collect(Collectors.toList());
    }
}
