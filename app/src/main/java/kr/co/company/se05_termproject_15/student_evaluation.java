package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class student_evaluation extends AppCompatActivity {

    TextView txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner spinner;
    private int prob1 = 0, prob2 = 0, prob3 = 0, prob4 = 0, prob5 = 0;
    private Button saveBtn;
    private RadioGroup prob1G, prob2G, prob3G, prob4G, prob5G;
    private String id;
    private int subjectID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_evaluation);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        saveBtn = (Button) findViewById(R.id.saveBtn);

        spinner = (Spinner) findViewById(R.id.spinner);
        //input array data
        final ArrayList<String> list = new ArrayList<>();

        id = null;
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                id = cursor.getString(cursor.getColumnIndex("id"));
            }
        }

        cursor = db.rawQuery("SELECT * FROM course", null);
        while (cursor.moveToNext()) {
            if (Integer.parseInt(id) == cursor.getInt(cursor.getColumnIndex("studentID"))) {
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
        //using ArrayAdapter

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);




        prob1G = (RadioGroup) findViewById(R.id.prob1G);
        prob2G = (RadioGroup) findViewById(R.id.prob2G);
        prob3G = (RadioGroup) findViewById(R.id.prob3G);
        prob4G = (RadioGroup) findViewById(R.id.prob4G);
        prob5G = (RadioGroup) findViewById(R.id.prob5G);


        prob1G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.prob1_1:
                        prob1 = 1;
                        break;
                    case R.id.prob1_2:
                        prob1 = 2;
                        break;
                    case R.id.prob1_3:
                        prob1 = 3;
                        break;
                    case R.id.prob1_4:
                        prob1 = 4;
                        break;
                    case R.id.prob1_5:
                        prob1 = 5;
                        break;
                }
            }
        });

        prob2G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.prob2_1:
                        prob2 = 1;
                        break;
                    case R.id.prob2_2:
                        prob2 = 2;
                        break;
                    case R.id.prob2_3:
                        prob2 = 3;
                        break;
                    case R.id.prob2_4:
                        prob2 = 4;
                        break;
                    case R.id.prob2_5:
                        prob2 = 5;
                        break;
                }
            }
        });

        prob3G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.prob3_1:
                        prob3 = 1;
                        break;
                    case R.id.prob3_2:
                        prob3 = 2;
                        break;
                    case R.id.prob3_3:
                        prob3 = 3;
                        break;
                    case R.id.prob3_4:
                        prob3 = 4;
                        break;
                    case R.id.prob3_5:
                        prob3 = 5;
                        break;
                }
            }
        });

        prob4G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.prob4_1:
                        prob4 = 1;
                        break;
                    case R.id.prob4_2:
                        prob4 = 2;
                        break;
                    case R.id.prob4_3:
                        prob4 = 3;
                        break;
                    case R.id.prob4_4:
                        prob4 = 4;
                        break;
                    case R.id.prob4_5:
                        prob4 = 5;
                        break;
                }
            }
        });

        prob5G.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.prob5_1:
                        prob5 = 1;
                        break;
                    case R.id.prob5_2:
                        prob5 = 2;
                        break;
                    case R.id.prob5_3:
                        prob5 = 3;
                        break;
                    case R.id.prob5_4:
                        prob5 = 4;
                        break;
                    case R.id.prob5_5:
                        prob5 = 5;
                        break;
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                while (cursor.moveToNext()) {
                    if (spinner.getSelectedItem().toString().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                        subjectID = cursor.getInt(cursor.getColumnIndex("s_id"));
                    }
                }

                double avg;
                if (prob1 == 0 || prob2 == 0 || prob3 == 0 || prob4 == 0 || prob5 == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(student_evaluation.this);
                    dialog = builder.setMessage("체크되지 않은 문항이 있습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    avg = (prob1 + prob2 + prob3 + prob4 + prob5) / 5.0;
                    Log.d("Query ","UPDATE course SET evaluation = " + avg
                            + " WHERE studentID = " + Integer.parseInt(id) + " AND subjectID = " + subjectID +";");
                    db.execSQL("UPDATE course SET evaluation = " + avg
                            + " WHERE studentID = " + Integer.parseInt(id) + " AND subjectID = " + subjectID +";");
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(student_evaluation.this);
                    dialog = builder.setMessage("등록 완료")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

    }
}
