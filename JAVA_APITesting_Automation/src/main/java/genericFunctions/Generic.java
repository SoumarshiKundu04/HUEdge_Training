package genericFunctions;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import java.io.FileInputStream;
import java.io.IOException;

public class Generic {

    public Object [][] ReadExcelData(String filename,String Sheetname) throws IOException {

        FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\"+filename+".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet xsheet = workbook.getSheet(Sheetname);
        int rowNum=xsheet.getLastRowNum();
        Row row= xsheet.getRow(1);
        int colNum=row.getLastCellNum();
        System.out.println("Reading sheet "+Sheetname+" having rows "+rowNum+" having column "+colNum);
        Object obj[][]=new Object[rowNum+1][colNum+1];
        for(int i=1;i<=rowNum;i++){
            Row r=xsheet.getRow(i);
            for(int j=0;j<colNum;j++){
                Cell cell=r.getCell(j);
                obj[i][j]=cell.toString();
            }
        }
        return obj;
    }
}
