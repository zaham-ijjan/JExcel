package org.zaham.jexcel;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.zaham.jexcel.core.ExcelGeneratorImp;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.ExcelGeneratorException;

import java.util.List;

@UtilityClass
@Slf4j
public final class JExcelFactory {

    private static final String NULL_ERROR_LOG = "error the list object provided is null";
    private static final String EMPTY_ERROR_LOG = "erro the list object provided is empty";

    @SneakyThrows
    public static <T> void excelFileGenerator(List<T> entities, ExcelType excelType, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else  {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz);
            excelGeneratorImp.writeEntityToFile(entities, path);
        }
    }


    @SneakyThrows
    public static <T> void excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz, enableColumName);
            excelGeneratorImp.writeEntityToFile(entities, path);
        }
    }

    @SneakyThrows
    public static <T> byte[] excelFileGenerator(List<T> entities, ExcelType excelType) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else  {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz);
            return excelGeneratorImp.writeEntityToByteArray(entities);
        }
    }

    @SneakyThrows
    public static <T> byte[] excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            ExcelGeneratorImp<T> excelGeneratorImp = new ExcelGeneratorImp<>(excelType, clazz, enableColumName);
            return excelGeneratorImp.writeEntityToByteArray(entities);
        }
    }
}
