package org.zaham.jexcel;

import org.zaham.jexcel.core.ExcelGenerator;
import org.zaham.jexcel.core.ExcelGeneratorImp;

public final class JExcelFactory {
    private JExcelFactory(){}

    public static ExcelGenerator excelGenerator(){
        return new ExcelGeneratorImp();
    }
}
