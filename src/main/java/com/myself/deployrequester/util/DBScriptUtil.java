package com.myself.deployrequester.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QueRenJie on ${date}
 */
public class DBScriptUtil {
    public List<String> analyzeSqlStatement(String sqlStatement) {
        if (StringUtils.isBlank(sqlStatement) || StringUtils.isBlank(sqlStatement.trim())) {
            return null;
        }
        //去除语句中的/*......*/字符串
        String treatedString = removeMultiRemarkedContent(sqlStatement);
        treatedString = removeRemarkedLine(treatedString);
        if (StringUtils.isBlank(treatedString)) {
            return null;
        }
        String[] seperatedStatements = treatedString.split(";");

        List<String> seperatedStatementList = new ArrayList<String>();
        if (seperatedStatements != null) {
            for (String str : seperatedStatements) {
                if (StringUtils.isNotBlank(str)) {
                    str = str.trim();
                }
                if (StringUtils.isNotBlank(str)) {
                    seperatedStatementList.add(str);
                }
            }
        }

        return seperatedStatementList;
    }

    private String removeMultiRemarkedContent(String originalString) {
        List<String> listString = new LinkedList<String>();

        boolean blnCanCopy = true;
        char[] charsOfOriginalString = originalString.toCharArray();
        int i = 0;
        for (; i < charsOfOriginalString.length - 1; i++) {
            if ('/' == charsOfOriginalString[i] && '*' == charsOfOriginalString[i + 1]) {
                blnCanCopy = false;
                i++;
                continue;
            }
            if (!blnCanCopy && '*' == charsOfOriginalString[i] && '/' == charsOfOriginalString[i + 1]) {
                blnCanCopy = true;
                i++;
                continue;
            }
            if (blnCanCopy) {
                listString.add(String.valueOf(charsOfOriginalString[i]));
            }
        }
        if (i == charsOfOriginalString.length - 1 && blnCanCopy) {
            listString.add(String.valueOf(charsOfOriginalString[i]));
        }
        StringBuffer sb = new StringBuffer();
        Iterator<String> iterator = listString.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }

        return sb.toString();
    }

    private String removeRemarkedLine(String originalString) {
        if (StringUtils.isBlank(originalString.trim())) {
            return "";
        }
        String[] strArray = originalString.split("\n");
        List<String> stringList = new ArrayList<String>();
        for (String line : strArray) {
            if (StringUtils.isBlank(line) || StringUtils.isBlank(line.trim())) {
                continue;
            }
            line = line.trim();
            if (line.startsWith("--")) {
                continue;
            }
            stringList.add(line);
        }
        StringBuffer sb = new StringBuffer();
        for (String line : stringList) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * 把字符串切分出非空的子字符串依次放到List中。
     * @param string
     * @return
     */
    public List<String> splitString(String string) {
        List<String> stringList = new ArrayList<String>();

        int startIndex = -1;
        int endIndex = 0;
        boolean isBlankChar = true;
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (isBlankChar && !(chars[i] == ' ' || chars[i] == '\t' || chars[i] == '\n' || chars[i] == '\r')){
                startIndex = i;
                isBlankChar = false;
            } else if (!isBlankChar && (chars[i] == ' ' || chars[i] == '\t' || chars[i] == '\n' || chars[i] == '\r')) {
                endIndex = i - 1;
                char[] words = new char[endIndex - startIndex + 1];
                for (int j = 0; j < words.length; j++) {
                    words[j] = chars[startIndex++];
                }
                stringList.add(String.valueOf(words));
                isBlankChar = true;
            }
        }
        if (!isBlankChar) {
            char[] words = new char[chars.length - startIndex];
            for (int i = 0; i < words.length; i++) {
                words[i] = chars[startIndex++];
            }
            stringList.add(String.valueOf(words));
        }

        return stringList;
    }

    /**
     * 获取表的名字
     * @param sql
     * @return
     */
    public String obtainTableName(String sql) {
        sql = sql.toLowerCase();
        List<String> sqlWordsList = splitString(sql);

        if (sqlWordsList != null && sqlWordsList.size() > 3) {
            if ("alter".equals(sqlWordsList.get(0)) && "table".equals(sqlWordsList.get(1))) {
                String temp = sqlWordsList.get(2);
                temp = StringUtils.replace(temp, "\"", "");
                int dotPosition = StringUtils.lastIndexOf(temp,".");
                if (dotPosition > -1) {
                    return StringUtils.substring(temp, dotPosition + 1);
                } else {
                    return temp;
                }
            }

            if ("comment".equals(sqlWordsList.get(0)) && "on".equals(sqlWordsList.get(1)) && "column".equals(sqlWordsList.get(2))) {
                String temp = sqlWordsList.get(3);
                temp = StringUtils.replace(temp, "\"", "");
                String[] strarrTemp = StringUtils.split(temp, ".");
                if (strarrTemp != null && strarrTemp.length == 2) {
                    return strarrTemp[0];
                }
                if (strarrTemp != null && strarrTemp.length == 3) {
                    return strarrTemp[1];
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DBScriptUtil dbScriptUtil = new DBScriptUtil();

        String str = "alter   table    \n  \r  \t      \"public\".pub_supplyregister add suppliertype numeric(2) ";
        String str2 = "COMMENT ON COLUMN \"pub_supplyregister\".\"suppliertype\" IS '供应商类型';";
        str = dbScriptUtil.obtainTableName(str2);
        System.out.println(str);
    }
}
