package com.example.s_m_s;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class CGPAActivity extends AppCompatActivity {
    private EditText Credit,Grade;
    private Button addCourse,seeGpa,erase;
    private TextView textView;
    double counter=0,total_credit=0,credit=0,grade=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpaactivity);
        Credit = findViewById(R.id.editText1);
        Grade = findViewById(R.id.editText2);
        addCourse = findViewById(R.id.button);
        seeGpa = findViewById(R.id.button2);
        erase = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit = Double.parseDouble(Credit.getText().toString());
                grade = Double.parseDouble(Grade.getText().toString());
                counter += (grade*credit);
                total_credit+=credit;
                Toast.makeText(getApplicationContext(),"Credit : "+credit+"\ngrade : "+grade,Toast.LENGTH_LONG).show();
                Credit.setText("");
                Grade.setText("");
            }
        });
        seeGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result = counter/total_credit;
                textView.setText("YOUR CGPA : "+result);

            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                total_credit=0;
                credit=0;
                grade=0;
                Credit.setText("");
                Grade.setText("");
                textView.setText("");
            }
        });

    }
}