package acme.me.pearls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileSortView {
    private static final int SIZE = 150;

    public static void main(String[] args) throws IOException {
        int[] array = new int[SIZE];
        File numberFile = new File("./target/classes/numbers");
        if (!numberFile.exists()) {
            numberFile.createNewFile();
            System.out.print(numberFile.exists());
        }
        /**
         * read numbers from file
         */
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new FileReader(numberFile));
        String lineStr;
        while ((lineStr = reader.readLine()) != null) {
            int a = Integer.parseInt(lineStr);
            array[Math.abs(a / 32)] = array[Math.abs(a / 32)] ^ (1 << a);
        }
        /**
         * out put index where bit value is 1
         */
        for (int j = 0; j < array.length * 32; j++) {
            if ((array[Math.abs(j / 32)] & 1 << j) != 0) {
                System.out.println(j);
            }
        }
    }
}
