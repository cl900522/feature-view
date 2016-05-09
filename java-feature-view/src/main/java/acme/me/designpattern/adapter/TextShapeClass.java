package acme.me.designpattern.adapter;

import acme.me.designpattern.brige.Coords;

public class TextShapeClass extends TextView implements Shape {

    public void boundingBox(Coords bottomLeft, Coords topRight) {
        int bottom = bottomLeft.x, left = bottomLeft.y, width = topRight.x, height = topRight.y;

        getOrigin(bottom, left);
        getExtent(width, height);

        bottomLeft = new Coords(bottom, left);
        topRight = new Coords(bottom + height, left + width);
    }

    public Manipulator createManipulator() {
        return new TextMainpulator();
    }

}
