package acme.me.designpattern.flyweight;

public class GlyphFactory {
    private Character[] character = new Character[256];
    public Character createCharacter(char ch){
        return new Character();
    }
    public Row createRow(){
        return null;
    }
    public Column createColumn(){
        return null;
    }
}
