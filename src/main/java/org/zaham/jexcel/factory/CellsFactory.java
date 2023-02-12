package org.zaham.jexcel.factory;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class CellsFactory {


    public static void setCellFactry(Cell cell , Object object){
        if(object instanceof Number){
            Number n = (Number) object;
            cell.setCellValue(n.doubleValue());
        } else if (object instanceof Date) {
            cell.setCellValue((Date) object);
        }else if (object instanceof LocalDate) {
            cell.setCellValue((LocalDate) object);
        }else if (object instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) object);
        }else if (object instanceof Calendar) {
            cell.setCellValue((Calendar) object);
        }else if (object instanceof String) {
            cell.setCellValue((String) object);
        }else if (object instanceof Boolean) {
            cell.setCellValue((Boolean) object);
        }
    }
}
