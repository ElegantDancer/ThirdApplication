package iqiyi.com.filedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.write_btn)
    Button writeBtn;
    @BindView(R.id.read_btn)
    Button readBtn;
    @BindView(R.id.content_txt)
    TextView contentTxt;

    private String fileName = "filetest.log";

    private int i = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final FileTest test = new FileTest(this);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.writeToFile(fileName);
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contentTxt.setText(test.ReadFromFile(fileName));

            }
        });
    }
}
