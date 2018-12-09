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

public class MemberListAdapter extends BaseAdapter{

    private Context context;
    private List<Member> memberList;
//    MySQLiteOpenHelper helper;
//    SQLiteDatabase db;

    public MemberListAdapter(Context context, List<Member> memberList ){
        this.context = context;
        this.memberList = memberList;
//        helper.setDB(context, "member.db");
//        helper = new MySQLiteOpenHelper(context);
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.mitem,null);
        final TextView memberTitle = (TextView) v.findViewById(R.id.memberTitle);
        TextView memberDep = (TextView) v.findViewById(R.id.memberDep);
        TextView memberID = (TextView) v.findViewById(R.id.memberID);
        TextView memberTel = (TextView) v.findViewById(R.id.memberTel);

        memberTitle.setText(memberList.get(i).getMemberTitle());
        memberDep.setText("소속 : " + memberList.get(i).getMemberDep());
        if(memberList.get(i).getMemberID().charAt(0) == '2') {
            memberID.setText("학번 : " + memberList.get(i).getMemberID());
        }
        else
            memberID.setText("교번 : " + memberList.get(i).getMemberID());
        memberTel.setText("연락처 : " + memberList.get(i).getMemberTel());

        v.setTag(memberList.get(i).getId());
        return v;
    }
}
