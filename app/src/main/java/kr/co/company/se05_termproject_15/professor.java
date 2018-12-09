package kr.co.company.se05_termproject_15;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class professor extends AppCompatActivity {
    TextView txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        txt = (TextView) findViewById(R.id.numbertxt);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gradebtn:
                Intent intent = new Intent(getApplicationContext(), professor_grade.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "성적부여", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.profilebtn:
                intent = new Intent(getApplicationContext(), professor_check.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "출석부", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.applybtn:
                intent = new Intent(getApplicationContext(), professor_register_lecture.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "강의등록", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.planbtn:
                intent = new Intent(getApplicationContext(), professor_plan.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "강의계획서등록", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.evaluationbtn:
                intent = new Intent(getApplicationContext(), professor_evaluation.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "강의평가", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
}