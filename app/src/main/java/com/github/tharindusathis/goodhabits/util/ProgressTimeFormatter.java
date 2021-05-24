package com.github.tharindusathis.goodhabits.util;

public final class ProgressTimeFormatter {

    private ProgressTimeFormatter() {
    }

    public static String parse(long timeInMillis) {
        if (timeInMillis <= 0) {
            return "";
        }
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;

        StringBuilder formattedTimeStr = new StringBuilder();

        if (weeks > 0) {
            formattedTimeStr.append(weeks).append("w ");
        }
        if (days % 7  > 0) {
            formattedTimeStr.append(days % 7).append("d ");
        }
        if (hours % 24 > 0) {
            formattedTimeStr.append(hours % 24).append("h ");
        }
        if (minutes % 60 > 0) {
            formattedTimeStr.append(minutes % 60).append("m ");
        }
        formattedTimeStr.append(seconds % 60).append("s");

        return formattedTimeStr.toString();
    }
}
