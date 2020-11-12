package com.example.demo.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SyncTest {
    public void syncBlock() {
        synchronized (this) {
            System.out.println("hello block");
        }
    }

    public synchronized void syncMethod() {
        System.out.println("hello method");
    }

    private static final Pattern pattern;

    static {
        String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co|lu)\\b()*";
        pattern = Pattern.compile(RE_TOP, Pattern.CASE_INSENSITIVE);
    }

    public static String [] getUrls() {
        final String url = "http://www.gintong.com/sub/subtopicApp/getUserArticleList";
        HttpResponse response = HttpUtil.createPost(url)
                .body("{\"startCreateTime\":\"\",\"endCreateTime\":\"\",\"createTime\":0,\"keyWord\":\"\",\"page\":0,\"size\":20,\"sort\":1,\"topicId\":0,\"topicIds\":[1745,3379,3600,4759,8345,8349,8351,8356,8357,8358,8359,8360],\"type\":-1}")
                .execute();
        if (200 == response.getStatus()) {
            JSONObject jsonObject = JSONObject.parseObject(response.body());
            JSONObject responseData = jsonObject.getJSONObject("responseData");
            JSONArray tbSubTopicArticles = responseData.getJSONArray("tbSubTopicArticles");
            String [] strings = new String[tbSubTopicArticles.size()];
            for (int i = 0; i < tbSubTopicArticles.size(); i++) {
                JSONObject jsonObject1 = JSONObject.parseObject(tbSubTopicArticles.get(i).toString());
                strings[i] =  jsonObject1.getString("url");
            }
           return strings;
        }
        return new String[0];
    }

    public static void main(String[] args) throws ParseException {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        int m = 2;
        int i = 0;
        while (i < 10) {
            m = integers.stream()
                    .mapToInt(Integer::intValue)
                    .reduce(m, Integer::sum);
            ++i;
        }
        System.out.println(m);
    }

    private static void shortUrl(Map<Long, String> map, final String url) {
        long hash = hash(url);
        if (hash < 0L) {
            hash = - hash;
        }
        map.put(hash,  url);
        String encode = encode(hash);
        System.out.println(encode);

        double decode = decode(encode);
        System.out.println(map.get(decode));
    }

    public static long hash(Object key) {
        long h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * @param number
     *            double类型的10进制数,该数必须大于0
     * @return string类型的64进制数
     */
    public static String encode(double number) {
        double rest = number;
        // 创建栈
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest >= 1) {
            // 进栈,
            // 也可以使用(rest - (rest / 64) * 64)作为求余算法
            stack.add(array[new Double(rest % 64).intValue()]);
            rest = rest / 64;
        }
        for (; !stack.isEmpty();) {
            // 出栈
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 支持范围是A-Z,a-z,0-9,+,-
     *
     * @param str 64进制的数字
     * @return 10进制的数字
     */
    public static double decode(String str) {
        // 倍数
        int multiple = 1;
        double result = 0;
        Character c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(str.length() - i - 1);
            result += decodeChar(c) * multiple;
            multiple = multiple * 64;
        }
        return result;
    }

    private static int decodeChar(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 打乱改字符数组的组合顺序，可以得到不同的转换结果
     */
    private static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g','h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '+', '-' };

}