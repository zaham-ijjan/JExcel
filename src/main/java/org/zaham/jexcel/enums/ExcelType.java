package org.zaham.jexcel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExcelType {
    XLSX("xlsx"),
    XLS("xls");

    private final String type ;


}
