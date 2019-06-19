package acme.me.j2se.classloader;

import acme.domain.User;
import com.sun.tools.attach.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.List;

/**
 * @author: cdchenmingxuan
 * @date: 2019/6/19 10:47
 * @description: java-feature-view
 */
public class JavaAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                System.out.println("Load class: " + className);
                return null;
            }
        }, false);
    }

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException {
        System.out.println("Agent Main called");
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {
                if (!className.replace("/", ".").equals(agentArgs)) {
                    return null;
                }
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctClass = pool.getCtClass(agentArgs);
                    CtMethod getAge = ctClass.getDeclaredMethod("getAge");
                    getAge.setBody("return Long.valueOf(\"100\");");
                    byte[] bytes = ctClass.toBytecode();
                    return bytes;
                } catch (Exception e) {
                    System.out.println("转换出错");
                    e.printStackTrace();
                }
                return classfileBuffer;
            }
        }, true);
        try {
            Class<?> aClass = JavaAgent.class.getClassLoader().loadClass(agentArgs);
            inst.retransformClasses(aClass);
        } catch (Exception e) {
            System.out.println("未发现目标类");
            e.printStackTrace();
        }
    }

    @Test
    public void test1()
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();

        User humain = new User();
        humain.setAge(12L);
        Long age = humain.getAge();
        System.out.println(age);

        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().contains(this.getClass().getSimpleName())) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("D:\\03_git\\feature-view\\java-feature-view\\j2se-view\\target\\javase-view-1.0.0.jar", "acme.domain.User");
                System.out.println("ok");
                virtualMachine.detach();
            }
        }

        age = humain.getAge();
        System.out.println(age);

        humain = new User();
        humain.getAge();
        System.out.println(age);
    }
}
