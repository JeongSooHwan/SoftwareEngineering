package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class professor_check extends AppCompatActivity {

    TextView txt, prob1txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner spinner, studentNameSpinner;
    private Button showBtn;
    private String id;
    private int subjectID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_check);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        prob1txt = (TextView) findViewById(R.id.prob1txt);
        showBtn = (Button) findViewById(R.id.showBtn);
        spinner = (Spinner) findViewById(R.id.spinner);
        studentNameSpinner = (Spinner) findViewById(R.id.studentNameSpinner);
        //input array data
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<String> list2 = new ArrayList<>();

        id = null;
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        /* 교수 아이디 찾기 */
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                id = cursor.getString(cursor.getColumnIndex("id")); // 교수 아이디 저장
            }
        }

        cursor = db.rawQuery("SELECT * FROM assign", null);
        while (cursor.moveToNext()) {
            if (Integer.parseInt(id) == cursor.getInt(cursor.getColumnIndex("professorID"))) {
                int s_id = cursor.getInt(cursor.getColumnIndex("subjectID"));
                Cursor cursor2 = db.rawQuery("SELECT * FROM subject", null);
                while (cursor2.moveToNext()) {
                    if (s_id == cursor2.getInt(cursor2.getColumnIndex("s_id"))) {
                        String title;
                        title = cursor2.getString(cursor2.getColumnIndex("s_title"));
                        list.add(title);
                    }
                }
            }
        }

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);




        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list2.clear();
                int count = 0;
                double sum = 0.0;
                String subjectName = spinner.getSelectedItem().toString();
                int s_id = 0;
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                while (cursor.moveToNext()) {
                    if (subjectName.equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                        s_id = cursor.getInt(cursor.getColumnIndex("s_id")); // 학수번호 저장
                    }
                }

                cursor = db.rawQuery("SELECT * FROM course", null);
                while (cursor.moveToNext()) {
                    if (s_id == cursor.getInt(cursor.getColumnIndex("subjectID"))) {
                        count++;
                        int studentID = cursor.getInt(cursor.getColumnIndex("studentID"));
                        Cursor cursor2 = db.rawQuery("SELECT * FROM member", null);
                        while (cursor2.moveToNext()) {
                            if (String.valueOf(studentID).equals(cursor2.getString(cursor2.getColumnIndex("id")))) {
                                String name;
                                name = cursor2.getString(cursor2.getColumnIndex("name"));
                                list2.add(name);
                            }
                        }
                    }
                }
                prob1txt.setText(""+count);
                ArrayAdapter spinnerAdapter2;
                spinnerAdapter2 = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list2);
                studentNameSpinner.setAdapter(spinnerAdapter2);
            }
        });

    }
}
