package top.wx.pojo;

public class Driver {
    private String driver_id;
    private String password;
    private String name;
    private String sex;
    private Double score;
    private String id_card;
    private String drive_age;
    private Double balance;
    private String dpic_url;

    public String getDriverId() {
        return driver_id;
    }
    public void setDriverId(String driver_id) { this.driver_id = driver_id; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) { this.score = score; }

    public String getIdCard() { return id_card; }
    public void setIdCard(String id_card) {
        this.id_card = id_card;
    }

    public String getDriveAge() {
        return drive_age;
    }
    public void setDriveAge(String drive_age) {
        this.drive_age = drive_age;
    }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPicUrl() {
        return dpic_url;
    }
    public void setPicUrl(String dpic_url) { this.dpic_url = dpic_url; }
}
