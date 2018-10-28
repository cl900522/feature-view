package acme.me.serialize.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.sf.json.JSONArray;

public class ImportUser {
    public static void main(String[] args) throws InvalidFormatException, IOException {
        System.out.println(excel());
    }

    private static String excel() throws InvalidFormatException, IOException {
        FileInputStream inp = new FileInputStream(new File("D:\\Area.xlsx"));
        Workbook workbook = WorkbookFactory.create(inp);

        // Get the first Sheet.
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate through the rows.
        List<Area> areaList = new ArrayList<Area>();
        for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext();) {
            Row row = rowsIT.next();
            Area area = new Area();
            area.id = row.getCell(0) == null ? 0 : (int) row.getCell(0).getNumericCellValue();
            area.province = row.getCell(1) == null ? "" : row.getCell(1).toString();
            area.city = row.getCell(2) == null ? "" : row.getCell(2).toString();
            area.county = row.getCell(3) == null ? "" : row.getCell(3).toString();
            area.town = row.getCell(4) == null ? "" : row.getCell(4).toString();
            area.address = row.getCell(5) == null ? "" : row.getCell(5).toString();
            area.x = row.getCell(6) == null ? 0.0 : row.getCell(6).getNumericCellValue();
            area.y = row.getCell(7) == null ? 0.0 : row.getCell(7).getNumericCellValue();
            areaList.add(area);
        }
        // Get the JSON text.
        return JSONArray.fromObject(areaList.toArray()).toString();
    }
}
