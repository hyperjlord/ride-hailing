package top.wx.pojo;

public class Car {
    private String driver_id;
    private String car_number;
    private String brand;
    private String model;
    private String color;
    private String cpic_url;

    public String getDriverId() {
        return driver_id;
    }
    public void setDriverId(String driver_id) { this.driver_id = driver_id; }

    public String getCarNumber(){ return car_number; }
    public void setCarNumber(String car_number) { this.car_number = car_number; }

    public String getBrand(){ return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel(){ return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor(){ return color; }
    public void setColor(String color) { this.color = color; }

    public String getPicUrl() { return cpic_url; }
    public void setPicUrl(String cpic_url) { this.cpic_url=cpic_url; }
}
