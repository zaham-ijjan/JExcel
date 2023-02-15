package org.zaham.jexcel;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zaham.jexcel.core.ExcelGenerator;
import org.zaham.jexcel.core.ExcelGeneratorImp;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.ExcelGeneratorException;

import java.io.OutputStream;
import java.util.List;

@Slf4j
public final class JExcelFactory<T> {

    private static final String NULL_ERROR_LOG = "error the list object provided is null";
    private static final String EMPTY_ERROR_LOG = "erro the list object provided is empty";
    private ExcelGenerator<T> excelGenerator ;

    @SneakyThrows
    public  void excelFileGenerator(List<T> entities, ExcelType excelType, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else  {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            excelGenerator = new ExcelGeneratorImp<>(excelType, clazz);
            excelGenerator.writeEntityToFile(entities, path);
        }
    }


    @SneakyThrows
    public  void excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            excelGenerator = new ExcelGeneratorImp<>(excelType, clazz, enableColumName);
            excelGenerator.writeEntityToFile(entities, path);
        }
    }

    @SneakyThrows
    public   OutputStream excelFileGenerator(List<T> entities, ExcelType excelType) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else  {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            excelGenerator = new ExcelGeneratorImp<>(excelType, clazz);
            return excelGenerator.writeEntityToByteArray(entities);
        }
    }

    @SneakyThrows
    public OutputStream excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else {
            Class<T> clazz = (Class<T>) entities.get(0).getClass();
            excelGenerator = new ExcelGeneratorImp<>(excelType, clazz, enableColumName);
            return excelGenerator.writeEntityToByteArray(entities);
        }
    }
}
