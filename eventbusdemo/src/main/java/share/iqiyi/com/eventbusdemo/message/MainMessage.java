package share.iqiyi.com.eventbusdemo.message;

/**
 * Created by zhenzhen on 2017/5/9.
 */

public class MainMessage {

    private String message;


    public MainMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
