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

public class admin extends AppCompatActivity {
    TextView txt;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    public static int studentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        helper.setDB(this,"member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();


        txt = (TextView) findViewById(R.id.numbertxt);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
        while(cursor.moveToNext()) {
            if(txt.getText().toString().equals(cursor.getString(cursor.getColumnIndex("name")))) {
                studentID = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.memberbtn:
                Intent intent = new Intent(getApplicationContext(), admin_member.class);
                intent.putExtra("name", txt.getText().toString());
//                Toast.makeText(getApplicationContext(), "회원관리", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.scholarbtn:
                intent = new Intent(getApplicationContext(), admin_scholar.class);
                intent.putExtra("name", txt.getText().toString());
//                Toast.makeText(getApplicationContext(), "장학관리", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
    }
}
