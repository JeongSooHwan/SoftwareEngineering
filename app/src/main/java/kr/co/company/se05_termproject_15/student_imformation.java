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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class student_imformation extends AppCompatActivity {
    TextView txt;
    EditText idtxt, nametxt, dptxt, teltxt, leveltxt;
    ImageButton btn;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
    private ArrayAdapter arrS;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imformation);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        idtxt = (EditText) findViewById(R.id.idtxt);
        nametxt = (EditText) findViewById(R.id.nametxt);
        dptxt = (EditText) findViewById(R.id.dptxt);
        teltxt = (EditText) findViewById(R.id.teltxt);
        leveltxt = (EditText) findViewById(R.id.leveltxt);
        btn = (ImageButton) findViewById(R.id.menubtn);
        Button editBtn = (Button) findViewById(R.id.edtiBtn);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);

        idtxt.setText(intent.getExtras().getString("id"));
        nametxt.setText(intent.getExtras().getString("name"));
        dptxt.setText(intent.getExtras().getString("department"));

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        arrS = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(arrS);
        statusSpinner.setEnabled(false);

        db = helper.getWritableDatabase();

        /* 학년 확인 */
        Cursor cursor = db.rawQuery("SELECT * FROM student", null);
        while (cursor.moveToNext()) {
            if (intent.getExtras().getString("id").equals(cursor.getString(cursor.getColumnIndex("id")))) {
                leveltxt.setText(cursor.getString(cursor.getColumnIndex("level")));
            }
        }
        /* 휴학상태 확인 */
        cursor = db.rawQuery("SELECT * FROM hakjuk", null);
        while (cursor.moveToNext()) {
            if (intent.getExtras().getString("id").equals(cursor.getString(cursor.getColumnIndex("id")))) {
                switch (cursor.getString(cursor.getColumnIndex("status"))) {
                    case "재학":
                        statusSpinner.setSelection(1);
                        break;
                    case "일반휴학":
                        statusSpinner.setSelection(2);
                        break;
                    case "군휴학":
                        statusSpinner.setSelection(3);
                        break;
                }
            }
        }
        teltxt.setText(intent.getExtras().getString("tel"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /* 수정버튼을 눌렀을 때 */
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                idtxt.setEnabled(true);
                nametxt.setEnabled(true);
//                dptxt.setEnabled(true);
                teltxt.setEnabled(true);
//                leveltxt.setEnabled(true);
                statusSpinner.setEnabled(true);
            }
        });
        /* 저장버튼을 눌렀을 때 */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("UPDATE member SET name = '" + nametxt.getText().toString() +
                        "', tel = '" + teltxt.getText().toString() + "' WHERE id = '" + idtxt.getText().toString() + "';");
                db.execSQL("UPDATE hakjuk SET status = '" + statusSpinner.getSelectedItem().toString() + "' WHERE id = " + idtxt.getText().toString() + ";");
                nametxt.setEnabled(false);
                teltxt.setEnabled(false);
                statusSpinner.setEnabled(true);

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(student_imformation.this);
                dialog = builder.setMessage("저장 완료")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
            }
        });
    }
}
