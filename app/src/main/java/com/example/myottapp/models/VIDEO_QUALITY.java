package com.example.myottapp.models;

public class VIDEO_QUALITY {

    String zero;

    String one;

    String two;

    String three;

    public VIDEO_QUALITY() {
    }

    public VIDEO_QUALITY(String zero, String one, String two, String three) {
        this.zero = zero;
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public String getZero() {
        return this.zero;
    }

    public void setZero(String value) {
        this.zero = value;
    }

    public String getOne() {
        return this.one;
    }

    public void setOne(String value) {
        this.one = value;
    }

    public String getTwo() {
        return this.two;
    }

    public void setTwo(String value) {
        this.two = value;
    }

    public String getThree() {
        return this.three;
    }

    public void setThree(String value) {
        this.three = value;
    }

}