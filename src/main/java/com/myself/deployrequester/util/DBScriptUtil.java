package com.myself.deployrequester.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

    public static void main(String[] args) {
        DBScriptUtil dbScriptUtil = new DBScriptUtil();
        String originalString = "a/**bcdefg\nhij*/kl/*mn你*/S*/好\naaaaaaa\nbbbbbbb\n\nccccccc;\neeeeeee;";
        System.out.println("originalString="+originalString);
        List<String> seperatedStatementList = dbScriptUtil.analyzeSqlStatement(originalString);
        if (seperatedStatementList != null) {
            for (String s : seperatedStatementList) {
                System.out.println("statement = " + s.trim());
            }
        }
    }
}
