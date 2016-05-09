package acme.me.designpattern.adapter;

import acme.me.designpattern.brige.Coords;

public interface Shape {
    public void boundingBox(Coords bottomLeft, Coords topRight);

    public Manipulator createManipulator();
}
