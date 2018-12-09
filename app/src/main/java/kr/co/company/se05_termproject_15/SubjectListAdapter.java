package kr.co.company.se05_termproject_15;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SubjectListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    public SubjectListAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        helper.setDB(context, "member.db");
        helper = new MySQLiteOpenHelper(context);
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item, null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseID = (TextView) v.findViewById(R.id.courseID);
        final TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseCapacity = (TextView) v.findViewById(R.id.courseCapacity);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from assign, subject,member WHERE subject.s_id = assign.subjectID AND assign.professorID = member.id", null);

        int subjectID = 0;
        while (cursor.moveToNext()) {
            if (courseList.get(i).getCourseTitle().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                courseProfessor.setText(" ( " + cursor.getString(cursor.getColumnIndex("name")) + " )");
            }
        }
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseCapacity.setText("정원 : " + courseList.get(i).getCourseCapacity() + "명");
        courseID.setText("학수번호 : " + courseList.get(i).getId());

        if (courseList.get(i).getYoil().contains(",")) {
            String[] yoil = courseList.get(i).getYoil().split(",");
            String[] startTime = courseList.get(i).getCourseSTime().split(",");
            String[] endTime = courseList.get(i).getCourseETime().split(",");

            courseTime.setText("강의 시간 : " + yoil[0] + " " + startTime[0] + "~" + endTime[0] + "  " + yoil[1] + " " + startTime[1] + "~" + endTime[1]);
        } else {
            courseTime.setText("강의 시간 : " + courseList.get(i).getYoil() + " " + courseList.get(i).getCourseSTime() + "~" + courseList.get(i).getCourseETime());
        }
        v.setTag(courseList.get(i).getId());


        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setTag(i);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int studentID = student.studentID; // 학번
                int subjectID = 0; // 학수번호
                int position = (Integer) view.getTag();
                String[] yoil = new String[0];
                String[] startTime = new String[0];
                String[] endTime = new String[0];

                String one_yoil = null;
                int one_start = 0;
                int one_end = 0;

                boolean one = false, two = false, register = true, name = true; // 강의시간의 갯수 플래그

                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);

                while (cursor.moveToNext()) {
                    if (courseTitle.getText().toString().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                        subjectID = cursor.getInt(cursor.getColumnIndex("s_id")); // 학수번호 저장
                    }
                }
                /* 클릭된 강의의 강의시간 가져오기 */
                if (courseList.get(position).getYoil().contains(",")) { // 강의 시간이 2개 일 경우
                    yoil = courseList.get(position).getYoil().split(",");
                    startTime = courseList.get(position).getCourseSTime().split(",");
                    endTime = courseList.get(position).getCourseETime().split(",");
                    two = true;
                } else {  // 강의 시간이 1개 일 경우
                    one_yoil = courseList.get(position).getYoil();
                    one_start = Integer.parseInt(courseList.get(position).getCourseSTime());
                    one_end = Integer.parseInt(courseList.get(position).getCourseETime());
                    one = true;
                }

                cursor = db.rawQuery("SELECT * FROM course, subject WHERE course.studentID = " +
                        studentID + " AND course.subjectID = subject.s_id", null);
                while (cursor.moveToNext()) {
                    if (one == true) { // 강의 시간이 1개 일 경우
                        if (cursor.getString(cursor.getColumnIndex("s_title")).equals(courseList.get(position).getCourseTitle())) {
//                            Toast.makeText(context, "강의제목 중복", Toast.LENGTH_SHORT).show();
                            name = false;
                            register = false;
                            break;
                        } else if (!cursor.getString(cursor.getColumnIndex("s_startTime")).contains(",")) {
                            String yo1 = cursor.getString(cursor.getColumnIndex("yoil"));
                            int start1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_startTime")));
                            int end1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_endTime")));
                            if (one_yoil.equals(yo1)) {
                                if ((start1 <= one_start && one_start < end1) ||
                                        (start1 < one_end && one_end <= end1)) {
//                                    Toast.makeText(context, one_yoil + " " + yo1 + " 1 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, one_yoil + " " + yo1 + " 1 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            }
                        } else if (cursor.getString(cursor.getColumnIndex("s_startTime")).contains(",")) {
                            String[] s_yoil = cursor.getString(cursor.getColumnIndex("yoil")).split(",");
                            String[] s_startTime = cursor.getString(cursor.getColumnIndex("s_startTime")).split(",");
                            String[] s_endTime = cursor.getString(cursor.getColumnIndex("s_endTime")).split(",");

                            if (one_yoil.equals(s_yoil[0])) {
                                if ((Integer.parseInt(s_startTime[0]) <= one_start && one_start < Integer.parseInt(s_endTime[0])) ||
                                        (Integer.parseInt(s_startTime[0]) < one_end && one_end <= Integer.parseInt(s_endTime[0]))) {
//                                    Toast.makeText(context, one_yoil + " " + s_yoil[0] + " 2 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, one_yoil + " " + s_yoil[0] + " 2 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (one_yoil.equals(s_yoil[1])) {
                                if ((Integer.parseInt(s_startTime[1]) <= one_start && one_start < Integer.parseInt(s_endTime[1])) ||
                                        (Integer.parseInt(s_startTime[1]) < one_end && one_end <= Integer.parseInt(s_endTime[1]))) {
//                                    Toast.makeText(context, one_yoil + " " + s_yoil[1] + "3 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, one_yoil + " " + s_yoil[1] + "3 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            }
                        } else {
//                            db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
                            register = true;
                        }
                    } else if (two == true) {
                        if (cursor.getString(cursor.getColumnIndex("s_title")).equals(courseList.get(position).getCourseTitle())) {
//                            Toast.makeText(context, "강의제목 중복", Toast.LENGTH_SHORT).show();
                            name = false;
                            register = false;
                            break;
                        } else if (!cursor.getString(cursor.getColumnIndex("s_startTime")).contains(",")) { // 강의시간이 1개인 경우
                            String yo1 = cursor.getString(cursor.getColumnIndex("yoil"));
                            int start1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_startTime")));
                            int end1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("s_endTime")));
                            if (yoil[0].equals(yo1)) { // 선택된 첫번째 강의요일과 일치할 경우
                                if ((start1 <= Integer.parseInt(startTime[0]) && Integer.parseInt(startTime[0]) < end1) ||
                                        (start1 < Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[0]) <= end1)) {
//                                    Toast.makeText(context, yoil[0] + " " + yo1 + " 1 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[0] + " " + yo1 + " 1 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (yoil[1].equals(yo1)) { // 선택된 두번째 강의요일과 일치할 경우
                                if ((start1 <= Integer.parseInt(startTime[1]) && Integer.parseInt(startTime[1]) < end1) ||
                                        (start1 < Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[1]) <= end1)) {
//                                    Toast.makeText(context, yoil[1] + " " + yo1 + " 2 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[1] + " " + yo1 + " 2 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            }
                        } else if (cursor.getString(cursor.getColumnIndex("s_startTime")).contains(",")) { // 강의시간이 2개인 경우
                            String[] s_yoil = cursor.getString(cursor.getColumnIndex("yoil")).split(",");
                            String[] s_startTime = cursor.getString(cursor.getColumnIndex("s_startTime")).split(",");
                            String[] s_endTime = cursor.getString(cursor.getColumnIndex("s_endTime")).split(",");

                            if (yoil[0].equals(s_yoil[0]) && !yoil[1].equals(s_yoil[1])) { // 선택된 첫번째 강의요일과 커서로 선택된 첫번째 강의요일이 같은 경우
                                if ((Integer.parseInt(s_startTime[0]) <= Integer.parseInt(startTime[0]) && Integer.parseInt(startTime[0]) < Integer.parseInt(s_endTime[0])) ||
                                        (Integer.parseInt(s_startTime[0]) < Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[0]) <= Integer.parseInt(s_endTime[0]))) {
//                                    Toast.makeText(context, yoil[0] + " " + s_yoil[0] + " 3 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[0] + " " + s_yoil[0] + " 3 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (yoil[0].equals(s_yoil[1]) && !yoil[1].equals(s_yoil[0])) { // 선택된 첫번째 강의요일과 커서로 선택된 두번째 강의요일이 같은 경우
                                if ((Integer.parseInt(s_startTime[1]) <= Integer.parseInt(startTime[0]) && Integer.parseInt(startTime[0]) < Integer.parseInt(s_endTime[1])) ||
                                        (Integer.parseInt(s_startTime[1]) < Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[0]) <= Integer.parseInt(s_endTime[1]))) {
//                                    Toast.makeText(context, yoil[0] + " " + s_yoil[1] + " 4 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[0] + " " + s_yoil[1] + " 4 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (yoil[1].equals(s_yoil[0]) && !yoil[0].equals(s_yoil[1])) {// 선택된 두번째 강의요일과 커서로 선택된 첫번째 강의요일이 같은 경우
                                if ((Integer.parseInt(s_startTime[0]) <= Integer.parseInt(startTime[1]) && Integer.parseInt(startTime[1]) < Integer.parseInt(s_endTime[0])) ||
                                        (Integer.parseInt(s_startTime[0]) < Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[1]) <= Integer.parseInt(s_endTime[0]))) {
//                                    Toast.makeText(context, yoil[1] + " " + s_yoil[0] + " 5 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[1] + " " + s_yoil[0] + " 5 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (yoil[1].equals(s_yoil[1]) && !yoil[0].equals(s_yoil[0])) {// 선택된 두번째 강의요일과 커서로 선택된 두번째 강의요일이 같은 경우
                                if ((Integer.parseInt(s_startTime[1]) <= Integer.parseInt(startTime[1]) && Integer.parseInt(startTime[1]) < Integer.parseInt(s_endTime[1])) ||
                                        (Integer.parseInt(s_startTime[1]) < Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[1]) <= Integer.parseInt(s_endTime[1]))) {
//                                    Toast.makeText(context, yoil[1] + " " + s_yoil[1] + " 6 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
//                                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
//                                    Toast.makeText(context, yoil[1] + " " + s_yoil[1] + " 6 등록 가능", Toast.LENGTH_SHORT).show();
                                    register = true;
                                }
                            } else if (yoil[1].equals(s_yoil[1]) && yoil[0].equals(s_yoil[0])) {// 선택된 강의요일과 커서로 선택된 강의요일이 모두 같은 경우
                                if ((Integer.parseInt(s_startTime[1]) <= Integer.parseInt(startTime[1]) && Integer.parseInt(startTime[1]) < Integer.parseInt(s_endTime[1])) ||
                                        (Integer.parseInt(s_startTime[1]) < Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[1]) <= Integer.parseInt(s_endTime[1]))) {
//                                    Toast.makeText(context, yoil[1] + " " + s_yoil[1] + " 7 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else if ((Integer.parseInt(s_startTime[0]) <= Integer.parseInt(startTime[0]) && Integer.parseInt(startTime[0]) < Integer.parseInt(s_endTime[0])) ||
                                        (Integer.parseInt(s_startTime[0]) < Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[0]) <= Integer.parseInt(s_endTime[0]))) {
//                                    Toast.makeText(context, yoil[0] + " " + s_yoil[0] + " 7 등록 불가", Toast.LENGTH_SHORT).show();
                                    register = false;
                                    break;
                                } else {
                                    register = true;
                                }
                            }

                        } else {
                            register = true;
                        }
                    }
                }

                cursor = db.rawQuery("SELECT * FROM course WHERE studentID =" + studentID + " AND subjectID = " + subjectID, null);
//                Toast.makeText(context,cursor.moveToNext() + "",Toast.LENGTH_SHORT).show();
                int credit = courseList.get(position).getCourseCredit();
                int result = credit + ((student_apply) student_apply.mContext).credit();
                if (cursor.moveToNext() == false && register == true && result <= 18) {
                    db.execSQL("INSERT INTO course VALUES(" + studentID + "," + subjectID + "," + null + "," + null + ");");
                    ((student_apply) student_apply.mContext).credit();
                    ((student_apply) student_apply.mContext).alertShow(0);
                } else if (result > 18) {
                    ((student_apply) student_apply.mContext).alertShow(4);
                } else if (name == false) {
                    ((student_apply) student_apply.mContext).alertShow(2);
                } else {
                    ((student_apply) student_apply.mContext).alertShow(3);
                }
            }
        });
        return v;
    }
}
