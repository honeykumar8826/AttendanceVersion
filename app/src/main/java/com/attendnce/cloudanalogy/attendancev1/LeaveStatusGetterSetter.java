package com.attendnce.cloudanalogy.attendancev1;

public class LeaveStatusGetterSetter {
    String subject     ;
    String LeaveType   ;
    String tlapproval  ;
    String hrapproval  ;
    String description ;

    String leavestatus;

    String  AppliedDate;
    String startdate;



    String enddate;



    String createddate;



    public LeaveStatusGetterSetter(String subject, String leaveType, String tlapproval, String hrapproval, String description, String leavestatus, String appliedDate,
                                   String startdate,String enddate) {
        this.subject = subject;
        LeaveType = leaveType;
        this.tlapproval = tlapproval;
        this.hrapproval = hrapproval;
        this.description = description;
        this.leavestatus = leavestatus;
        this.AppliedDate = appliedDate;
        this.startdate = startdate;
        this.enddate = enddate;

    }

    public LeaveStatusGetterSetter(){

    }
















    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getLeavestatus() {
        return leavestatus;
    }
    public void setLeavestatus(String leavestatus) {
        this.leavestatus = leavestatus;
    }
    public String getAppliedDate() {
        return AppliedDate;
    }
    public void setAppliedDate(String appliedDate) {
        AppliedDate = appliedDate;
    }
    public String getstartdate() {
        return startdate;
    }
    public void setstartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getLeaveType() {
        return LeaveType;
    }
    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getTlapproval() {
        return tlapproval;
    }
    public void setTlapproval(String tlapproval) {
        this.tlapproval = tlapproval;
    }
    public String getHrapproval() {
        return hrapproval;
    }
    public void setHrapproval(String hrapproval) {
        this.hrapproval = hrapproval;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }
}
