package kr.co.company.se05_termproject_15;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class gradeListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
//    MySQLiteOpenHelper helper;
//    SQLiteDatabase db;

    public gradeListAdapter(Context context, List<Course> courseList ){
        this.context = context;
        this.courseList = courseList;
//        helper.setDB(context, "member.db");
//        helper = new MySQLiteOpenHelper(context);
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
        View v = View.inflate(context, R.layout.gradeitem,null);
        final TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseGrade.setText(courseList.get(i).getCourseGrade());
        v.setTag(courseList.get(i).getId());

        return v;
    }
}