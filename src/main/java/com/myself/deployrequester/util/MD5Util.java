package com.myself.deployrequester.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QueRenJie on ${date}
 */
public class MD5Util {
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String input_charset) {
        text = StringUtils.isBlank(text) ? "" : text;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static String signSqls(List<String> sqlList, String input_charset) {
        String text = "";

        if (sqlList != null) {
            StringBuffer sb = new StringBuffer();
            for (String sql : sqlList) {
                sb.append(sql);
            }
            text = sb.toString();
            text = text == null ? "" : text;
        }

        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(text);
        String strWithoutBlank = m.replaceAll("");
        strWithoutBlank = strWithoutBlank.toLowerCase();

        return sign(strWithoutBlank, input_charset);
    }

    public static void main(String[] args) {
        String str = "aaa nnn   \t\taa\raafg\nggaAA  ";
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        String strNoBlank = m.replaceAll("");
        strNoBlank = strNoBlank.toLowerCase();
        System.out.println(strNoBlank);
        String b = MD5Util.sign(strNoBlank, "utf-8");
        System.out.println(b);
    }
}
