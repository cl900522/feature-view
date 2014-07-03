package acme.me.designpattern.facade;

import java.io.InputStream;

public class Scanner {
    public Scanner(InputStream inStream){
        this.inStream = inStream;
    }
    private InputStream inStream;
}
