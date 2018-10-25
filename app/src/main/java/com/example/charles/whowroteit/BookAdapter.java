package com.example.charles.whowroteit;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by charles on 25/09/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context,  int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Book book = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView author = (TextView) view.findViewById(R.id.author);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());

        return view;
    }

}
