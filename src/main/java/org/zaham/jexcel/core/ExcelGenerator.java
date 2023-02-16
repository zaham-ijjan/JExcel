package org.zaham.jexcel.core;

import lombok.SneakyThrows;
import org.zaham.jexcel.enums.ExcelType;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

public interface ExcelGenerator<T> {

     @SneakyThrows
     File writeEntityToFile(List<T> entities, ExcelType excelType, String path, boolean enable);

     @SneakyThrows
     OutputStream writeEntityToByteArray(List<T> entities, ExcelType excelType,boolean enable);
}
