package org.zaham.jexcel.annotation;

import org.zaham.jexcel.enums.ExcelCellType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    public int index() default 0;

    public String header() default "";

    public ExcelCellType type() default ExcelCellType.DEFAULT;
}
