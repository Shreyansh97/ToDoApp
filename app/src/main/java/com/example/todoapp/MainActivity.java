package com.example.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add=(Button) findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent intent = new Intent(MainActivity.this,Add.class);
                intent.putExtra("Method","New");
                startActivity(intent);
            }
        });
        Button completed=(Button) findViewById(R.id.Completed);
        completed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Show.class);
                intent.putExtra("Method","Completed");
                startActivity(intent);
            }
        });
        Button overdue=(Button) findViewById(R.id.Overdue);
        overdue.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Show.class);
                intent.putExtra("Method","Overdue");
                startActivity(intent);
            }
        });
        Button upcoming=(Button) findViewById(R.id.Upcoming);
        upcoming.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Show.class);
                intent.putExtra("Method","Upcoming");
                startActivity(intent);
            }
        });
    }
}
