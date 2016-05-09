package acme.me.j2se.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.poi.util.TempFile;

public class SerializeView {
    public static void main(String[] args) {
        File file = TempFile.createTempFile("Today", Math.random() + "");

        Student me = new Student();
        me.setName("ChenMx");
        me.setBirthDate(new Date());
        me.setSex("readlMale");

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(me);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Student another = (Student) ois.readObject();
            System.out.println("Name:" + another.getName() + ";Sex:" + another.getSex() + ";Birthday:" + another.getBirthDate());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
