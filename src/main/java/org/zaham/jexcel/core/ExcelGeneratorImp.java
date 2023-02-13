package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zaham.jexcel.annotation.ExcelEntity;
import org.zaham.jexcel.enums.ExcelType;
import org.zaham.jexcel.factory.WorkBookFactory;
import org.zaham.jexcel.mapper.MapFileToEntity;
import org.zaham.jexcel.mapper.MapFileToEntityImpl;

import java.io.FileOutputStream;
import java.util.List;

@Slf4j
public class ExcelGeneratorImp implements ExcelGenerator {

    private final MapFileToEntity mapFileToEntityImpl = new MapFileToEntityImpl();

    private boolean firstRowHeader = true;

    public ExcelGeneratorImp() {
    }

    public ExcelGeneratorImp(boolean firstRowHeader) {
        firstRowHeader = firstRowHeader;
    }

    @SneakyThrows
    public <T> void writeEntity(ExcelType excelType, List<T> entities) {
        log.info("starting generation of ExcelFile With Type: {}", excelType);
        String fileName = entities.get(0).getClass().getSimpleName() + "." + excelType.getType();
        Workbook workbook = WorkBookFactory.buildWorkBook(excelType, null);
        Sheet sheet = buildSheetName(workbook, entities);
        mapFileToEntityImpl.mapEntityToFile(sheet, entities,firstRowHeader);
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public <T> Sheet buildSheetName(Workbook workbook, List<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            T entity = entities.get(0);
            String defaultSheetName = entity.getClass().getSimpleName();
            Class<?> type = entity.getClass();
            if (type.isAnnotationPresent(ExcelEntity.class)) {
                ExcelEntity excelEntity = type.getAnnotation(ExcelEntity.class);
                return !excelEntity.sheetName().equals("") ? workbook.createSheet(excelEntity.sheetName()) : workbook.createSheet(defaultSheetName);
            }
        }
        return workbook.createSheet();
    }
}
