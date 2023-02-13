package org.zaham.jexcel;

import lombok.experimental.UtilityClass;
import org.zaham.jexcel.core.ExcelGeneratorImp;
import org.zaham.jexcel.enums.ExcelType;

import java.util.List;

@UtilityClass
public final class JExcelFactory {
    public static <T> void excelGenerator(List<T> entities, ExcelType excelType) {
        if (entities != null && !entities.isEmpty()) {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz);
            excelGeneratorImp.writeEntity(entities);
        }
    }


    public static <T> void excelGenerator(List<T> entities, ExcelType excelType, boolean firstRowHeader) {
        if (entities != null && !entities.isEmpty()) {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz, firstRowHeader);
            excelGeneratorImp.writeEntity(entities);
        }
    }
}
