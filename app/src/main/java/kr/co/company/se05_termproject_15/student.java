package kr.co.company.se05_termproject_15;

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

public class student extends AppCompatActivity {
    TextView txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    public static int studentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();


        txt = (TextView) findViewById(R.id.numbertxt);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                studentID = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timetablebtn:
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                db = helper.getReadableDatabase();
//                while (cursor.moveToNext()) {
//                    Log.d("Query ", cursor.getString(cursor.getColumnIndex("s_id")));
                    Intent intent = new Intent(getApplicationContext(), student_timetable.class);
//                    intent.putExtra("title", cursor.getString(cursor.getColumnIndex("s_title")));
//                    intent.putExtra("yoil", cursor.getString(cursor.getColumnIndex("yoil")));
//                    intent.putExtra("starttime", cursor.getString(cursor.getColumnIndex("s_startTime")));
//                    intent.putExtra("endtime", cursor.getString(cursor.getColumnIndex("s_endTime")));
//                    intent.putExtra("room", cursor.getString(cursor.getColumnIndex("s_room")));
                    startActivity(intent);
//                }
                Toast.makeText(getApplicationContext(), "강의 시간표", Toast.LENGTH_SHORT).show();
                break;
            case R.id.gradebtn:
                intent = new Intent(getApplicationContext(), student_grade.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "성적조회", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.profilebtn:
                cursor = db.rawQuery("SELECT * FROM member", null);
                db = helper.getReadableDatabase();
                while (cursor.moveToNext()) {
                    if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                        Log.d("Query ", cursor.getString(cursor.getColumnIndex("id")));
                        intent = new Intent(getApplicationContext(), student_imformation.class);
                        intent.putExtra("id", cursor.getString(cursor.getColumnIndex("id")));
                        intent.putExtra("name", cursor.getString(cursor.getColumnIndex("name")));
                        intent.putExtra("department", cursor.getString(cursor.getColumnIndex("department")));
                        intent.putExtra("tel", cursor.getString(cursor.getColumnIndex("tel")));
                        Toast.makeText(getApplicationContext(), "학생정보", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
                break;
            case R.id.applybtn:
                intent = new Intent(getApplicationContext(), student_apply.class);
                intent.putExtra("name", txt.getText().toString());
                intent.putExtra("studentID",studentID);
                Toast.makeText(getApplicationContext(), "수강신청", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.scholarbtn:
                intent = new Intent(getApplicationContext(), student_scholar.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "장학조회", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.evaluationbtn:
                intent = new Intent(getApplicationContext(), student_evaluation.class);
                intent.putExtra("name", txt.getText().toString());
                Toast.makeText(getApplicationContext(), "강의평가", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
}
