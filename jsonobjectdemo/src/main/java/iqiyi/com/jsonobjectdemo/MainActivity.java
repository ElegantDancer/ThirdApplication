package iqiyi.com.jsonobjectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("1", "1gaga");
            jsonObject.put("2", "2gaga");
            jsonObject.put("3", "3gaga");
            jsonObject.put("4", "4gaga");
            jsonObject.put("5", "5gaga");
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Iterator<String> keys = jsonObject.keys();
       
        while(keys.hasNext()){
            Toast.makeText(this, "" + keys.next(), Toast.LENGTH_SHORT).show();
        }
    }
}
