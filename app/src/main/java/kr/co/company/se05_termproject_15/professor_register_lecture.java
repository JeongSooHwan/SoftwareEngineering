package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class professor_register_lecture extends AppCompatActivity {
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private ArrayAdapter arrCredit, arrCapacity, arrYoil, arrStartTime, arrEndTime, arrYoil1, arrStartTime1, arrEndTime1, arrYoil2, arrStartTime2, arrEndTime2;
    private Spinner spinCredit, spinCapacity, spinYoil, spinStartTime, spinEndTime, spinYoil1, spinStartTime1, spinEndTime1, spinYoil2, spinStartTime2, spinEndTime2;
    Button button;
    TextView txt;
    LinearLayout form1, form2;
    EditText etsubject, etnumber, etroom, etroom1, etroom2;
    private int professorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_lecture);
        txt = (TextView) findViewById(R.id.numbertxt);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                professorID = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }

        button = (Button) findViewById(R.id.successbtn);
        form1 = (LinearLayout) findViewById(R.id.layoutweek1);
        form2 = (LinearLayout) findViewById(R.id.layoutweek2);
        etsubject = (EditText) findViewById(R.id.subjectname);
        etnumber = (EditText) findViewById(R.id.lecturenumber);
        etroom = (EditText) findViewById(R.id.room);
        etroom1 = (EditText) findViewById(R.id.room1);
        etroom2 = (EditText) findViewById(R.id.room2);

        spinCredit = (Spinner) findViewById(R.id.credit);
        arrCredit = ArrayAdapter.createFromResource(this, R.array.credit, android.R.layout.simple_spinner_dropdown_item);
        spinCredit.setAdapter(arrCredit);

        spinCapacity = (Spinner) findViewById(R.id.capacity);
        arrCapacity = ArrayAdapter.createFromResource(this, R.array.capacity, android.R.layout.simple_spinner_dropdown_item);
        spinCapacity.setAdapter(arrCapacity);

        spinYoil = (Spinner) findViewById(R.id.yoil);
        arrYoil = ArrayAdapter.createFromResource(this, R.array.yoil, android.R.layout.simple_spinner_dropdown_item);
        spinYoil.setAdapter(arrYoil);

        spinStartTime = (Spinner) findViewById(R.id.starttime);
        arrStartTime = ArrayAdapter.createFromResource(this, R.array.stime, android.R.layout.simple_spinner_dropdown_item);
        spinStartTime.setAdapter(arrStartTime);

        spinEndTime = (Spinner) findViewById(R.id.endtime);
        arrEndTime = ArrayAdapter.createFromResource(this, R.array.etime, android.R.layout.simple_spinner_dropdown_item);
        spinEndTime.setAdapter(arrEndTime);

        spinYoil1 = (Spinner) findViewById(R.id.yoil1);
        arrYoil1 = ArrayAdapter.createFromResource(this, R.array.yoil, android.R.layout.simple_spinner_dropdown_item);
        spinYoil1.setAdapter(arrYoil1);

        spinStartTime1 = (Spinner) findViewById(R.id.starttime1);
        arrStartTime1 = ArrayAdapter.createFromResource(this, R.array.stime, android.R.layout.simple_spinner_dropdown_item);
        spinStartTime1.setAdapter(arrStartTime1);

        spinEndTime1 = (Spinner) findViewById(R.id.endtime1);
        arrEndTime1 = ArrayAdapter.createFromResource(this, R.array.etime, android.R.layout.simple_spinner_dropdown_item);
        spinEndTime1.setAdapter(arrEndTime1);

        spinYoil2 = (Spinner) findViewById(R.id.yoil2);
        arrYoil2 = ArrayAdapter.createFromResource(this, R.array.yoil, android.R.layout.simple_spinner_dropdown_item);
        spinYoil2.setAdapter(arrYoil2);

        spinStartTime2 = (Spinner) findViewById(R.id.starttime2);
        arrStartTime2 = ArrayAdapter.createFromResource(this, R.array.stime, android.R.layout.simple_spinner_dropdown_item);
        spinStartTime2.setAdapter(arrStartTime2);

        spinEndTime2 = (Spinner) findViewById(R.id.endtime2);
        arrEndTime2 = ArrayAdapter.createFromResource(this, R.array.etime, android.R.layout.simple_spinner_dropdown_item);
        spinEndTime2.setAdapter(arrEndTime2);
    }

    public void register(View v) {
        boolean suc = false;
        boolean flag = false;
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(professor_register_lecture.this);
        RadioButton rad1 = findViewById(R.id.radio1);
        RadioButton rad2 = findViewById(R.id.radio2);
        db = helper.getReadableDatabase();
        if (etsubject.getText().toString().getBytes().length <= 0 || etnumber.getText().toString().getBytes().length <= 0 ||
                (etroom.getText().toString().getBytes().length <= 0 && etroom1.getText().toString().getBytes().length <= 0 &&
                        etroom2.getText().toString().getBytes().length <= 0) ||
                spinCredit.getSelectedItem().toString() == "학점" || spinCapacity.getSelectedItem().toString() == "수용 인원" ||
                (spinStartTime.getSelectedItem().toString() == "시작" && spinStartTime1.getSelectedItem().toString() == "시작"
                        && spinStartTime2.getSelectedItem().toString() == "시작") || (spinEndTime.getSelectedItem().toString() == "종료" &&
                spinEndTime1.getSelectedItem().toString() == "종료" && spinEndTime2.getSelectedItem().toString() == "종료") ||
                (spinYoil.getSelectedItem().toString() == "요일") && (spinYoil1.getSelectedItem().toString() == "요일") && spinYoil2.getSelectedItem().toString() == "요일") { //빈값이 넘어올때의 처리
            Toast.makeText(getApplicationContext(), "빈 칸을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            flag = true;
            Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
            while (cursor.moveToNext()) {
                if (etnumber.getText().toString().equals(cursor.getString(cursor.getColumnIndex("s_id")))) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 학수번호 입니다", Toast.LENGTH_SHORT).show();
                    etnumber.setText("");
                    suc = true;
                }
            }
        }

        if (suc == false && flag == true) {
            db = helper.getWritableDatabase();
            String subjectname = etsubject.getText().toString();
            String lecturenumber = etnumber.getText().toString();
            String credit = spinCredit.getSelectedItem().toString().substring(0, 1);
            String capacity = spinCapacity.getSelectedItem().toString().substring(0, 2);
            int id = Integer.parseInt(lecturenumber);
            int cap = Integer.parseInt(capacity);
            int cre = Integer.parseInt(credit);

            if (rad1.isChecked()) {
                String room = etroom.getText().toString();
                String yoil = spinYoil.getSelectedItem().toString().substring(0, 1);
                String start = spinStartTime.getSelectedItem().toString().substring(0, 2);
                String end = spinEndTime.getSelectedItem().toString().substring(0, 2);
                if (Integer.parseInt(start) >= Integer.parseInt(end)) {
                    dialog = builder.setMessage("수업 시작시간과 종료시간을 다시 확인해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    db.execSQL("INSERT INTO subject VALUES(" + id + ", '" + subjectname + "', '" + capacity + "', '" + room + "', '" + yoil + "', '" + start + "', '" + end + "' , '" + credit + "' );");
                    db.execSQL("INSERT INTO assign VALUES(" + professorID + ", " + id + ", NULL);");
                    db.close();
                    /* 양식 초기화 */
                    etsubject.setText("");
                    etnumber.setText("");
                    etroom.setText("");
                    spinYoil.setSelection(0);
                    spinStartTime.setSelection(0);
                    spinEndTime.setSelection(0);
                    rad1.setChecked(false);

                    builder = new AlertDialog.Builder(professor_register_lecture.this);
                    dialog = builder.setMessage("등록 성공")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
            } else if (rad2.isChecked()) {
                String room1 = etroom1.getText().toString();
                String yoil1 = spinYoil1.getSelectedItem().toString().substring(0, 1);
                String start1 = spinStartTime1.getSelectedItem().toString().substring(0, 2);
                String end1 = spinEndTime1.getSelectedItem().toString().substring(0, 2);
                String room2 = etroom2.getText().toString();
                String yoil2 = spinYoil2.getSelectedItem().toString().substring(0, 1);
                String start2 = spinStartTime2.getSelectedItem().toString().substring(0, 2);
                String end2 = spinEndTime2.getSelectedItem().toString().substring(0, 2);

                if ((Integer.parseInt(start1) >= Integer.parseInt(end1)) || (Integer.parseInt(start2) >= Integer.parseInt(end2))) {

                    dialog = builder.setMessage("수업 시작시간과 종료시간을 다시 확인해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    db.execSQL("INSERT INTO subject VALUES(" + id + ", '" + subjectname + "', '" + capacity + "', '" + room1 + "," + room2 + "', '" + yoil1 + "," + yoil2 + "', '" + start1 + "," + start2 + "', '" + end1 + "," + end2 + "' , '" + credit + "' );");
                    db.execSQL("INSERT INTO assign VALUES(" + professorID + ", " + id + ")");
                    db.close();
                    /* 양식 초기화 */
                    etsubject.setText("");
                    etnumber.setText("");
                    etroom1.setText("");
                    spinYoil1.setSelection(0);
                    spinStartTime1.setSelection(0);
                    spinEndTime1.setSelection(0);
                    etroom2.setText("");
                    spinYoil2.setSelection(0);
                    spinStartTime2.setSelection(0);
                    spinEndTime2.setSelection(0);
                    rad2.setChecked(false);
                    builder = new AlertDialog.Builder(professor_register_lecture.this);
                    dialog = builder.setMessage("등록 성공")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        }
    }

    public void clickradio(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                form1.setVisibility(View.VISIBLE);
                form2.setVisibility(View.GONE);
                break;
            case R.id.radio2:
                form1.setVisibility(View.GONE);
                form2.setVisibility(View.VISIBLE);
                break;
        }
    }
}
