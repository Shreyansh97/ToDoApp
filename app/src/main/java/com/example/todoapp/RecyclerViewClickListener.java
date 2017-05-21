package com.example.todoapp;

import android.view.View;

/**
 * Created by shreyansh on 21/5/17.
 */

public interface RecyclerViewClickListener {
    void onClick(View view,int position);
    void onLongClick(View view,int position);
}
