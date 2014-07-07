package acme.me.designpattern.iterator;

public class Weight implements Comparable<Weight>{
    private float weght;
    private String unit;
    public float getWeght() {
        return weght;
    }
    public void setWeght(float weght) {
        this.weght = weght;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int compareTo(Weight another) {
        if(this.weght >= another.getWeght())
            return 1;
        else
            return -1;
    }

}
