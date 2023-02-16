package org.zaham.jexcel;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zaham.jexcel.core.ExcelGenerator;
import org.zaham.jexcel.core.ExcelGeneratorImp;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.ExcelGeneratorException;
import org.zaham.jexcel.mapper.MapEntityToFile;
import org.zaham.jexcel.mapper.MapEntityToFileImpl;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
public final class JExcelFactory<T> {

    private static final String NULL_ERROR_LOG = "error the list object provided is null";
    private static final String EMPTY_WARN_LOG = "the list object provided is empty Nothing to be generated";

    private final ExcelGenerator<T> excelGenerator;

    public JExcelFactory() {
        MapEntityToFile<T> mapEntityToFile = new MapEntityToFileImpl<>();
        excelGenerator = new ExcelGeneratorImp<>(mapEntityToFile);
    }


    @SneakyThrows
    public Optional<File> excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        }
        if (entities.isEmpty()) {
            log.warn(EMPTY_WARN_LOG);
            return Optional.empty();
        } else {
            return Optional.of(excelGenerator.writeEntityToFile(entities, excelType, path, enableColumName));
        }

    }

    @SneakyThrows
    public Optional<OutputStream> excelByteArrayGenerator(List<T> entities, ExcelType excelType, boolean enableColumName, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            log.warn(EMPTY_WARN_LOG);
            return Optional.empty();
        } else {
            return Optional.of(excelGenerator.writeEntityToByteArray(entities, excelType, enableColumName));
        }
    }
}
