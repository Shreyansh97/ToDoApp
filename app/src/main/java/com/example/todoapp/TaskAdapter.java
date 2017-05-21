package com.example.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shreyansh on 20/5/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView Title,Description,Date;
        MyViewHolder(View view){
            super(view);
            Title = (TextView) view.findViewById(R.id.title);
            Description = (TextView) view.findViewById(R.id.description);
            Date = (TextView) view.findViewById(R.id.Date);
        }
    }

    private List<Task> tasks;
    public TaskAdapter(List tasks){
        this.tasks = tasks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int type){
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        holder.Title.setText(tasks.get(position).getTitle());
        holder.Description.setText(tasks.get(position).getDescription());
        holder.Date.setText(tasks.get(position).getDate());
    }

    @Override
    public int getItemCount(){
        return tasks.size();
    }
}
