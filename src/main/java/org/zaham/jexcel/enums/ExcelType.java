package org.zaham.jexcel.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExcelType {
    XLSX("xlsx"),
    XLS("xls");

    private final String type ;


}
