package demo.testdemo;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        String path = String.valueOf(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));

//
//        File file = new File(path);
//        if(file.exists()){
//            file.delete();
//        }
//
//        file.mkdir();

        File file2 = new File(path, "test.txt");
        if(!file2.exists()){
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        final File file = new File(path);
//
//
//        if (file.exists()) {
//            file.delete();
//        }
//
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < 100; i++) {
//            cachedThreadPool.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        doSomething(MainActivity.this, file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//        }

    }


    private void doSomething(Context context, File file) throws IOException {
        DataCollector collector = new DataCollector(context);
        collector.collectDeviceInfo();
        String dcd_data = new String(Base64.encode(collector.getJsonString().getBytes(), Base64.NO_WRAP));
        String dcd_data2 = new String(Base64.decode(dcd_data.getBytes(), Base64.NO_WRAP));

        writeFile(file, dcd_data + "\n" + dcd_data2);
    }

    private void writeFile(File file, String str) throws IOException {
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " "
                + "hh:mm:ss");
        FileWriter fileWriter = new FileWriter(file, true);//true表示接着上次的读
        BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
        StringBuffer buffer = new StringBuffer();
        buffer.append(tempDate.format(new Date())).append(": ").append(str);//.append("\r\n");
        bufferWriter.newLine();
        bufferWriter.write(buffer.toString());
        bufferWriter.flush();
        fileWriter.close();
    }
}
