package com.example.erik.studentclient.trash;


import com.example.erik.studentclient.Course;
import com.example.erik.studentclient.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockData {

    private MockData(){}

    public static List<String> getCourseList(){
        ArrayList<String> returnee = new ArrayList<String>();
        returnee.add("all courses");
        returnee.add("Java-101");
        returnee.add("Java-102");
        returnee.add("Java-103");
        return returnee;
    }

    public static List<Student> getShortStudentList(){
        return getStudentList().stream()
                .filter(listItem -> listItem.getId() >= 80)
                .collect(Collectors.toList());
    }

    public static List<Student> getStudentList(){
        ArrayList<Student> returnee = new ArrayList<Student>();
        returnee.add(new Student(0,"Tony", "Belafonte"));
        returnee.add(new Student(1,"Ronnie", "Rose"));
        returnee.add(new Student(2,"Seth", "Lofgren"));
        returnee.add(new Student(3,"Diana", "Melua"));
        returnee.add(new Student(4,"Mark", "Krall"));
        returnee.add(new Student(5,"Ian", "Croce"));
        returnee.add(new Student(6,"Shakey", "Bowie"));
        returnee.add(new Student(7,"Otis", "Dylan"));
        returnee.add(new Student(8,"John", "Jones"));
        returnee.add(new Student(9,"Miles", "Rose"));
        returnee.add(new Student(10,"Katie", "Davis"));
        returnee.add(new Student(11,"Etta", "Ericsson"));
        returnee.add(new Student(12,"Mark", "Croce"));
        returnee.add(new Student(13,"Warren", "Iommi"));
        returnee.add(new Student(14,"Neil", "Croce"));
        returnee.add(new Student(15,"Nina", "Moore"));
        returnee.add(new Student(16,"Nora", "Jones"));
        returnee.add(new Student(17,"Etta", "Baker"));
        returnee.add(new Student(18,"Aretha", "Wylde"));
        returnee.add(new Student(19,"Udo", "Idol"));
        returnee.add(new Student(20,"Jerry", "Ericsson"));
        returnee.add(new Student(21,"Diana", "Cash"));
        returnee.add(new Student(22,"Jerry", "Jones"));
        returnee.add(new Student(23,"Otis", "Jones"));
        returnee.add(new Student(24,"Roky", "Bowie"));
        returnee.add(new Student(25,"Harry", "Satriani"));
        returnee.add(new Student(26,"Etta", "Allen"));
        returnee.add(new Student(27,"Tom", "Croce"));
        returnee.add(new Student(28,"Sam", "Moore"));
        returnee.add(new Student(29,"Eric", "Dylan"));
        returnee.add(new Student(30,"Otis", "Cooke"));
        returnee.add(new Student(31,"Mark", "Cooke"));
        returnee.add(new Student(32,"John", "Rose"));
        returnee.add(new Student(33,"Harry", "Krall"));
        returnee.add(new Student(34,"Lily", "Jones"));
        returnee.add(new Student(35,"Udo", "Cooper"));
        returnee.add(new Student(36,"Miles", "Croce"));
        returnee.add(new Student(37,"Mark", "Dirkschneider"));
        returnee.add(new Student(38,"Neil", "Iommi"));
        returnee.add(new Student(39,"Udo", "Reed"));
        returnee.add(new Student(40,"Katie", "Winehouse"));
        returnee.add(new Student(41,"Jerry", "Croce"));
        returnee.add(new Student(42,"Eric", "Melua"));
        returnee.add(new Student(43,"Nina", "Cassidy"));
        returnee.add(new Student(44,"Aretha", "James"));
        returnee.add(new Student(45,"Zakk", "Belafonte"));
        returnee.add(new Student(46,"Axl", "Baker"));
        returnee.add(new Student(47,"Sam", "Cooke"));
        returnee.add(new Student(48,"Gary", "Graves"));
        returnee.add(new Student(49,"Warren", "Baker"));
        returnee.add(new Student(50,"Nina", "MacFarlane"));
        returnee.add(new Student(51,"Jimi", "MacFarlane"));
        returnee.add(new Student(52,"Ian", "Cooper"));
        returnee.add(new Student(53,"Jim", "Allen"));
        returnee.add(new Student(54,"Nora", "Dio"));
        returnee.add(new Student(55,"Seth", "King"));
        returnee.add(new Student(56,"Otis", "Croce"));
        returnee.add(new Student(57,"Joe", "Cooper"));
        returnee.add(new Student(58,"Gary", "Lennon"));
        returnee.add(new Student(59,"Seth", "Jones"));
        returnee.add(new Student(60,"Nina", "Franklin"));
        returnee.add(new Student(61,"Eric", "Knopfler"));
        returnee.add(new Student(62,"Johnny", "Dylan"));
        returnee.add(new Student(63,"Nora", "Idol"));
        returnee.add(new Student(64,"Bob", "Jackson"));
        returnee.add(new Student(65,"David", "Dio"));
        returnee.add(new Student(66,"John", "Dio"));
        returnee.add(new Student(67,"Seth", "Zevon"));
        returnee.add(new Student(68,"Roky", "Cooper"));
        returnee.add(new Student(69,"Nora", "Idol"));
        returnee.add(new Student(70,"David", "Anderson"));
        returnee.add(new Student(71,"Nora", "Moore"));
        returnee.add(new Student(72,"Eric", "Wylde"));
        returnee.add(new Student(73,"Johnny", "Waits"));
        returnee.add(new Student(74,"Zakk", "Redding"));
        returnee.add(new Student(75,"Seth", "Lennon"));
        returnee.add(new Student(76,"John", "Moore"));
        returnee.add(new Student(77,"Nina", "Zevon"));
        returnee.add(new Student(78,"Jim", "Flack"));
        returnee.add(new Student(79,"Nils", "King"));
        returnee.add(new Student(80,"Michael", "Franklin"));
        returnee.add(new Student(81,"George", "Idol"));
        returnee.add(new Student(82,"Ronnie", "Flack"));
        returnee.add(new Student(83,"Billy", "Harrison"));
        returnee.add(new Student(84,"Roky", "Winehouse"));
        returnee.add(new Student(85,"Albert", "Rose"));
        returnee.add(new Student(86,"Warren", "Bibb"));
        returnee.add(new Student(87,"Tony", "Lofgren"));
        returnee.add(new Student(88,"Albert", "Hendrix"));
        returnee.add(new Student(89,"Etta", "James"));
        returnee.add(new Student(90,"Billy", "Bowie"));
        returnee.add(new Student(91,"Jimi", "Anderson"));
        returnee.add(new Student(92,"Zakk", "Belafonte"));
        returnee.add(new Student(93,"Michael", "Cassidy"));
        returnee.add(new Student(94,"Ronnie", "James"));
        returnee.add(new Student(95,"Nora", "Flack"));
        returnee.add(new Student(96,"Diana", "Melua"));
        returnee.add(new Student(97,"Miles", "Moore"));
        returnee.add(new Student(98,"Billy", "Melua"));
        returnee.add(new Student(99,"Diana", "MacFarlane"));
        return returnee;
    }

    public static List<String> getYearList(){
        ArrayList<String> returnee = new ArrayList<String>();
        returnee.add("-");
        returnee.add("2014");
        returnee.add("2015");
        returnee.add("2016");
        returnee.add("2017");
        return returnee;
    }

    public static List<Course> getFullCourseList(){
        ArrayList<Course> returnee = new ArrayList<Course>();
        returnee.add(new Course(0, "2014-02-22", "2014-11-14", "C-101",    60, "Programming with C"));
        returnee.add(new Course(1, "2015-02-21", "2015-10-25", "JAVA-101", 40, "Programming with Java"));
        returnee.add(new Course(2, "2015-02-21", "2015-11-17", "DB-101",   60, "Introduction to Databases"));
        returnee.add(new Course(3, "2015-02-21", "2015-05-02", "Bash-101", 15, "Introduction to Bash"));
        returnee.add(new Course(4, "2015-02-21", "2015-11-17", "C-101",    60, "Programming with C"));
        returnee.add(new Course(5, "2015-02-21", "2015-10-25", "C-102",    40, "Programming with C++"));
        returnee.add(new Course(6, "2016-02-18", "2016-10-18", "JAVA-101", 40, "Programming with Java"));
        returnee.add(new Course(7, "2016-02-18", "2016-10-18", "JAVA-102", 40, "Programming with Java"));
        returnee.add(new Course(8, "2016-02-18", "2016-11-12", "DB-101",   60, "Introduction to Databases"));
        returnee.add(new Course(9, "2016-02-18", "2016-04-27", "Bash-101", 15, "Introduction to Bash"));
        returnee.add(new Course(10, "2016-02-18", "2016-11-12", "C-101",    60, "Programming with C"));
        returnee.add(new Course(11, "2016-02-18", "2016-10-18", "C-102",    40, "Programming with C++"));
        returnee.add(new Course(12, "2016-02-18", "2016-10-18", "C-103",    40, "Programming with C#"));
        returnee.add(new Course(13, "2017-02-23", "2017-10-19", "JAVA-101", 40, "Programming with Java"));
        returnee.add(new Course(14, "2017-02-23", "2017-10-19", "JAVA-102", 40, "Programming with Java"));
        returnee.add(new Course(15, "2017-02-23", "2017-11-11", "DB-101",   60, "Introduction to Databases"));
        returnee.add(new Course(16, "2017-02-23", "2017-05-02", "Bash-101", 15, "Introduction to Bash"));
        returnee.add(new Course(17, "2017-02-23", "2017-11-11", "C-101",    60, "Programming with C"));
        returnee.add(new Course(18, "2017-02-23", "2017-10-19", "C-102",    40, "Programming with C++"));
        returnee.add(new Course(19, "2017-02-23", "2017-10-19", "C-103",    40, "Programming with C#"));
        return returnee;
    }

}
