package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Show extends Activity implements PopupMenu.OnMenuItemClickListener {
    String method;
    RecyclerView mRecycler;
    TaskAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Task> tasks;
    DatabaseHandler db;
    int onClickPosition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Bundle extras = getIntent().getExtras();
        method = extras.getString("Method");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(method+" Tasks");

        db = new DatabaseHandler(this);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        setValues();

        mAdapter = new TaskAdapter(tasks);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        mRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecycler, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {
                onClickPosition = position;
                PopupMenu menu = new PopupMenu(Show.this,view);
                //PopupMenu menu1 = (PopupMenu) ;
                //MenuItem itemView = (MenuItem) view.findViewById(R.id.three);
                //if(method.equals("Completed"))
                  //  itemView.setTitle("Mark as imcomplete");
                //menu.getMenuInflater().inflate(R.menu.menu_popup,menu.getMenu());
                menu.setOnMenuItemClickListener(Show.this);
                menu.inflate(R.menu.menu_popup);
                menu.show();

                /*menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        int id=item.getItemId();
                        switch (id){
                            case R.id.one:
                                Intent intent = new Intent(Show.this,Add.class);
                                intent.putExtra("Task",tasks.get(position));
                                startActivity(intent);
                                finish();
                                startActivity(getIntent());
                                break;
                            case R.id.two:
                                deleteRecord(position);
                                break;
                            case R.id.three:
                                toggleComplete(position);
                        }
                        return true;
                    }
                });*/

            }

            @Override
            public void onLongClick(View view, int position) {
                onClick(view,position);
            }
        }));
    }

    public void setValues(){
        if(method.equals("Completed"))
            tasks = db.getTasks(1);
        else {
            tasks = new ArrayList<Task>();
            List<Task> tasks1 = db.getTasks(0);
            Calendar current = Calendar.getInstance();
            for(Task task:tasks1){
                String a[]=task.getDate().split("[ :,/]");
                Calendar calendar = Calendar.getInstance();
                int f[]=new int[5];
                for(int i=0;i<5;i++)
                    f[i]=Integer.parseInt(a[i]);
                calendar.set(f[2],f[1],f[0],f[3],f[4]);
                if(method.equals("Overdue") && calendar.after(current))
                    tasks.add(task);
                else if(method.equals("Upcoming") && calendar.before(current))
                    tasks.add(task);
            }
        }
    }
    public void toggleComplete(int position){
        Task task = tasks.get(position);
        task.toggleCompleted();
        db.updateTask(task);
        finish();
        startActivity(getIntent());
    }
    public void deleteRecord(int position){
        db.deleteTask(tasks.get(position));
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.one:
                Intent intent = new Intent(Show.this,Add.class);
                intent.putExtra("Method","Edit");
                intent.putExtra("ID",tasks.get(onClickPosition).getId());
                startActivity(intent);
                return true;
            case R.id.two:
                deleteRecord(onClickPosition);
                return true;
            case R.id.three:
                toggleComplete(onClickPosition);
                return true;
        }
        return false;
    }
    //public void onclick(View view,int position){}

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//        if(method.equals("Completed"))
//            menu.findItem(R.id.three).setTitle("Mark as incomplete");
//        return true;
//    }
}
