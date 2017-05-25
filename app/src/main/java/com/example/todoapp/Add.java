package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Add extends AppCompatActivity {
    String method;
    private String Title,Description,Date,Time;
    private TextView title,desc,date,time;
    private Button submit;
    private Task task;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bundle extra= getIntent().getExtras();
        method = extra.getString("Method");
        title = (TextView) findViewById(R.id.Title);
        desc = (TextView) findViewById(R.id.Description);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        submit = (Button) findViewById(R.id.submit);
        db = new DatabaseHandler(this);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Title = title.getText().toString();
                Description = desc.getText().toString();
                submit();
            }
        });


        if(method.equals("Edit")){
            task = db.getTaskFromId(extra.getInt("ID"));
            title.setText(task.getTitle());
            desc.setText(task.getDescription());
            String date = task.getDate();
            String time = date.substring(date.indexOf(' ')+1);
            date=date.substring(0,date.indexOf(' '));
            this.date.setText(date);
            this.time.setText(time);
        }

        else
            task = new Task();

        final Calendar myCalendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate(myCalendar);
            }
        };

        final Calendar cCalendar=Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker,int selectedHour, int selectedMinute){
                cCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                cCalendar.set(Calendar.MINUTE,selectedMinute);
                updateTime(cCalendar);
            }
        };

        this.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Add.this,time,cCalendar.get(Calendar.HOUR_OF_DAY),
                        cCalendar.get(Calendar.MINUTE),true).show();
            }
        });

        this.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Add.this,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    void updateDate(Calendar cal){
        String date=new String();
        date=cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
        this.date.setText(date);
        Date=date;
    }
    void updateTime(Calendar cal){
        String time=new String();
        time=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
        this.time.setText(time);
        Time=time;
    }
    private void submit(){
        task.setTitle(Title);
        task.setDescription(Description);
        task.setDate(Date+" "+Time);
        if(method.equals("New"))
            db.addTask(task);
        else
            db.updateTask(task);
        Intent intent = new Intent(Add.this,MainActivity.class);
        startActivity(intent);
    }
}
