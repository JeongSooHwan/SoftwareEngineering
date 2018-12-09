package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class student_grade extends AppCompatActivity {
    TextView txt;
    ListView lv, resultList;
    private gradeListAdapter adapter;
    private List<Course> courseList;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private String studentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_grade);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);


        studentID = null;
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        /* 학생 아이디 찾기 */
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                studentID = cursor.getString(cursor.getColumnIndex("id")); // 학생 아이디 저장
            }
        }

        Button showBtn = (Button) findViewById(R.id.showBtn);
        lv = (ListView) findViewById(R.id.listview);
        courseList = new ArrayList<Course>();
        adapter = new gradeListAdapter(getApplicationContext(), courseList);
        lv.setAdapter(adapter);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle;
                int courseCredit;
                String courseGrade;
                db = helper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM course", null);
                while (cursor.moveToNext()) {
                    if (Integer.parseInt(studentID) == cursor.getInt(cursor.getColumnIndex("studentID"))) {
                        int subjectID = cursor.getInt(cursor.getColumnIndex("subjectID"));
                        courseGrade = cursor.getString(cursor.getColumnIndex("grade"));

                        Cursor subjectCursor = db.rawQuery("SELECT * FROM subject WHERE s_id = " + subjectID, null);
                        subjectCursor.moveToNext();
                        courseTitle = subjectCursor.getString(subjectCursor.getColumnIndex("s_title"));
                        courseCredit = subjectCursor.getInt(subjectCursor.getColumnIndex("s_credit"));

                        Course course = new Course(courseTitle, courseCredit, courseGrade);
                        courseList.add(course);
                    }
                }

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(student_grade.this);
                dialog = builder.setMessage("조회하였습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
