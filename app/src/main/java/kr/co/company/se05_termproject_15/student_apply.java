package kr.co.company.se05_termproject_15;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class student_apply extends AppCompatActivity {
    TextView txt, plantxt, creditText;
    ListView lv, resultList;
    LinearLayout planform;
    private SubjectListAdapter adapter;
    private ApplyListAdapter applyAdapter;
    private List<Course> courseList, applyList;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner spinner;
    private int studentid;
    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject);
        mContext = this;

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        studentid = intent.getExtras().getInt("studentID");
        txt.setText(name);
        plantxt = (TextView) findViewById(R.id.plantxt);
        creditText = (TextView) findViewById(R.id.creditText);

        spinner = (Spinner) findViewById(R.id.spinner);

        Button applybtn = (Button) findViewById(R.id.applybtn);
        Button planbtn = (Button) findViewById(R.id.planbtn);
        Button showBtn = (Button) findViewById(R.id.showBtn);
        final Button listbtn = (Button) findViewById(R.id.listbtn);
        planform = (LinearLayout) findViewById(R.id.planform);

        /* 수강신청 리스트뷰랑 신청내역 리스트뷰 설정 */
        lv = (ListView) findViewById(R.id.listview);
        resultList = (ListView) findViewById(R.id.resultList);

        courseList = new ArrayList<Course>();
        applyList = new ArrayList<Course>();

        adapter = new SubjectListAdapter(getApplicationContext(), courseList);
        applyAdapter = new ApplyListAdapter(getApplicationContext(), applyList);

        lv.setAdapter(adapter);
        resultList.setAdapter(applyAdapter);

        final ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("s_title")));
        }

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);


        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lv.setVisibility(View.VISIBLE);
                resultList.setVisibility(View.GONE);
                planform.setVisibility(View.GONE);
                creditText.setVisibility(View.GONE);
                courseList.clear();

                int id;
                String courseTitle;
                int courseCredit;
                int courseCapacity;
                String yoil;
                String courseSTime;
                String courseETime;
                String courseRoom;


                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("s_id"));
                    courseTitle = cursor.getString(cursor.getColumnIndex("s_title"));
                    courseCredit = cursor.getInt(cursor.getColumnIndex("s_credit"));
                    courseCapacity = cursor.getInt(cursor.getColumnIndex("s_capacity"));
                    yoil = cursor.getString(cursor.getColumnIndex("yoil"));
                    courseSTime = cursor.getString(cursor.getColumnIndex("s_startTime"));
                    courseETime = cursor.getString(cursor.getColumnIndex("s_endTime"));
                    courseRoom = cursor.getString(cursor.getColumnIndex("s_room"));

                    Course course = new Course(id, courseTitle, courseCredit, courseCapacity, yoil, courseSTime, courseETime, courseRoom);
                    courseList.add(course);
                }

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(student_apply.this);
                dialog = builder.setMessage("조회하였습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                adapter.notifyDataSetChanged();
            }
        });

        planbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(View.GONE);
                resultList.setVisibility(View.GONE);
                planform.setVisibility(View.VISIBLE);
                creditText.setVisibility(View.GONE);
            }
        });

        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lv.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                planform.setVisibility(View.GONE);
                creditText.setVisibility(View.VISIBLE);

                applyList.clear();
                int id = 0;
                String courseTitle;
                int courseCredit;
                int courseCapacity;
                String yoil;
                String courseSTime;
                String courseETime;
                String courseRoom;

//                Cursor cursor = db.rawQuery("SELECT s_credit FROM course, member, subject WHERE course.studentID = member.id AND course.subjectID = subject.s_id AND course.studentID =" +
//                        studentid, null);
//                int credit = 0;
//                while (cursor.moveToNext()) {
//                    credit += cursor.getInt(cursor.getColumnIndex("s_credit"));
//                }
//                creditText.setText(credit +" 학점 / 18 학점");

                credit();
                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from member, subject, course WHERE course.studentID = member.id" +
                        " AND subject.s_id = course.subjectID AND member.id = '" + studentid + "'", null);
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("s_id"));
                    courseTitle = cursor.getString(cursor.getColumnIndex("s_title"));
                    courseCredit = cursor.getInt(cursor.getColumnIndex("s_credit"));
                    courseCapacity = cursor.getInt(cursor.getColumnIndex("s_capacity"));
                    yoil = cursor.getString(cursor.getColumnIndex("yoil"));
                    courseSTime = cursor.getString(cursor.getColumnIndex("s_startTime"));
                    courseETime = cursor.getString(cursor.getColumnIndex("s_endTime"));
                    courseRoom = cursor.getString(cursor.getColumnIndex("s_room"));

                    Course course = new Course(id, courseTitle, courseCredit, courseCapacity, yoil, courseSTime, courseETime, courseRoom);
                    applyList.add(course);
                }

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(student_apply.this);
                dialog = builder.setMessage("조회하였습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                applyAdapter.notifyDataSetChanged();
            }
        });


        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                int id = 0;
                while (cursor.moveToNext()) {
                    if (spinner.getSelectedItem().toString().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                        id = cursor.getInt(cursor.getColumnIndex("s_id"));
                        break;
                    }
                }

                cursor = db.rawQuery("SELECT * FROM assign WHERE subjectID = " + id, null);
                if (cursor.moveToFirst() == true && cursor.getString(cursor.getColumnIndex("coursePlan")) != null) {
                    plantxt.setText(cursor.getString(cursor.getColumnIndex("coursePlan")));
                } else {
                    plantxt.setText("등록된 강의계획서가 없습니다.");
                }


            }
        });
    }

    public int credit() {
        Cursor cursor = db.rawQuery("SELECT s_credit FROM course, member, subject WHERE course.studentID = member.id AND course.subjectID = subject.s_id AND course.studentID =" +
                studentid, null);
        int credit = 0;
        while (cursor.moveToNext()) {
            credit += cursor.getInt(cursor.getColumnIndex("s_credit"));
        }
        creditText.setText(credit + " 학점 / 18 학점");
        return credit;
    }

    public void alertShow(int i) {
        switch (i) {
            case 0:
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("등록 완료")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                break;
            case 1:
                builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("삭제 완료")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                break;
            case 2:
                builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("이미 등록된 강의입니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                break;
            case 3:
                builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("겹치는 수업이 존재합니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                break;
            case 4:
                builder = new AlertDialog.Builder(this);
                dialog = builder.setMessage("수강 가능한 학점을 초과했습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                break;
        }

    }
}
