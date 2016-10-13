package acme.me.j2se.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.io.StringBufferInputStream;

import org.junit.Test;

public class StreamView {
    @Test
    public void fileIOStream() {
        FileInputStream fIns = null;
        FileOutputStream fOus = null;
        try {
            File fileFrom = new File("target/classes/base64.png");
            File fileTo = new File("target/classes/base64-copy.png");

            if (!fileFrom.exists()) {
                System.err.println("File:" + fileFrom.getAbsolutePath() + " does not exist");
                return;
            }
            fOus = new FileOutputStream(fileTo);

            fIns = new FileInputStream(fileFrom);
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = fIns.read(bytes)) > 0) {
                fOus.write(bytes, 0, length);
            }
            System.out.println("File copy to :" + fileTo.getAbsolutePath() + "success");
            fileTo.delete();
        } catch (Exception e) {

        } finally {
            try {
                if (fOus != null) {
                    fOus.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fIns != null) {
                    fIns.close();
                }
            } catch (IOException e) {
            }
        }

    }

    @Test
    public void byteArrayIOStream() {
        try {
            byte[] dataBytes = "Hello this is me\nAnd this it the second line\nThis is the second Line".getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);

            ByteArrayOutputStream bo = new ByteArrayOutputStream(1024);

            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = bais.read(bytes)) > 0) {
                bo.write(bytes, 0, length);
            }

            File fileTo = new File("target/classes/bytearrayout.txt");
            bo.writeTo(new FileOutputStream(fileTo));
        } catch (IOException e) {
        }
    }

    @Test
    public void sequenceIOStream() {
        try {
            byte[] dataBytes1 = "Hello this is The first paragrapth\n".getBytes();
            ByteArrayInputStream in1 = new ByteArrayInputStream(dataBytes1);

            byte[] dataBytes2 = "Hello this is The second paragrapth".getBytes();
            ByteArrayInputStream in2 = new ByteArrayInputStream(dataBytes2);

            SequenceInputStream sis = new SequenceInputStream(in1, in2);

            ByteArrayOutputStream bo = new ByteArrayOutputStream(1024);

            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = sis.read(bytes)) > 0) {
                bo.write(bytes, 0, length);
            }

            File fileTo = new File("target/classes/bytearrayout.txt");
            bo.writeTo(new FileOutputStream(fileTo));
        } catch (IOException e) {
        }
    }
}
