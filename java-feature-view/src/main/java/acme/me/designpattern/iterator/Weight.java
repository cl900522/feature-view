package acme.me.designpattern.iterator;

public class Weight implements Comparable<Weight>{
    private float weight;
    private String unit = "kg";
    public float getWeght() {
        return weight;
    }
    public void setWeght(float weght) {
        this.weight = weght;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int compareTo(Weight another) {
        if(this.weight >= another.getWeght())
            return 1;
        else
            return -1;
    }
    public String toString(){
        return weight+unit;
    }
}
