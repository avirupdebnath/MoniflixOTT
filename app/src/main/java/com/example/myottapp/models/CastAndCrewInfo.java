package com.example.myottapp.models;

import java.io.Serializable;

public class CastAndCrewInfo implements Serializable {

    private String CastCrewName;
    private String  SkillName;
    private String  Type;

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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


}
