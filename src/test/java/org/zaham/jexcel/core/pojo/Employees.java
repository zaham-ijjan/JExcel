package org.zaham.jexcel.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zaham.jexcel.annotation.ExcelEntity;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ExcelEntity(sheetName = "employers check")
public class Employees{
    private String last_name;
    private String first_name;
    private String title;
    private String title_of_courtesy;
    private Date birth_date;
    private Date hire_date;
    private String address;
    private String city;
    private String region;
    private String postal_code;
    private String country;
    private String home_phone;
    private String extension;
    private String photo_path;
}