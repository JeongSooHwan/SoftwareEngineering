package kr.co.company.se05_termproject_15;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.Random;

public class student_timetable extends AppCompatActivity {
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;
//    private AutoResizeTextView monarr[] = new AutoResizeTextView[11];
//    private AutoResizeTextView tuearr[] = new AutoResizeTextView[11];
//    private AutoResizeTextView wedarr[] = new AutoResizeTextView[11];
//    private AutoResizeTextView thuarr[] = new AutoResizeTextView[11];
//    private AutoResizeTextView friarr[] = new AutoResizeTextView[11];

    private TextView monarr[] = new TextView[11];
    private TextView tuearr[] = new TextView[11];
    private TextView wedarr[] = new TextView[11];
    private TextView thuarr[] = new TextView[11];
    private TextView friarr[] = new TextView[11];
    private int[] color = {R.drawable.selecttext, R.drawable.selecttxt2, R.drawable.selecttxt3, R.drawable.selecttxt4,R.drawable.selecttxt5,
            R.drawable.selecttxt6,R.drawable.selecttxt7,R.drawable.selecttxt8,R.drawable.selecttxt9, R.drawable.selecttxt10};

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        monarr[0] = findViewById(R.id.monday9);
        monarr[1] = findViewById(R.id.monday10);
        monarr[2] = findViewById(R.id.monday11);
        monarr[3] = findViewById(R.id.monday12);
        monarr[4] = findViewById(R.id.monday13);
        monarr[5] = findViewById(R.id.monday14);
        monarr[6] = findViewById(R.id.monday15);
        monarr[7] = findViewById(R.id.monday16);
        monarr[8] = findViewById(R.id.monday17);
        monarr[9] = findViewById(R.id.monday18);
        monarr[10] = findViewById(R.id.monday19);

        tuearr[0] = findViewById(R.id.tuesday9);
        tuearr[1] = findViewById(R.id.tuesday10);
        tuearr[2] = findViewById(R.id.tuesday11);
        tuearr[3] = findViewById(R.id.tuesday12);
        tuearr[4] = findViewById(R.id.tuesday13);
        tuearr[5] = findViewById(R.id.tuesday14);
        tuearr[6] = findViewById(R.id.tuesday15);
        tuearr[7] = findViewById(R.id.tuesday16);
        tuearr[8] = findViewById(R.id.tuesday17);
        tuearr[9] = findViewById(R.id.tuesday18);
        tuearr[10] = findViewById(R.id.tuesday19);

        wedarr[0] = findViewById(R.id.wednesday9);
        wedarr[1] = findViewById(R.id.wednesday10);
        wedarr[2] = findViewById(R.id.wednesday11);
        wedarr[3] = findViewById(R.id.wednesday12);
        wedarr[4] = findViewById(R.id.wednesday13);
        wedarr[5] = findViewById(R.id.wednesday14);
        wedarr[6] = findViewById(R.id.wednesday15);
        wedarr[7] = findViewById(R.id.wednesday16);
        wedarr[8] = findViewById(R.id.wednesday17);
        wedarr[9] = findViewById(R.id.wednesday18);
        wedarr[10] = findViewById(R.id.wednesday19);

        thuarr[0] = findViewById(R.id.thursday9);
        thuarr[1] = findViewById(R.id.thursday10);
        thuarr[2] = findViewById(R.id.thursday11);
        thuarr[3] = findViewById(R.id.thursday12);
        thuarr[4] = findViewById(R.id.thursday13);
        thuarr[5] = findViewById(R.id.thursday14);
        thuarr[6] = findViewById(R.id.thursday15);
        thuarr[7] = findViewById(R.id.thursday16);
        thuarr[8] = findViewById(R.id.thursday17);
        thuarr[9] = findViewById(R.id.thursday18);
        thuarr[10] = findViewById(R.id.thursday19);

        friarr[0] = findViewById(R.id.friday9);
        friarr[1] = findViewById(R.id.friday10);
        friarr[2] = findViewById(R.id.friday11);
        friarr[3] = findViewById(R.id.friday12);
        friarr[4] = findViewById(R.id.friday13);
        friarr[5] = findViewById(R.id.friday14);
        friarr[6] = findViewById(R.id.friday15);
        friarr[7] = findViewById(R.id.friday16);
        friarr[8] = findViewById(R.id.friday17);
        friarr[9] = findViewById(R.id.friday18);
        friarr[10] = findViewById(R.id.friday19);


        for (int i = 0; i < 11; i++) {
//            monarr[i].setText("가나다라가");
            monarr[i].setTextColor(Color.WHITE);
            monarr[i].setBackgroundResource(R.drawable.text2);
            monarr[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
//            tuearr[i].setText("가나다라가");
            tuearr[i].setTextColor(Color.WHITE);
            tuearr[i].setBackgroundResource(R.drawable.text2);
            tuearr[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
//            wedarr[i].setText("가나다라가");
            wedarr[i].setTextColor(Color.WHITE);
            wedarr[i].setBackgroundResource(R.drawable.text2);
            wedarr[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
//            thuarr[i].setText("가나다라가");
            thuarr[i].setTextColor(Color.WHITE);
            thuarr[i].setBackgroundResource(R.drawable.text2);
            thuarr[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
//            friarr[i].setText("가나다라가");
            friarr[i].setTextColor(Color.WHITE);
            friarr[i].setBackgroundResource(R.drawable.text2);
            friarr[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        }

        helper.setDB(this, "member.db");
        helper = new MySQLiteOpenHelper(this);
        db = helper.getReadableDatabase();
        int studentid = student.studentID;
        Cursor cursor = db.rawQuery("SELECT * FROM course, member, subject WHERE course.studentID = member.id AND course.subjectID = subject.s_id AND course.studentID =" +
                studentid, null);
//        Cursor cursor = db.rawQuery("SELECT * FROM subject", null);

        while (cursor.moveToNext()) {

            String title = "", yoil = "", yoil1 = "", yoil2 = "", start = "", start1 = "", start2 = "",
                    end = "", end1 = "", end2 = "", room = "", room1 = "", room2 = "";
            int s, s1, s2, e, e1, e2;

            title = cursor.getString(cursor.getColumnIndex("s_title"));
            yoil = cursor.getString(cursor.getColumnIndex("yoil"));
            start = cursor.getString(cursor.getColumnIndex("s_startTime"));
            end = cursor.getString(cursor.getColumnIndex("s_endTime"));
            room = cursor.getString(cursor.getColumnIndex("s_room"));
            Random ran = new Random();
            int num = ran.nextInt(color.length);
            if (yoil.contains(",")) {
                String yoilarr[] = yoil.split(",");
                yoil1 = yoilarr[0];
                yoil2 = yoilarr[1];

                String startarr[] = start.split(",");
                start1 = startarr[0];
                start2 = startarr[1];

                String endarr[] = end.split(",");
                end1 = endarr[0];
                end2 = endarr[1];

                String roomarr[] = room.split(",");
                room1 = roomarr[0];
                room2 = roomarr[1];

                /*String to Integer*/
                s1 = Integer.parseInt(start1);
                s2 = Integer.parseInt(start2);
                e1 = Integer.parseInt(end1);
                e2 = Integer.parseInt(end2);



                switch (yoil1) {
                    case "월":
                        for (int i = s1 - 9; i < e1 - 9; i++) {
                            monarr[i].setBackgroundResource(color[num]);
                            if (i == (s1 - 9)) {
                                monarr[s1 - 9].setText(title);
                                monarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "화":
                        for (int i = s1 - 9; i < e1 - 9; i++) {
                            tuearr[i].setBackgroundResource(color[num]);
                            if (i == (s1 - 9)) {
                                tuearr[s1 - 9].setText(title);
                                tuearr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "수":
                        for (int i = s1 - 9; i < e1 - 9; i++) {
                            wedarr[i].setBackgroundResource(color[num]);
                            if (i == (s1 - 9)) {
                                wedarr[s1 - 9].setText(title);
                                wedarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "목":
                        for (int i = s1 - 9; i < e1 - 9; i++) {
                            thuarr[i].setBackgroundResource(color[num]);
                            if (i == (s1 - 9)) {
                                thuarr[s1 - 9].setText(title);
                                thuarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "금":
                        for (int i = s1 - 9; i < e1 - 9; i++) {
                            friarr[i].setBackgroundResource(color[num]);
                            if (i == (s1 - 9)) {
                                friarr[s1 - 9].setText(title);
                                friarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;

                }

                switch (yoil2) {
                    case "월":
                        for (int i = s2 - 9; i < e2 - 9; i++) {
                            monarr[i].setBackgroundResource(color[num]);
                            if (i == (s2 - 9)) {
                                monarr[s2 - 9].setText(title);
                                monarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "화":
                        for (int i = s2 - 9; i < e2 - 9; i++) {
                            tuearr[i].setBackgroundResource(color[num]);
                            if (i == (s2 - 9)) {
                                tuearr[s2 - 9].setText(title);
                                tuearr[i].setTextColor(Color.BLACK);
                            }
                        }

                        break;
                    case "수":
                        for (int i = s2 - 9; i < e2 - 9; i++) {
                            wedarr[i].setBackgroundResource(color[num]);
                            if (i == (s2 - 9)) {
                                wedarr[s2 - 9].setText(title);
                                wedarr[i].setTextColor(Color.BLACK);
                            }
                        }

                        break;
                    case "목":
                        for (int i = s2 - 9; i < e2 - 9; i++) {
                            thuarr[i].setBackgroundResource(color[num]);
                            if (i == (s2 - 9)) {
                                thuarr[s2 - 9].setText(title);
                                thuarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "금":
                        for (int i = s2 - 9; i < e2 - 9; i++) {
                            friarr[i].setBackgroundResource(color[num]);
                            if (i == (s2 - 9)) {
                                friarr[s2 - 9].setText(title);
                                friarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;

                }
            } else {    //일주일에 한번만 수강하는 경우
                s = Integer.parseInt(start);
                e = Integer.parseInt(end);
                switch (yoil) {
                    case "월":
                        for (int i = s - 9; i < e - 9; i++) {
                            monarr[i].setBackgroundResource(color[num]);
                            if (i == (s - 9)) {
                                monarr[s - 9].setText(title);
                                monarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "화":
                        for (int i = s - 9; i < e - 9; i++) {
                            tuearr[i].setBackgroundResource(color[num]);
                            if (i == (s - 9)) {
                                tuearr[s - 9].setText(title);
                                tuearr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "수":
                        for (int i = s - 9; i < e - 9; i++) {
                            wedarr[i].setBackgroundResource(color[num]);
                            if (i == (s - 9)) {
                                wedarr[s - 9].setText(title);
                                wedarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "목":
                        for (int i = s - 9; i < e - 9; i++) {
                            thuarr[i].setBackgroundResource(color[num]);
                            if (i == (s - 9)) {
                                thuarr[s - 9].setText(title);
                                thuarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                    case "금":
                        for (int i = s - 9; i < e - 9; i++) {
                            friarr[i].setBackgroundResource(color[num]);
                            if (i == (s - 9)) {
                                friarr[s - 9].setText(title);
                                friarr[i].setTextColor(Color.BLACK);
                            }
                        }
                        break;
                }
            }


        }//cursor 이동 while문


//        for (int i = 0; i < 11; i++) {
//            monarr[i].resizeText();
//            tuearr[i].resizeText();
//            wedarr[i].resizeText();
//            thuarr[i].resizeText();
//            friarr[i].resizeText();
//        }
    }
}
