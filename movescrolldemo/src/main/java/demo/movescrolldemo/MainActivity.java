package demo.movescrolldemo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_group);


//        findViewById(R.id.move_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = getMac(MainActivity.this);
//                String bl = getBluetoothAddress();
//                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public String getMac(Context context) {


        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }

    /**
     * 获取蓝牙MAC地址
     * 相关的权限申请在 QYVideoClient中{@see QYVideoClient/AndroidManifest.xml}
     * @return 蓝牙MAC地址
     */
    private String getBluetoothAddress() {

        try {

            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

            if ((adapter != null) && (adapter.isEnabled())) {
                return encode(adapter.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String encode(String content) {

        if (content == null) return "";

        try {
            return URLEncoder.encode(content, "utf-8").toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }

    }

}
