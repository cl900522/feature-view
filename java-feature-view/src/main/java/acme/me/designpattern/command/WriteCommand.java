package acme.me.designpattern.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WriteCommand {
    File sourceFile, targetFile;

    public WriteCommand(File sourceFile, File targetFile) {
        if (sourceFile != null && targetFile != null) {
            this.sourceFile = sourceFile;
            this.targetFile = targetFile;
        } else {
            System.err.println("File param is null!");
        }
    }

    public void execute()  throws IOException {
        InputStream sourceFileStream = null;
        OutputStream targeFileStream = null;
        byte[] fileContent =new byte[1024*10];

        try{
            sourceFileStream = new FileInputStream(this.sourceFile);
            targeFileStream = new FileOutputStream(this.targetFile);
            while(sourceFileStream.read(fileContent) != -1){
                targeFileStream.write(fileContent);
            }
        }catch(Exception e){
            System.err.println("Error to get sourceFile resource!");
        }finally{
            if(sourceFileStream!= null)
                sourceFileStream.close();
        }
    }
}
