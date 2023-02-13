package org.zaham.jexcel.core;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public class SheetContainer<T>{
    private Sheet sheet;
    private List<T> data;
    private String heading = "";
}
