package org.zaham.jexcel.mapper;

import lombok.SneakyThrows;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Iterator;
import java.util.List;

public class MapFileToEntity {

    @SneakyThrows
    public <T> void mapFileToObject(List<Cell> cells, Class<T> clazz){
        T object = clazz.newInstance();

    }
    public <T> List<T> map(Workbook workbook , Class<T> clazz){
        int numberSheet = workbook.getNumberOfSheets();
        for (int i = 0; i < numberSheet; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                List<Cell> cells = IteratorUtils.toList(row.cellIterator());
            }
        }
     throw new UnsupportedOperationException("method not implemented yet");
    }
}
