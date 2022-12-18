package com.example.s_m_s;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_student);

        Intent intent = new Intent(StudentActivity.this,ImageActivity.class);
        startActivity(intent);

    }
}