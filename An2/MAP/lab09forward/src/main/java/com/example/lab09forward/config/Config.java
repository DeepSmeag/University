package com.example.lab09forward.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class models Config type of entity
 */
public class Config {
    public static int friendTextSize = 14;
    public static double friendButtonWidth = 100;
    public static double friendButtonHeight = 30;
    public static double friendBoxHeight = 30;
    public static double friendBoxWidth = 450;
    public static double friendBoxSpacing = 20;

    /**
     * properties class member
     */
    private Config() {}

//    public static String CONFIG_LOCATION = Config.class.getClassLoader()
//            .getResource("config.properties").getFile();
    /**
     * userFile class member
     */
    public static String usersFile = "users.csv";
    /**
     * friendshipsFile class member
     */
    public static String friendshipsFile = "friendships.csv";

    /**
     * dbLink class member
     */
    public static String dbLink = "jdbc:postgresql://localhost:5432/SocialNetwork";
    public static String username = "postgres";
    public static String password = "password";


    public static Integer loginMenuWidth = 600;
    public static Integer loginMenuHeight = 522;

    public static Integer mainMenuWidth = 800;
    public static Integer mainMenuHeight = 600;

    public static Integer registerMenuWidth = 400;
    public static Integer registerMenuHeight = 400;

    public static Integer pagePaneWidth = 600;
    public static Integer pagePaneHeight = 600;


}
