package acme.me.designpattern;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private static final String xgsql = "UPDATE iptvAccount set userAreaName='%s', userAreaCode='%s', minAreaCode='%s' WHERE userId='%s';\n";
    private static final String bjsql = "INSERT INTO iptvAccount (userId,userAreaName,userAreaCode,minAreaCode) VALUES('%s','%s','%s','%s') ON DUPLICATE KEY UPDATE userAreaName='%s', userAreaCode='%s', minAreaCode='%s';\n";
    private static final Map<String, String[]> areaInfo = new HashMap<String, String[]>();
    private static final String[] EAREA = new String[] { "", "" };
    @Before
    public void loadingArea() throws Exception {
        File f = new File("C:/Users/Administrator/Desktop/area.txt");
        BufferedReader fr = new BufferedReader(new FileReader(f));

        String readLine = fr.readLine();
        while ((readLine = fr.readLine()) != null) {
            String[] split = readLine.split("\\|");
            areaInfo.put(split[0], new String[] { split[1], split[2] });
        }
        fr.close();
    }

    @Test
    public void xiaoganData() throws Exception {
        File f = new File("C:/Users/Administrator/Desktop/area_data.csv");
        File t = new File("C:/Users/Administrator/Desktop/xiaogan.sql");
        if (!t.exists()) {
            t.createNewFile();
        }
        BufferedReader fr = new BufferedReader(new FileReader(f));
        BufferedWriter fw = new BufferedWriter(new FileWriter(t));
        String readLine = fr.readLine();
        fw.write("START TRANSACTION;\n");
        while ((readLine = fr.readLine()) != null) {
            String[] split = readLine.split(",");
            String u = split[0].replace("\"", "");
            String m = split[4].replace("\"", "");
            if (m == null || m.trim().equals("")) {
                m = split[3].replace("\"", "");
            }
            String[] area = areaInfo.get(m);
            if (area == null) {
                area = EAREA;
                System.out.println(u);
            }
            fw.write(String.format(xgsql, area[0], area[1], m, u));
        }
        fw.write("COMMIT;\n");
        fw.close();
        fr.close();
    }

    @Test
    public void beijingData() throws Exception {
        File f = new File("C:/Users/Administrator/Desktop/area_data.csv");
        File t = new File("C:/Users/Administrator/Desktop/beijing.sql");
        if (!t.exists()) {
            t.createNewFile();
        }
        BufferedReader fr = new BufferedReader(new FileReader(f));
        BufferedWriter fw = new BufferedWriter(new FileWriter(t));
        String readLine = fr.readLine();
        fw.write("START TRANSACTION;\n");
        while ((readLine = fr.readLine()) != null) {
            String[] split = readLine.split(",");
            String u = split[0].replace("\"", "");
            String m = split[4].replace("\"", "");
            if (m == null || m.trim().equals("")) {
                m = split[3].replace("\"", "");
            }
            String[] area = areaInfo.get(m);
            if (area == null) {
                area = EAREA;
                System.out.println(u);
            }
            fw.write(String.format(bjsql, u, area[0], area[1], m, area[0], area[1], m));
        }
        fw.write("COMMIT;\n");
        fw.close();
        fr.close();
    }
}
