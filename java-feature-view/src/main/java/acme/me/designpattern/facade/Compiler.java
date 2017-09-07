package acme.me.designpattern.facade;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Compiler {
    public void compile(InputStream input, ByteArrayInputStream output){
        Scanner scanner = new Scanner(input);
        ProgramNodeBuilder builder = null;
        Parser parser = new Parser();
        
        parser.parse(scanner, builder);
        RISCCodeGenerator generator = new RISCCodeGenerator(output);
        ProgramNode parseTree = builder.getRootNode();
        parseTree.traverse(generator);
    }
}
