package acme.me.designpattern.adapter;

import acme.me.designpattern.brige.Coords;

public class TextShapeVariable implements Shape{
    private TextView textView;
    public TextShapeVariable(TextView textView){
        this.textView = textView;
    }

    public void boundingBox(Coords bottomLeft, Coords topRight) {
        int bottom = bottomLeft.x, left = bottomLeft.y, width = topRight.x, height = topRight.y;

        textView.getOrigin(bottom, left);
        textView.getExtent(width, height);

        bottomLeft = new Coords(bottom, left);
        topRight = new Coords(bottom + height, left + width);
    }

    public Manipulator createManipulator() {
        return new TextMainpulator();
    }

}
