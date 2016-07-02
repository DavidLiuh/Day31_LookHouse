package com.qf.day31_lookhouse.model;

/**
 * 城市实体类
 */
public class CitiyEntity {

    private int cityid;
    private String cityname;
    private int type = 1;//当前的类型， 0 - 表示标签， 1 - 表示城市

    public CitiyEntity() {
    }

    public CitiyEntity(String cityname, int type) {
        this.cityname = cityname;
        this.type = type;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CitiyEntity{" +
                "cityname='" + cityname + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * 判断当前对象和label是否相等
     * @param label
     * @return
     */
    public boolean eqLabel(String label){
        if(type == 0 && cityname.equals(label)){
            return true;
        } else {
            return false;
        }
    }
}
