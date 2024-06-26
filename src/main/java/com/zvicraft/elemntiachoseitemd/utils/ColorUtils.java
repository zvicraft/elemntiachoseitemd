package com.zvicraft.elemntiachoseitemd.utils;

public class ColorUtils {
    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    public ColorUtils() {
    }

    public static String color(String text) {
        return text.replace("&", "§");
    }
}



