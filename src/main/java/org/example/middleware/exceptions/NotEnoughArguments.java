package org.example.middleware.exceptions;

public class NotEnoughArguments extends RuntimeException{

    public NotEnoughArguments(String from, String to, String studyPlace) {
        super(buildMessage(from, to, studyPlace));
    }

    public NotEnoughArguments(int floor, String studyPlace) {
        super(buildMessage(floor, studyPlace));
    }

    private static String buildMessage(String from, String to, String studyPlace) {
        StringBuilder sb = new StringBuilder("Отсутствуют аргументы: ");

        if (from == null) sb.append("from ");
        if (to == null) sb.append("to ");
        if (studyPlace == null) sb.append("study_place ");

        return sb.toString().trim();
    }

    private static String buildMessage(int floor, String studyPlace) {
        StringBuilder sb = new StringBuilder("Отсутствуют аргументы: ");

        if (Integer.toString(floor) == null) sb.append("floor ");
        if (studyPlace == null) sb.append("study_place ");

        return sb.toString().trim();
    }

}
