package io.loopcamp.test.day13_excell_mocks;

import io.loopcamp.util.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ReadExcelFileDataTest {

    @Test
    public void readDocuportUsersDataTest () {
        String path = "src/test/resources/DocQa3.xlsx";
        ExcelUtil excelUtil = new ExcelUtil(path, "QA2"); //DocQa3 is the sheet name (tab name at the bottom)
        System.out.println("Columns: " + excelUtil.getColumnsNames());


        int rowCount = excelUtil.rowCount();
        System.out.println("Row count: " +rowCount );

        System.out.println(excelUtil.getCellData(0, 0));  // in works with indexes starting from 0's
        System.out.println(excelUtil.getCellData(1, 1));


        List <Map<String, String>> data = excelUtil.getDataList();
        System.out.println("Date: " + data);

        //Optional Practice: create a reusable method to open the excel file/sheet
    }


}
