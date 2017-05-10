package demo.refreshlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.refreshlayout.adapter.MyAdapter;
import demo.refreshlayout.model.Book;
import demo.refreshlayout.view.ScrollLinearLayout;

public class MainActivity extends Activity implements ScrollLinearLayout.IScrollControlListener{

    private ListView mListView;
    private TextView mTextView;
    private  int offsetY = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.ref_listview);
        mTextView = (TextView) findViewById(R.id.ref_tex);

        List<Book> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Book book = new Book();
            book.setBookId(i);
            book.setBookName("第" + i + "本书籍哦！");
            list.add(book);
        }

        MyAdapter myAdapter = new MyAdapter(this, list);

        mListView.setAdapter(myAdapter);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offsetY += 30;
                mTextView.setTranslationY(-offsetY);
                mListView.setTranslationY(-offsetY);
            }
        });
    }

    @Override
    public boolean shouldDispatchTouch() {
        return false;
    }

    @Override
    public int getScrollDistance() {
        return 300;
    }
}
