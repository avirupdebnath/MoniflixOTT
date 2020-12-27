package com.example.myottapp.models;

import java.io.Serializable;

public class CastAndCrewInfo implements Serializable {

    private String CastCrewName;
    private String  SkillName;
    private int  Type;

    public String getCastCrewName() {
        return CastCrewName;
    }

    public void setCastCrewName(String castCrewName) {
        CastCrewName = castCrewName;
    }

    public String getSkillName() {
        return SkillName;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }


}
