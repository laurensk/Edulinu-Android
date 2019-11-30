package com.laurensk.edulinu.helpers;

import com.laurensk.edulinu.models.Teacher;

public class TeacherHelpers {

    public static String genderToText(Teacher teacher) {

        if (teacher.gender.equals("w")) {
            return "Frau";
        } else if (teacher.gender.equals("m")) {
            return "Herr";
        }

        return teacher.firstName;
    }
}
