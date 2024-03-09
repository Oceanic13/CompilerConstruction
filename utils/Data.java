package utils;

public abstract class Data {

    public static boolean asBool(boolean v) {return v;}
    public static boolean asBool(int v) {return v != 0;}
    public static boolean asBool(double v) {return v != 0.;}
    public static boolean asBool(char v) {return v != ' ';}
    public static boolean asBool(String v) {return v.length() > 0;}

    public static int asInt(boolean v) {return v? 1 : 0;}
    public static int asInt(int v) {return v;}
    public static int asInt(double v) {return (int)v;}
    public static int asInt(char v) {return v;}
    public static int asInt(String v) {return v.length();}

    public static double asDec(boolean v) {return v? 1. : 0.;}
    public static double asDec(int v) {return v;}
    public static double asDec(double v) {return v;}
    public static double asDec(char v) {return v;}
    public static double asDec(String v) {return v.length();}

    public static String asStr(boolean v) {return v? "true" : "false";}
    public static String asStr(int v) {return ""+v;}
    public static String asStr(double v) {return ""+v;}
    public static String asStr(char v) {return ""+v;}
    public static String asStr(String v) {return v;}

    public static char asChar(boolean v) {return v? '1' : '0';}
    public static char asChar(int v) {return (char)v;}
    public static char asChar(double v) {return (char)v;}
    public static char asChar(char v) {return v;}
    public static char asChar(String v) {return v.charAt(0);}
}
