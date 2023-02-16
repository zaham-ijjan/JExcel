# JExcel Library
a Java tiny library that generate an Excel File based on a Pojo

## Usage


1. Get the jar. (Download JAR | Github Release | Maven Repo)
2. Add this as a dependency in your project.
3. Use it as below.

Let's suppose that you have a list of objects that you want to write them on some excel File

``` Java
 List<Employee> employees = ...;
```
 you have 2 options eather you want To generate File in a known location or You want it as an OutputStream 
 
 ``` Java
/**
 * String path = "..." //path  that you want to generate your file
 * boolean column = true ; // if you want to display the field names of your object in the first Name
 * JExcel<Employees> jExcel = new JExcel<>(); 
 * Optional<File> fileOptional = jExcel.excelFileGenerator(employeesList,excelType,enableColumn,path); // Option 1
 * Optional<OutputStream> outpuStreamOptional  = jExcel.excelFileGenerator(employeesList,excelType,enableColumn,path); // Option 2
 * 
 */
 ```
