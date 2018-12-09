package kr.co.company.se05_termproject_15;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
//    public static final String ROOT_DIR = "/data/data/kr.co.company.se05_termproject_15/databases/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //db를 사용해야되기 때문에 helper에 연결시켜준다
        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper (this);
        db = helper.getWritableDatabase();

        final EditText etid = (EditText) findViewById(R.id.etid);
        final EditText etpwd = (EditText) findViewById(R.id.etpwd);
        Button button = (Button) findViewById(R.id.loginbtn);
        final TextView textView = (TextView) findViewById(R.id.textView);

        //버튼을 클릭하게 됨과 동시에 db에서 확인을하게 된다
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = helper.getReadableDatabase();
                String id = etid.getText().toString();
                String pwd = etpwd.getText().toString();
                String db_id = null;
                String db_pwd = null;
                Cursor cursor;
                boolean flag = false;
                cursor = db.rawQuery("SELECT * FROM member", null);
                while (cursor.moveToNext()) {
                    db_id = cursor.getString(cursor.getColumnIndex("id"));
                    db_pwd = cursor.getString(cursor.getColumnIndex("pwd"));


                    //그다음 조건문을 걸어줘서 조건에 부합하다면 성공 메세지와 함께 다음 화면으로 이동
                    if (id.equals(db_id) && pwd.equals(db_pwd) && db_id.charAt(0) == '2') { // Student
                        String db_name = cursor.getString(cursor.getColumnIndex("name"));
                        Intent intent = new Intent(getApplicationContext(), student.class);
                        intent.putExtra("number", etid.getText().toString());
                        intent.putExtra("name",db_name);
                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        flag = true;
                        break;
                    }
                    else if (id.equals(db_id) && pwd.equals(db_pwd) && db_id.charAt(0) == '1') { // Professor
                        String db_name = cursor.getString(cursor.getColumnIndex("name"));
                        Intent intent = new Intent(getApplicationContext(), professor.class);
                        intent.putExtra("number", etid.getText().toString());
                        intent.putExtra("name",db_name);
                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        flag = true;
                        break;
                    }
                    else if (id.equals(db_id) && pwd.equals(db_pwd) && db_id.charAt(0) == 'A') { // Admin
                        String db_name = cursor.getString(cursor.getColumnIndex("name"));
                        Intent intent = new Intent(getApplicationContext(), admin.class);
                        intent.putExtra("number", etid.getText().toString());
                        intent.putExtra("name",db_name);
                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        flag = true;
                        break;
                    }
                    else if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd)) {//유효성검사 해당 변수에 안에 값의 null이거나 비어 있는지를 체크
                        Toast.makeText(MainActivity.this, "ID와 PW를 입력하세요.", Toast.LENGTH_LONG).show();
                        break;

                    }
                }
                if(flag == false){
                    //아니라면 실패라는 메세지와 함께 화면 이동은 발생하지 않음
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
