package demo.refreshlayout.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import demo.refreshlayout.R;
import demo.refreshlayout.model.Book;

/**
 * Created by zhenzhen on 2017/2/23.
 */

public class MyAdapter extends BaseAdapter {


    private List<Book> mBookList;
    private Context mContext;

    public MyAdapter(Context context, List<Book> mBookList) {
        this.mContext = context;
        this.mBookList = mBookList;
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = mBookList.get(position);
        holder.idView.setText(book.getBookId() + "");
        holder.nameView.setText(book.getBookName());


        return convertView;
    }

    private class ViewHolder{
        private TextView idView;
        private TextView nameView;

        public ViewHolder(View view) {
            idView = (TextView) view.findViewById(R.id.list_id);
            nameView = (TextView) view.findViewById(R.id.list_name);
        }
    }
}
