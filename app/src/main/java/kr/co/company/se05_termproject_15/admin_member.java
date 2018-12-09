package kr.co.company.se05_termproject_15;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class admin_member extends AppCompatActivity {
    TextView txt;
    EditText user_id, user_name, user_pwd, user_pwd2, user_tel, del_id, del_pwd;
    ListView lv;
    LinearLayout addform, delform;
    private ArrayAdapter arrA, arrL, arrP;
    private Spinner spinner, levelSpinner, posSpinner;
    private MemberListAdapter adapter;
    private List<Member> memberList;
    private RadioGroup radioGroup;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_member);

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getWritableDatabase();

//        spinner 설정
        spinner = (Spinner) findViewById(R.id.depSpinner);
        arrA = ArrayAdapter.createFromResource(this, R.array.dep, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrA);

        levelSpinner = (Spinner) findViewById(R.id.levelSpinner);
        arrL = ArrayAdapter.createFromResource(this, R.array.level, android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(arrL);
        levelSpinner.setEnabled(false);

        posSpinner = (Spinner) findViewById(R.id.positionSpinner);
        arrP = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_dropdown_item);
        posSpinner.setAdapter(arrP);
        posSpinner.setEnabled(false);

        txt = (TextView) findViewById(R.id.numbertxt);
        final Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        txt.setText(name);

        Button applybtn = (Button) findViewById(R.id.listbtn); // 사용자 조회 버튼
        Button addbtn = (Button) findViewById(R.id.addbtn); // 회원 등록 버튼
        Button delbtn = (Button) findViewById(R.id.delbtn); // 회원 삭제 버튼
        final RadioButton stuBtn = (RadioButton) findViewById(R.id.stuBtn);
        final RadioButton proBtn = (RadioButton) findViewById(R.id.proBtn);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        final Button registerbtn = (Button) findViewById(R.id.registerbtn); // 등록 버튼
        final Button deletebtn = (Button) findViewById(R.id.deletebtn); // 등록 버튼
        addform = (LinearLayout) findViewById(R.id.addform); // 회원등록 폼
        delform = (LinearLayout) findViewById(R.id.delform); // 회원삭제 폼

        user_id = (EditText) findViewById(R.id.user_id);
        user_name = (EditText) findViewById(R.id.user_name);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        user_pwd2 = (EditText) findViewById(R.id.user_pwd2);
        user_tel = (EditText) findViewById(R.id.user_tel);

        del_id = (EditText) findViewById(R.id.del_id);
        del_pwd = (EditText) findViewById(R.id.del_pwd);


        lv = (ListView) findViewById(R.id.listview);
        memberList = new ArrayList<Member>();
        adapter = new MemberListAdapter(getApplicationContext(), memberList);
        lv.setAdapter(adapter);


        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(View.VISIBLE);
                addform.setVisibility(View.GONE);
                delform.setVisibility(View.GONE);
                memberList.clear();
                int id;
                String memberTitle;
                String memberDep;
                String memberID;
                String memberTel;

                db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM member", null);
                while (cursor.moveToNext()) {
                    if(!cursor.getString(cursor.getColumnIndex("id")).contains("A")) {
                        id = cursor.getInt(cursor.getColumnIndex("id"));
                        memberTitle = cursor.getString(cursor.getColumnIndex("name"));
                        memberDep = cursor.getString(cursor.getColumnIndex("department"));
                        memberID = cursor.getString(cursor.getColumnIndex("id"));
                        memberTel = cursor.getString(cursor.getColumnIndex("tel"));
                        Member member = new Member(id, memberTitle, memberDep, memberID, memberTel);
                        memberList.add(member);
                    }
                }

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(admin_member.this);
                dialog = builder.setMessage("조회하였습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                adapter.notifyDataSetChanged();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(View.GONE);
                addform.setVisibility(View.VISIBLE);
                delform.setVisibility(View.GONE);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.stuBtn:
                                Toast.makeText(getApplicationContext(), "학생", Toast.LENGTH_SHORT).show();
                                levelSpinner.setEnabled(true);
                                posSpinner.setEnabled(false);
                                break;
                            case R.id.proBtn:
                                Toast.makeText(getApplicationContext(), "교수", Toast.LENGTH_SHORT).show();
                                levelSpinner.setEnabled(false);
                                posSpinner.setEnabled(true);
                                break;
                        }
                    }
                });


                registerbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean suc = false;
                        boolean flag = false;
                        boolean levelflag = false;
                        boolean posflag = false;

                        if (user_id.getText().toString().getBytes().length <= 0 || user_name.getText().toString().getBytes().length <= 0 ||
                                user_pwd.getText().toString().getBytes().length <= 0 || user_pwd2.getText().toString().getBytes().length <= 0 ||
                                user_tel.getText().toString().getBytes().length <= 0 || spinner.getSelectedItem().toString() == "") { //빈값이 넘어올때의 처리
                            Toast.makeText(getApplicationContext(), "빈 칸을 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else if (!user_pwd.getText().toString().equals(user_pwd2.getText().toString())) {
                            user_pwd.setText("");
                            user_pwd2.setText("");
                            Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            flag = true;
                            Cursor cursor = db.rawQuery("SELECT * FROM member", null);
                            while (cursor.moveToNext()) {
                                if (user_id.getText().toString().equals(cursor.getString(cursor.getColumnIndex("id")))) {
                                    Toast.makeText(getApplicationContext(), "이미 존재하는 ID 입니다", Toast.LENGTH_SHORT).show();
                                    user_id.setText("");
                                    suc = true;
                                }
                            }
                        }


                        if (!stuBtn.isChecked() && !proBtn.isChecked()) {
                            Toast.makeText(getApplicationContext(), "학생 또는 교수를 체크해주세요.", Toast.LENGTH_SHORT).show();
                        }

                        if (stuBtn.isChecked() && !levelSpinner.getSelectedItem().toString().contains("-")) {
                            levelflag = true;
                        } else if (stuBtn.isChecked() && levelSpinner.getSelectedItem().toString().contains("-")) {
                            Toast.makeText(getApplicationContext(), "학년을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                            levelSpinner.setSelection(0);
                        }

                        if (proBtn.isChecked() && !posSpinner.getSelectedItem().toString().contains("-")) {
                            posflag = true;
                        } else if (proBtn.isChecked() && posSpinner.getSelectedItem().toString().contains("-")) {
                            Toast.makeText(getApplicationContext(), "직급을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                            posSpinner.setSelection(0);
                        }

                        if (suc == false && flag == true && (levelflag == true || posflag == true)) {
                            int id = Integer.parseInt(user_id.getText().toString());
                            String pwd = user_pwd.getText().toString();
                            String name = user_name.getText().toString();
                            String dep = spinner.getSelectedItem().toString();
                            String level = levelSpinner.getSelectedItem().toString().substring(0, 1);
                            String pos = posSpinner.getSelectedItem().toString();
                            String tel = user_tel.getText().toString();

                            db.execSQL("INSERT INTO member VALUES(" + id + ", '" + pwd + "', '" + name + "', '" + dep + "', '" + tel + "' );");
                            if (user_id.getText().toString().charAt(0) == '2')
                                db.execSQL("INSERT INTO student VALUES(" + id + "," + level + ");");
                            else
                                db.execSQL("INSERT INTO professor VALUES(" + id + ", '" + pos + "' );");
                            db.close();

                            user_id.setText("");
                            user_pwd.setText("");
                            user_pwd2.setText("");
                            user_name.setText("");
                            spinner.setSelection(0);
                            levelSpinner.setSelection(0);
                            posSpinner.setSelection(0);
                            levelSpinner.setEnabled(false);
                            posSpinner.setEnabled(false);
                            user_tel.setText("");

                            AlertDialog dialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(admin_member.this);
                            dialog = builder.setMessage("등록 성공")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                        }
                    }

                });

            }
        });

        delbtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                lv.setVisibility(View.GONE);
                addform.setVisibility(View.GONE);
                delform.setVisibility(View.VISIBLE);

                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean delF = false;
                        db = helper.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM member", null);
                        while (cursor.moveToNext()) {
                            if (del_id.getText().toString().equals(cursor.getString(cursor.getColumnIndex("id"))) &&
                                    del_pwd.getText().toString().equals(cursor.getString(cursor.getColumnIndex("pwd")))) {
                                db.execSQL("DELETE FROM member WHERE id = '" + del_id.getText().toString() + "';");
                                delF = true;
                                AlertDialog dialog;
                                AlertDialog.Builder builder = new AlertDialog.Builder(admin_member.this);
                                dialog = builder.setMessage("삭제 성공")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                del_id.setText("");
                                del_pwd.setText("");
                                break;
                            }
                        }
                        if (delF == false) {
                            Toast.makeText(getApplicationContext(), "ID 또는 패스워드를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                            del_id.setText("");
                            del_pwd.setText("");
                        }
                    }
                });
            }
        });
    }

}
