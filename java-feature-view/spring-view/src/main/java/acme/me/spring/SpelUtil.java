package acme.me.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.*;
import org.springframework.context.expression.StandardBeanExpressionResolver;

/**
 * spring es 工具
 *
 * @author: cdchenmingxuan
 * @date: 2019/6/19 09:28
 * @description: java-feature-view
 */
public class SpelUtil implements BeanPostProcessor, BeanFactoryAware, BeanClassLoaderAware {
    private BeanFactory beanFactory;
    private BeanExpressionResolver resolver;
    private BeanExpressionContext expressionContext;

    /**
     * 解析 SPEL
     *
     * @param value
     * @return
     */
    public Object resolveExpression(String value) {
        String resolvedValue = resolve(value);
        if (!(resolvedValue.startsWith("#{") && value.endsWith("}"))) {
            return resolvedValue;
        }
        return this.resolver.evaluate(resolvedValue, this.expressionContext);
    }

    /**
     * 解析 ${}
     *
     * @param value
     * @return
     */
    public String resolve(String value) {
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.resolver = new StandardBeanExpressionResolver(classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
            this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 对 bean 的后置处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /**
        //获取实际类型
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        //获取所有方法
        ReflectionUtils.doWithMethods(targetClass, method -> {
            //获取自定义的注解(Bean是个例子）
            org.springframework.context.annotation.Bean annotation = AnnotationUtils.findAnnotation(method, org.springframework.context.annotation.Bean.class);
            //假设下面的 value 支持 SPEL
            for (String val : annotation.name()) {
                //解析表达式
                Object value = resolveExpression(val);
                //TODO 其他逻辑
            }
        }, method -> {
            //TODO 过滤方法
            return true;
        });

         **/
        return bean;
    }
}
