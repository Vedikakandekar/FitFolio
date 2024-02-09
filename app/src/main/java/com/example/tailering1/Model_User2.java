package com.example.tailering1;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Model_User2 {
    String type,timestamp ;
    double amt,rate;
    int qty;
    ArrayList<String> remarks= new ArrayList<>();
    HashMap<String,String> measurnments = new HashMap<>();

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Model_User2(String type, double amt, double rate, int qty, ArrayList remarks, HashMap measurenments, String timestamp) {
        this.type = type;
        this.amt = amt;
        this.rate = rate;
        this.qty = qty;
        this.remarks = remarks;
        this.measurnments = measurenments;
        this.timestamp = timestamp;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public ArrayList<String> getRemarks() {
        return remarks;
    }

    public void setRemarks(ArrayList<String> remarks) {
        this.remarks = remarks;
    }

    public HashMap<String, String> getMeasurnments() {
        return measurnments;
    }

    public void setMeasurnments(HashMap<String, String> measurnments) {
        this.measurnments = measurnments;
    }
}
