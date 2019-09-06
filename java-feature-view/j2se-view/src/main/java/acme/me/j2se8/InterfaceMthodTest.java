package acme.me.j2se8;

import org.junit.Test;

/**
 * @author: cdchenmingxuan
 * @date: 2019/9/5 16:56
 * @description: java-feature-view
 */
public class InterfaceMthodTest {
    @Test
    public void test1() {
        Car2 car = new Car2();
        car.print();

    }

    public interface Vehicle {
        default void print() {
            System.out.println("我是一辆车!");
        }

        /**
         * 静态默认方法,接口可以声明（并且可以提供实现）静态方法
         */
        static void blowHorn() {
            System.out.println("按喇叭!!!");
        }
    }

    public interface FourWheeler {
        default void print() {
            System.out.println("我是一辆四轮车!");
        }
    }


    public interface SpecVehicle extends Vehicle, FourWheeler {
        @Override
        default void print() {
            System.out.println("我是一辆特殊的车子");
        }
    }

    public class Car2 implements Vehicle, FourWheeler {
        public void print() {
            FourWheeler.super.print();
            Vehicle.super.print();
            Vehicle.blowHorn();
            System.out.println("I am a car!");
        }
    }
}
