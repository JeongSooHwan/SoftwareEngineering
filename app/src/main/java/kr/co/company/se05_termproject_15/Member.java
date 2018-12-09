package kr.co.company.se05_termproject_15;

public class Member {
    int id;
    String memberTitle;
    String memberDep;
    String memberID;
    String memberTel;

    public Member(int id, String memberTitle, String memberDep, String memberID, String memberTel) {
        this.id = id;
        this.memberTitle = memberTitle;
        this.memberDep = memberDep;
        this.memberID = memberID;
        this.memberTel = memberTel;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberTitle() {
        return memberTitle;
    }

    public void setMemberTitle(String memberTitle) {
        this.memberTitle = memberTitle;
    }

    public String getMemberDep() {
        return memberDep;
    }

    public void setMemberDep(String memberDep) {
        this.memberDep = memberDep;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }


}
