package acme.me.designpattern.factory;


public class StrawberyCakeFactory implements AbstractCakeFactory {
    private static boolean flag = false;

    /**
     * 避免通过反射来生成多个实例
     * @throws Exception
     */
    public StrawberyCakeFactory() throws Exception{
        if (flag) {
            flag = !flag;
        } else {
            throw new Exception("");
        }
    }

    public Cake createCake() {
        return new StrawberyCake();
    }

}
