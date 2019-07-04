package acme.me.spring.expression;

import acme.me.spring.Human;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;

/**
 * spel演示
 *
 * @author: cdchenmingxuan
 * @date: 2019/6/25 11:47
 * @description: java-feature-view
 */
public class SpelTest {

    @Test
    public void test1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        System.out.println(message);

        exp = parser.parseExpression("relatives[0] == '1L'");
        Human rootObject = new Human();
        ArrayList<String> relatives = new ArrayList<>();
        relatives.add("1L");
        rootObject.setRelatives(relatives);
        EvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);

        boolean result = exp.getValue(evaluationContext, Boolean.class);
        System.out.println(result);
    }

    @Test
    public void test2() {
        //如果没有config，那么获取超过list长度的会报错
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        ExpressionParser parser = new SpelExpressionParser(config);
        Expression exp = parser.parseExpression("relatives[2]");

        Human rootObject = new Human();
        ArrayList<String> relatives = new ArrayList<>();
        relatives.add("1L");

        String message = (String) exp.getValue(rootObject);
        System.out.println(message);
    }


    @Test
    public void test3() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());

        ExpressionParser parser = new SpelExpressionParser(config);
        Expression exp = parser.parseExpression("relatives[0]");
        Human rootObject = new Human();
        ArrayList<String> relatives = new ArrayList<>();
        relatives.add("1L");
        rootObject.setRelatives(relatives);

        Long start = System.currentTimeMillis();
        EvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);
        for (int i = 0; i < 10000; i++) {
            exp.getValue(evaluationContext, String.class);
        }
        System.out.println(System.currentTimeMillis() - start + "ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            rootObject.getRelatives().get(0);
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
