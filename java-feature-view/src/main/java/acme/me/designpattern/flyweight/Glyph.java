package acme.me.designpattern.flyweight;

import java.awt.Font;

public abstract class Glyph {
    abstract public void draw();
    abstract public void setFont();
    abstract public Font getFont();
    abstract public void first();
    abstract public void next();
    abstract public boolean isDone();
    abstract public Glyph current();
    
    abstract public void insert();
    abstract public void remove();
}
