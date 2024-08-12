package org.example;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenericFunction {
    public String getExcelDataFromCell(String file,String sheet,int row,int col) throws IOException {
        FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\"+file+".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet xsheet = workbook.getSheet(sheet);
        Row xrow = xsheet.getRow(row);
        Cell cell = xrow.getCell(col);
        return cell.toString();
    }
    public String returnTodaysDate(String format){
        LocalDate date =LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);
        String fdate= date.format(formatter);
        return fdate;
    }
    public String randomKeyGenerator(){
        Random rand=new Random();
        return String.valueOf(rand.nextInt(100));
    }

}
