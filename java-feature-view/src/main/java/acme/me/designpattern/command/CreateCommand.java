package acme.me.designpattern.command;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CreateCommand implements Command {
    public CreateCommand(){
    }
    public void execute() {
        String filePath= askFileName();
        File file = new File(filePath);
        if(!file.exists()){
            try{
                if(file.createNewFile()){
                    System.out.println("Success to create file:"+file.getAbsolutePath());
                }
            }catch(IOException E){
                System.err.println("Error happened to create file:"+filePath);
            }
        }
    }
    private String askFileName(){
        System.out.print("Please input file name here:");
        Scanner sc = new Scanner(System.in);
        try{
            return sc.next();
        }finally{
            sc.close();
        }
    }
    public static void main(String[] args){
        Command createCommand = new CreateCommand();
        createCommand.execute();
    }
}
