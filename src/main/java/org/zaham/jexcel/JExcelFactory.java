package org.zaham.jexcel;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zaham.jexcel.core.ExcelGenerator;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.exception.ExcelGeneratorException;
import org.zaham.jexcel.injectors.Injector;

import java.util.List;

@Slf4j
public final class JExcelFactory<T> {

    private static final String NULL_ERROR_LOG = "error the list object provided is null";
    private static final String EMPTY_ERROR_LOG = "erro the list object provided is empty";

    private final ExcelGenerator<T> excelGenerator;

    public JExcelFactory() {
        Injector.startApplication(JExcelFactory.class);
        this.excelGenerator = Injector.getService(ExcelGenerator.class);
    }


    @SneakyThrows
    public void excelFileGenerator(List<T> entities, ExcelType excelType, boolean enableColumName, @NonNull String path) {
        if (entities == null) {
            throw new ExcelGeneratorException(NULL_ERROR_LOG);
        } else if (entities.isEmpty()) {
            throw new ExcelGeneratorException(EMPTY_ERROR_LOG);
        } else {
            excelGenerator.writeEntityToFile(entities, excelType, path, enableColumName);
        }
    }
}
