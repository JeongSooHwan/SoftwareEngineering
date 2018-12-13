package kr.co.company.se05_termproject_15;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class professor_evaluation extends AppCompatActivity {
    TextView txt, prob1txt, prob2txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner spinner;
    private Button showBtn;
    private String id;
    private int subjectID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_evaluation);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        prob1txt = (TextView) findViewById(R.id.prob1txt);
        prob2txt = (TextView) findViewById(R.id.prob2txt);

        showBtn = (Button) findViewById(R.id.showBtn);

        spinner = (Spinner) findViewById(R.id.spinner);
        //input array data
        final ArrayList<String> list = new ArrayList<>();

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

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(professor_evaluation.this);
                dialog = builder.setMessage("조회하였습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                cursor = db.rawQuery("SELECT * FROM course", null);
                while (cursor.moveToNext()) {
                    if (s_id == cursor.getInt(cursor.getColumnIndex("subjectID"))) {
                        count++;
                        sum += cursor.getDouble(cursor.getColumnIndex("evaluation"));
                    }
                }

                prob1txt.setText("" + count);
                if (count == 0) {
                    prob2txt.setText("0");
                } else {
                    prob2txt.setText("" + (sum / count));
                }


            }
        });

    }

}
