package com.example.tailering1;

import java.util.ArrayList;

public class Model_User {
    String type , rate,timestamp;

    Model_User()
    {}

    public Model_User(String type , String rate , String timestamp){
        this.type = type;
        this.rate = rate;
        this.timestamp = timestamp;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
