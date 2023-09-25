package www.kb.common;

import java.util.regex.Pattern;

public class Validator {
    //선택값일 때 데이터 검사
    //필수값일 때 데이터 검사
    public static boolean isValidated(String data, String regexp, boolean isEssential) {
        boolean isValidated = true;
        // 필수값
        if (isEssential) {
            if (data == null || data.equals("") || !Pattern.matches(regexp, data)) {
                isValidated = false;
            }
        } else {  //선택값
            if (data != null && !data.equals("") && !Pattern.matches(regexp, data)) { //데이터 입력시
                isValidated = false;
            }
        }
        return isValidated;
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty(); //isEmpty(): 빈값이냐
    }

    public static String changeToString(String str) {
        if (str != null) {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
            str = str.replaceAll("'", "&#039;");
            str = str.replaceAll("\"", "&quot;");
            str = str.replaceAll("<br>", "\r\n;");
        }
        return str;
    }

    public static String changetoHtml(String str) {
        if (str != null) {
            str = str.replaceAll("&amp;", "&");
            str = str.replaceAll("&lt;", "<");
            str = str.replaceAll("&gt;", ">");
            str = str.replaceAll("&#039;", "'");
            str = str.replaceAll("&quot;", "\"");
            str = str.replaceAll("\r\n;", "<br>");
        }
        return str;
    }
}

