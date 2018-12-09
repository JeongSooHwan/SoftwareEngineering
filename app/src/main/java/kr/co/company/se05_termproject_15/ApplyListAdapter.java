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

public class ApplyListAdapter extends BaseAdapter{

    private Context context;
    private List<Course> courseList;
    MySQLiteOpenHelper helper;
    SQLiteDatabase db;

    public ApplyListAdapter(Context context, List<Course> courseList ){
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
        View v = View.inflate(context, R.layout.applyitem,null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseProfessor = (TextView)v.findViewById(R.id.courseProfessor);
        TextView courseID = (TextView) v.findViewById(R.id.courseID);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseCapacity = (TextView) v.findViewById(R.id.courseCapacity);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from assign, subject,member WHERE subject.s_id = assign.subjectID AND assign.professorID = member.id", null);

        int subjectID = 0;
        while (cursor.moveToNext()) {
            if(courseList.get(i).getCourseTitle().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                courseProfessor.setText(" ( " + cursor.getString(cursor.getColumnIndex("name")) + " )");
            }
        }
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseCapacity.setText("정원 : "+courseList.get(i).getCourseCapacity() + "명");
        courseID.setText("학수번호 : " + courseList.get(i).getId());

        if(courseList.get(i).getYoil().contains(",")) {
            String[] yoil = courseList.get(i).getYoil().split(",");
            String[] startTime = courseList.get(i).getCourseSTime().split(",");
            String[] endTime = courseList.get(i).getCourseETime().split(",");

            courseTime.setText("강의 시간 : " + yoil[0] + " " + startTime[0] + "~" + endTime[0] + "  " + yoil[1] + " " + startTime[1] + "~" + endTime[1]);
        }
        else{
            courseTime.setText("강의 시간 : " + courseList.get(i).getYoil() + " " + courseList.get(i).getCourseSTime() + "~" + courseList.get(i).getCourseETime() );
        }
        v.setTag(courseList.get(i).getId());


        Button delButton = (Button) v.findViewById(R.id.delButton);
        delButton.setTag(i);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int studentID = student.studentID;
                int subjectID = 0;
                int position = (Integer) view.getTag();
                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM subject", null);
                while (cursor.moveToNext()) {
                    if(courseTitle.getText().toString().equals(cursor.getString(cursor.getColumnIndex("s_title")))) {
                        subjectID = cursor.getInt(cursor.getColumnIndex("s_id"));
                    }
                }
                db.execSQL("DELETE FROM course WHERE studentID = " + studentID + " AND subjectID = "+ subjectID + ";");

                courseList.remove(position);
                ((student_apply)student_apply.mContext).credit();
                ((student_apply)student_apply.mContext).alertShow(1);
                notifyDataSetChanged();

            }
        });
        return v;
    }
}
