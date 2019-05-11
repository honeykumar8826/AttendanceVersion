package com.attendnce.cloudanalogy.attendancev1;

public class Birthday_AniversaryGetterSetter {


    public String dateofjoining;
    public String dateofbirtday;
    public String img_attach;

    public String name;



    public Birthday_AniversaryGetterSetter(String dateofjoining, String dateofbirtday, String img_attach) {
        this.dateofjoining = dateofjoining;
        this.dateofbirtday = dateofbirtday;
        this.img_attach = img_attach;
    }

    public Birthday_AniversaryGetterSetter(){

    }

    public String getDateofjoining() {
        return dateofjoining;
    }
    public void setDateofjoining(String dateofjoining) {
        this.dateofjoining = dateofjoining;
    }
    public String getDateofbirtday() {
        return dateofbirtday;
    }

    public void setDateofbirtday(String dateofbirtday) {
        this.dateofbirtday = dateofbirtday;
    }

    public String getImg_attach() {
        return img_attach;
    }
    public void setImg_attach(String img_attach) {
        this.img_attach = img_attach;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
