package com.example.tailering1;

import java.util.Date;

public class Model_User3 {
    String custname, custmob, timestamp;
    String totalamt;
    CharSequence date;


    public Model_User3(String custname, String custmob, String totalamt,CharSequence date , String timestamp) {
        this.custname = custname;
        this.custmob = custmob;
        this.totalamt = totalamt;
        this.date = date;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustmob() {
        return custmob;
    }

    public void setCustmob(String custmob) {
        this.custmob = custmob;
    }

    public String getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(String totalamt) {
        this.totalamt = totalamt;
    }

    public CharSequence getDate() {
        return date;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }
}
