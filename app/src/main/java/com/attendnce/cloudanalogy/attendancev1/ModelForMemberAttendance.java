package com.attendnce.cloudanalogy.attendancev1;

public class ModelForMemberAttendance {

    public String username;
    public String timein;
    public String timeout;
    public String totalhour;

   public ModelForMemberAttendance()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTotalhour() {
        return totalhour;
    }

    public void setTotalhour(String totalhour) {
        this.totalhour = totalhour;
    }



    public ModelForMemberAttendance(String username, String timein, String timeout, String totalhour) {
        this.username = username;
        this.timein = timein;
        this.timeout = timeout;
        this.totalhour = totalhour;
    }

}
