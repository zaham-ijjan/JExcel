package org.zaham.jexcel.mapper;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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

public class MapFileToEntityImpl<T> implements MapFileToEntity<T> {

    private Field[] declaredFields ;
    private AtomicInteger rowIndex ;
    private AtomicBoolean enableColumnName ;
    private AtomicReference<Row> row;
    private Sheet sheet;

    public MapFileToEntityImpl(boolean enableColumnName ,Class<T> type) {
        this.declaredFields = type.getDeclaredFields();
        this.enableColumnName = new AtomicBoolean(enableColumnName);
    }

    BiFunction<List<T>,Sheet,List<T>> initSheet = (ts, sht) -> {
        this.rowIndex = new AtomicInteger();
        this.sheet = sht;
        this.row = new AtomicReference<>(sheet.createRow(rowIndex.getAndIncrement()));
        return ts;
    };

    UnaryOperator<List<T>> buldColmunNames = ts -> {
        if(enableColumnName.get()){
            AtomicInteger columnCells = new AtomicInteger();
            generateExcelHeader(declaredFields)
                    .forEach(s -> {
                        Cell cell = row.get().createCell(columnCells.get());
                        CellsFactory.setCellFactry(cell,s);
                        columnCells.getAndIncrement();
                    });
            enableColumnName.set(false);
        }
        return ts;
    };

    @SuppressWarnings("java:S3011")
    Function<List<T>,Void> writeInExcelFile = ts -> {
        ts.forEach( entity ->{
            row.set(sheet.createRow(rowIndex.getAndIncrement()));
            AtomicInteger numberOfCells = new AtomicInteger();
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
        return null;
    } ;

    @SneakyThrows
    @Override
    public void mapEntityToFile(Sheet sheet,List<T> entities,boolean firstRowHeader){
        initSheet.andThen(buldColmunNames)
                .andThen(writeInExcelFile)
                .apply(entities,sheet);
    }
    public List<String> generateExcelHeader(Field[] fields){
         return Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
