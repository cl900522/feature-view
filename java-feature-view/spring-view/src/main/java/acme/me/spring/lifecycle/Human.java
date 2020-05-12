package acme.me.spring.lifecycle;

import java.util.List;

public class Human implements Sleepable {

    @Override
    public void sleep() {
        System.out.println("睡觉了！梦中自有颜如玉！");
    }

    private List<String> relatives;

    public List<String> getRelatives() {
        return relatives;
    }

    public void setRelatives(List<String> relatives) {
        this.relatives = relatives;
    }

    public void listRelatives() {
        String relas = "";
        for (String rela : relatives) {
            relas += rela + ";";
        }
        System.out.println(relas);
    }
}
