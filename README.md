# JExcel Library based on apache poi excel writer
a Java tiny library that generate an Excel File based on a Pojo Using Apache POI

## Usage

1. Get the jar. (Download JAR | Github Release)
2. Add this as a dependency in your project
3. Use it as below.

``` xml
<dependency>
  <groupId>org.zaham</groupId>
  <artifactId>jexcel</artifactId>
  <version>1.0-2</version>
</dependency>
``` 
Let's suppose that you have a list of objects that you want to write them on some excel File

``` Java
 List<Employee> employees = ...;
```
 you have 2 options eather you want To generate File in a known location or You want it as an OutputStream 
 
 ``` Java
String path = "..." //path  that you want to generate your file
boolean column = true ; // if you want to display the field names of your object in the first Name
JExcel<Employees> jExcel = new JExcel<>(); 
Optional<File> fileOptional = jExcel.excelFileGenerator(employeesList,excelType,enableColumn,path); // Option 1
Optional<OutputStream> outpuStreamOptional  = jExcel.excelByteArrayGenerator(employeesList,excelType,enableColumn) // Option 2 without path

 ```

### Supported Data Types 
    - Primitive Types (int ,long, double ....)
    - Integer, Double .. 
    - Date
    - LocalDate
    - LocalDateTime
    - Calendar
    - String
    - Boolean
### Annotations 

 ``` Java
 @ExcelEntity // used to define 
 @ExcelIgnore // in case you want to ignoe a field when writing
 @ExcelProperty // if you don't want your column to takee the field name and instead you gave it a custom name
 
 ```
#### Example of POJO

 ``` Java
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ExcelEntity(sheetName = "employers check")
public class Employees{
    private String last_name;
    private String first_name;
    private String title;
    @ExcelProperty(excelProperty = "courtesy)
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
    @ExcelIgnore
    private String photo_path;
}
 ```
Now, most project modules would have entities just like these that holds a list of data which needs to be written to Excel file(s) as it is.

For this, we created a common utility that can be invoked as,

![Annotated Sheet Image](/docs/result.png "Excel generated using Annotations")

