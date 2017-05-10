package demo.testdemo;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhenzhen on 2017/2/24.
 */

public class TestFile {

    public void testFile(Context context) throws IOException {

        String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + File.separator + "gaga.txt";
        File file = new File(path);

        if(!file.exists()){
            file.createNewFile();//因为这个是手机目录  所以不需要额外mkDir
        }

        FileWriter fileReader = new FileWriter(file, true);
        BufferedWriter bufferedReader = new BufferedWriter(fileReader, 8192);
        bufferedReader.write("");
    }
}
