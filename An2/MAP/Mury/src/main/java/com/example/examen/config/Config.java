package com.example.examen.config;

public class Config {

    public static int windowWidth = 800;
    public static int windowHeight = 600;
    public static int textSize = 12;
    public static double buttonWidth = 100;
    public static double buttonHeight = 30;
    public static double boxHeight = 30;
    public static double boxWidth = 300;
    public static double boxSpacing = 20;

    private Config() {}

    public static String dbLink = "jdbc:postgresql://localhost:5432/Examen";
    public static String username = "postgres";
    public static String password = "password";
}
