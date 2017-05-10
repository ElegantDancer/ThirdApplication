package iqiyi.com.filedemo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by zhenzhen on 2017/2/28.
 */

public class FileTest {

    private Context mContext;

    public FileTest(Context context) {
        mContext = context;
    }

    public void writeToFile(String fileName){

        String filePath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        try {

            File file = new File(filePath, fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 8196);
            bufferedWriter.write("我是一个小菜鸟，每天进步一点点！一定会成功的!");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String ReadFromFile(String fileName){
        String filePath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        File file = new File(filePath, fileName);
        if(!file.exists()){
            return "";
        }

        String result = null;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str ;
            while((str = bufferedReader.readLine()) != null){
                Log.i("------>", str);
            }
            result = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
