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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class admin_scholar extends AppCompatActivity {

    TextView txt;
    EditText idtxt, registertxt, scholartxt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner spinner, studentSpinner;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_scholar);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        idtxt = (EditText) findViewById(R.id.idtxt);
        registertxt = (EditText) findViewById(R.id.registertxt);
        scholartxt = (EditText) findViewById(R.id.scholartxt);

        //id refernece for wizet
        spinner = (Spinner) findViewById(R.id.spinner);
        studentSpinner = (Spinner) findViewById(R.id.studentSpinner);

        //input array data
        final ArrayList<String> list2 = new ArrayList<>();

        String id = null;
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("id")).charAt(0) == '2') {
                id = cursor.getString(cursor.getColumnIndex("id"));
                list2.add(id);
            }
        }
        //using ArrayAdapter
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list2);
        studentSpinner.setAdapter(spinnerAdapter);

        //input array data
        final ArrayList<String> list = new ArrayList<>();
        list.add("2017/1학기");
        list.add("2017/2학기");
        list.add("2018/1학기");
        list.add("2018/2학기");
        //using ArrayAdapter
        ArrayAdapter spinnerAdapter2;
        spinnerAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter2);

        Button registerbtn = (Button) findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id;
                int register;
                int scholar = 0;

                id = Integer.parseInt(studentSpinner.getSelectedItem().toString());
                register = Integer.parseInt(registertxt.getText().toString());
                scholar = Integer.parseInt(scholartxt.getText().toString());

                if (register < 1000000) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(admin_scholar.this);
                    dialog = builder.setMessage("등록금을 다시 확인해주세요.\n (1,000,000 ~ 3,000,000)")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else if (register < scholar) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(admin_scholar.this);
                    dialog = builder.setMessage("장학금이 등록금보다 많습니다. ")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    db.execSQL("INSERT INTO scholarship VALUES(" + id + ", " + (register - scholar) +
                            ", " + scholar + ", '" + spinner.getSelectedItem().toString() + "');");

                    studentSpinner.setSelection(0);
                    registertxt.setText("");
                    scholartxt.setText("0");

                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(admin_scholar.this);
                    dialog = builder.setMessage("등록 완료")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }


            }
        });
    }
}
