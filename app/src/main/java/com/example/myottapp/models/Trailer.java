package com.example.myottapp.models;

import java.io.Serializable;

public class Trailer implements Serializable {
    private int Id;
    private boolean IsTeaser;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isTeaser() {
        return IsTeaser;
    }

    public void setTeaser(boolean teaser) {
        IsTeaser = teaser;
    }
}
