package acme.me.other;

public class Kit {
    public static void main(String[] args) {
        int seed = 0;
        for (int i = 0; i < 10; i++) {
            seed = seed++;
        }
        System.out.println("Ned value is still:" + seed);

        int j = 80;
        String s1 = String.valueOf(j < 100 ? j : 100.0);
        String s2 = String.valueOf(j < 100 ? j : 100);
        if (s1.equals(s2)) {
            System.out.println("S1 and S2 is the same!");
        } else {
            System.out.println("S1 and S2 is NOT the same!");
        }
    }
}
