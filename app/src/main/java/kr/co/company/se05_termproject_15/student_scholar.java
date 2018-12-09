package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class student_scholar extends AppCompatActivity {

    TextView txt, registertxt, scholartxt;
    ImageButton btn;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private Spinner Rspinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_scholar);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        registertxt = (TextView) findViewById(R.id.registertxt);
        scholartxt = (TextView) findViewById(R.id.scholartxt);

        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        //id refernece for wizet
        Rspinner = (Spinner) findViewById(R.id.Rspinner);

        //input array data
        final ArrayList<String> list = new ArrayList<>();

        String id = null;
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while (cursor.moveToNext()) {
            if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                id = cursor.getString(cursor.getColumnIndex("id"));
            }
        }

        cursor = db.rawQuery("SELECT * FROM scholarship", null);
        while (cursor.moveToNext()) {
            if (Integer.parseInt(id) == cursor.getInt(cursor.getColumnIndex("id"))) {
                String semester;
                semester = cursor.getString(cursor.getColumnIndex("semester"));
                list.add(semester);
            }
        }
        //using ArrayAdapter

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        Rspinner.setAdapter(spinnerAdapter);


        Button registerBtn = (Button) findViewById(R.id.showbtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = null;
                Cursor cursor = db.rawQuery("SELECT * FROM member", null);
                while (cursor.moveToNext()) {
                    if (txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                        id = cursor.getString(cursor.getColumnIndex("id"));
                    }
                }
                cursor = db.rawQuery("SELECT * FROM scholarship", null);
                while (cursor.moveToNext()) {
                    if (Integer.parseInt(id) == cursor.getInt(cursor.getColumnIndex("id")) &&
                            Rspinner.getSelectedItem().toString().equals(cursor.getString(cursor.getColumnIndex("semester")))) {
                        registertxt.setText(""+cursor.getInt(cursor.getColumnIndex("register")));
                        scholartxt.setText(""+cursor.getInt(cursor.getColumnIndex("scholarship")));
                    }
                }
            }
        });
    }
}
