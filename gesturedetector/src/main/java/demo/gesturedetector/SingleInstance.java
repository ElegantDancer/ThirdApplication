package demo.gesturedetector;

/**
 * Created by zhenzhen on 2017/2/20.
 */
public class SingleInstance {
    private static SingleInstance ourInstance = new SingleInstance();

    public static SingleInstance getInstance() {
        return ourInstance;
    }

    private SingleInstance() {
    }

    private <T> T getT(){
        return (T) "gaga";
    }
}
