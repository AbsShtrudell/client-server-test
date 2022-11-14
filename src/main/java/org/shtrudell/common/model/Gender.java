package org.shtrudell.common.model;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
    MALE("M", "Male"), FEMALE("F", "Female");

    private final String code;
    private final String text;

    private Gender(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static Gender getByCode(String genderCode) {
        for (Gender g : Gender.values()) {
            if (g.code.equals(genderCode)) {
                return g;
            }
        }
        return null;
    }

    public static List<String> listAllGenders() {
        List<String> list = new ArrayList<>();
        list.add(Gender.FEMALE.toString());
        list.add(Gender.MALE.toString());
        return list;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
