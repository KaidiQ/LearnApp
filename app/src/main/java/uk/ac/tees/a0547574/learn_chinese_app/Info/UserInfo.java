package uk.ac.tees.a0547574.learn_chinese_app.Info;

public class UserInfo {
    public long rowid;
    public int Idnumber;
    public String name;
    public int age;
    public long height;
    public float weight;
    public boolean married;
    public String update_time;
    public String phone;
    public String password;

    public UserInfo() {
        rowid = 0l;
        Idnumber = 0;
        name = "";
        age = 0;
        height = 0l;
        weight = 0.0f;
        married = false;
        update_time = "";
        phone = "";
        password = "";
    }
}
