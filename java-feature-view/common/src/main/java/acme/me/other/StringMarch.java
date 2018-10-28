package acme.me.other;

public class StringMarch {
    private int compare(String source, String target) {
        int sourceLength;
        int targetLength;
        if (source == null || (sourceLength = source.length()) == 0) {
            return 0;
        }
        if (target == null || (targetLength = target.length()) == 0) {
            return 0;
        }

        int d[][]; // 矩阵
        int i; // 遍历str的
        int j; // 遍历target的
        char sourceChar; // str的
        char targetChar; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1

        d = new int[sourceLength + 1][targetLength + 1];
        for (i = 0; i <= sourceLength; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= targetLength; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= sourceLength; i++) { // 遍历str
            sourceChar = source.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= targetLength; j++) {
                targetChar = target.charAt(j - 1);
                if (sourceChar == targetChar) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[sourceLength][targetLength];
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     * @param str
     * @param target
     * @return
     */
    public float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }

    public static void main(String[] args) {
        StringMarch lt = new StringMarch();
        String target = "请问慢性胃炎如何治疗";
        String str = "慢性胃炎";
        System.out.println("similarityRatio=" + lt.getSimilarityRatio(str, target));
    }
}
