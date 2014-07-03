package acme.me.designpattern.factory;

public enum CakeEnum {
    STRAWBERY("Strawbery"), MILKY("Milky");
    String cakeName;

    private CakeEnum(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getName() {
        return cakeName;
    }
}
