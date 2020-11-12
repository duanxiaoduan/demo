package com.example.demo.problem;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author duanxiaoduan
 * @version 2019/9/6
 */
public class LeetCodeProblems {

    private final Queue<Integer> requestQueue = new LinkedList<>();

    public static String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }

    public static String defangIPaddr_(String address) {
        StringBuilder ip = new StringBuilder();
        for (char ch : address.toCharArray()) {
            ip.append(ch == '.' ? "[.]" : ch);
        }
        return ip.toString();
    }

    @Deprecated
    public static int peakIndexInMountainArray(int[] A) {
        //error
        for (int i = 0, length = A.length; i <= length - 2; i++) {
            if (A[i] < A[i + 1] && A[i + 1] > A[i + 2]) {
                return ++i;
            }
        }
        return -1;
    }

    public static int peakIndexInMountainArray_(int[] A) {
        int left = 0;
        int right = A.length - 1;
        while (left < A.length - 1 && right > 0) {
            if (A[left] < A[left + 1]) {
                left++;
            } else {
                return left;
            }
            if (A[right] < A[right - 1]) {
                right--;
            } else {
                return right;
            }
        }
        return -1;
    }

    public static String removeOuterParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        int currDepth = 0;
        for (char curr : s.toCharArray()) {
            if (curr == '(' && (currDepth += 1) > 1) {
                sb.append(curr);
            } else if (curr == ')' && (currDepth -= 1) >= 1) {
                sb.append(curr);
            }
        }
        return sb.toString();
    }

    public int ping(int timeNow) {

        requestQueue.add(timeNow);

        Integer peek = requestQueue.peek();

        while (requestQueue.peek() < (timeNow - 3000)) {
            requestQueue.poll();
        }

        return requestQueue.size();
    }

    /**
     * 如果一个数字能被它自己的各位数字整除，那么这个数字是一个自除数字，求在[left, right]双闭区间内的所有自除数字。
     *
     * @param left
     * @param right
     * @return
     */
    private static List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> list = new ArrayList<>(right - left);
        for (int i = left; i <= right; i++) {
            boolean flag = true;
            int num = i;
            //个位数字不用处理，而大于10的数 本身除以分解后的每一位数
            if (i > 9) {
                while (num > 0) {
                    // 含0的数不行 除数为0了
                    if (num % 10 == 0) {
                        flag = false;
                        break;
                    }
                    // num % 10 == 最后一位数
                    int k = num % 10;
                    if (i % k != 0) {
                        flag = false;
                        //i++;
                        break;
                    }
                    num /= 10;

                }
            }
            if (flag) {
                list.add(i);
            }
        }
        return list;
    }

    public static class ListNode {
        private int data;
        private ListNode next;

        public ListNode() {
            this(0, null);
        }

        public ListNode(int data) {
            this(data, null);
        }

        public ListNode(int data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * 获取单链表倒数第N个节点的值
     *
     * @param node 链表的头结点
     * @param n    倒数第n个结点
     * @return 倒数第n个结点
     */
    public static ListNode getLastN(ListNode node, int n) {
        //单链表不能为空 并且n大于0
        if (null == node || n < 1) {
            return null;
        }
        //指向头节点
        ListNode pointer = node;
        //倒数第n个节点与倒数第一个节点相隔 n-1 个位置
        //先走 n-1 个位置
        for (int i = 0; i < n - 1; i++) {
            //说明还有节点
            if (null != pointer.next) {
                pointer = pointer.next;
            } else {
                //说明没有那么多的节点 不满足条件 直接返回结果
                return null;
            }
        }
        //pointer 还没有走到链表的结尾，那么 pointer node 一起走
        //当 pointer 走到最后一个节点（null == pointer.next),此时的 node 就是倒数的第 n 个节点
        while (null != pointer.next) {
            pointer = pointer.next;
            node = node.next;
        }
        return node;
    }

    /**
     * 删除链表的倒数第N个节点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode curA = head;
        ListNode curB = head;
        for (int i = 0; i < n; i++) {
            curA = curA.next;
        }
        //如果走了n步之后该节点指向空节点，则该链表只有一个节点
        if (curA == null) {
            head = head.next;
            return head;
        }
        //当第一个指针的下一个节点为空时，该指针指向最后一个节点，而指针curB 走了L-n-1步，即指向该删除节点的前一个节点
        while (curA.next != null) {
            curA = curA.next;
            curB = curB.next;
        }
        //将本来指向应当删除节点地址指向应当删除节点的下一个节点的地址
        curB.next = curB.next.next;
        return head;
    }

    /**
     * 斐波那契数列
     * 0 1 1 2 3 5 8
     *
     * @param n
     * @return
     */
    public static long fibonacci(int n) {
        final int zero = 0, one = 1, two = 2, three = 3;
        if (n <= one) {
            return zero;
        }
        if (n == two || n == three) {
            return one;
        }
        return fibonacci(n - 2) + fibonacci(n - 1);
    }

    public static long fibonacci2(int n) {
        final int zero = 0, one = 1, two = 2, three = 3;
        if (n <= one) {
            return zero;
        }
        if (n == two || n == three) {
            return one;
        }
        long prePre = zero;
        long pre = one;
        long current = one;
        for (int i = three; i <= n; i++) {
            current = prePre + pre;
            prePre = pre;
            pre = current;
        }
        return current;
    }

    /**
     * Function: 判断一个数字是否为快乐数字 19 就是快乐数字  11就不是快乐数字
     * 19
     * 1*1+9*9=82
     * 8*8+2*2=68
     * 6*6+8*8=100
     * 1*1+0*0+0*0=1
     * <p>
     * 11
     * 1*1+1*1=2
     * 2*2=4
     * 4*4=16
     * 1*1+6*6=37
     * 3*3+7*7=58
     * 5*5+8*8=89
     * 8*8+9*9=145
     * 1*1+4*4+5*5=42
     * 4*4+2*2=20
     * 2*2+0*0=2
     * <p>
     * 这里结果 1*1+1*1=2 和 2*2+0*0=2 重复，所以不是快乐数字
     *
     * @param number
     * @return
     */
    public static boolean isHappy(int number) {
        HashSet<Integer> hashSet = Sets.newHashSet();
        while (number > 1) {
            int sum = 0;
            while (number > 0) {
                //取最后一位
                int i = number % 10;
                sum += i * i;
                //向前一位
                number /= 10;
            }
            //若已存在则直接返回
            if (hashSet.contains(sum)) {
                return false;
            } else {
                hashSet.add(sum);
            }
            //和在进行下一步
            number = sum;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/string-without-aaa-or-bbb/
     *
     * @param A
     * @param B
     * @return
     */
    public static String strWithout3a3b(int A, int B) {
        if (A < 1 || B < 1 || A - B > 3 || B - A > 3) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(64);
        while (A > 0 || B > 0) {
            //A比B多余两个以上，先排A
            if (A - B >= 2) {
                stringBuffer.append("a");
                A--;
                //A 还比B多余 两个以上继续排a
                if (A - B >= 2) {
                    stringBuffer.append("a");
                    A--;
                }
                if (B > 0) {
                    stringBuffer.append("b");
                    B--;
                }
            } else if (B - A >= 2) {
                //B比A多余两个以上，先排B，还有A的话在排列
                stringBuffer.append("b");
                B--;
                //B 还比A多余 两个以上继续排b
                if (B - A >= 2) {
                    stringBuffer.append("b");
                    B--;
                }
                if (A > 0) {
                    stringBuffer.append("a");
                    A--;
                }
            } else {
                //处理剩余的
                if (A > 0) {
                    //a还有
                    stringBuffer.append("a");
                    A--;
                }
                if (B > 0) {
                    //b还有
                    stringBuffer.append("b");
                    B--;
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * https://leetcode.com/problems/letter-tile-possibilities/
     *
     * @param tiles
     * @return
     */
    public static int numTilePossibilities(String tiles) {
        if (StringUtils.isBlank(tiles)) {
            return 0;
        }
        char[] chars = tiles.toCharArray();
        Map<Character, Integer> charIntegerMap = new HashMap<Character, Integer>(16);
        for (int i = 0; i < chars.length; i++) {
            Character aChar = chars[i];
            if (null == charIntegerMap.get(aChar)) {
                charIntegerMap.put(aChar, 1);
            } else {
                charIntegerMap.put(aChar, charIntegerMap.get(aChar) + 1);
            }
        }
        //移动交换
        int size = charIntegerMap.size();

        return 0;
    }

    private void helper(char[] s, int left, int right) {
        if (left > right) {
            return;
        }
        char temp = s[left];
        s[left++] = s[right];
        s[right--] = temp;
        helper(s, left, right);
    }

    public void reverseString(char[] s) {
        //自己交换 中间的不变
        helper(s, 0, s.length - 1);
    }

    public static int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            if (null == map.get(chars[i])) {
                map.put(chars[i], 1);
            } else {
                map.put(chars[i], map.get(chars[i]) + 1);
            }
        }
        for (int i = 0; i < chars.length; i++) {
            if (1 == map.get(chars[i])) {
                return i;
            }
        }
        return -1;
    }

    public static int lengthOfLastWord(String s) {
        String[] strings = s.split(" ");
        if (null == strings || strings.length < 1) {
            return 0;
        }
        return strings[strings.length - 1].length();
    }

    public static boolean buddyStrings(String A, String B) {
        char[] chars = A.toCharArray();


        return false;
    }

    /**
     * 编写两个线程，一个线程打印1~25，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。
     */
    private static volatile int threadFlag = 2;

    private static void threadNotify() {
        Helper.instance.run(threadA());
        Helper.instance.run(threadB());
    }

    private static Runnable threadA() {
        String[] strings = Helper.buildNoArray(52);
        return () -> {
            for (int i = 0; i < strings.length; i += 2) {
                while (1 == threadFlag) {
                }
                Helper.print(strings[i], strings[i + 1]);
                threadFlag = 1;
            }
        };
    }

    private static Runnable threadB() {
        String[] strings = Helper.buildCharArray(26);
        return () -> {
            for (String string : strings) {
                while (2 == threadFlag) {
                }
                Helper.print(string);
                threadFlag = 2;
            }
        };
    }


    /**
     * https://leetcode.com/problems/palindromic-substrings/
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        char[] chars = s.toCharArray();
        System.out.println("****");


        return 0;
    }

    public static String createParam(Map<String, Object> param) {

        List<String> keys = new ArrayList<String>(param.keySet());
        Collections.sort(keys);
        StringBuilder preStr = new StringBuilder();
        String key = "";
        String value = "";
        for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i);
            value = param.get(key).toString();
            if ("".equals(value) || value == null || key.equalsIgnoreCase("sign")) {
                continue;
            }
            preStr.append(key).append("=").append(value).append("&");
        }
        System.out.println(">>>>【加密前拼接的字符串为:" + preStr + "】<<<<<");
        return preStr.deleteCharAt(preStr.length() - 1).toString();
    }

    public static String createSign(Map<String, Object> param) {
        return SecureUtil.md5(createParam(param).toUpperCase());
    }

    private static void getToken() {
        final String url = "https://ciapi.qiyeos.com/apiweb/oauthToken";
        Map<String, Object> hashMap = Maps.newHashMapWithExpectedSize(6);
        hashMap.put("clientId", "3ab97e16ff00003c5a76cf01d24a3a52");
        hashMap.put("client_secret", "3ab97e16ff00003c5a76cf01136637c2");
        hashMap.put("redirectUri", "https://www.gintong.com/cross/register/getThirdUserInfo");
        hashMap.put("code", "iE837i");
        hashMap.put("timestamp", System.currentTimeMillis() / 1000);
        String sign = createSign(hashMap);
        hashMap.put("sign", sign);

        HttpResponse response = HttpUtil.createPost(url)
                .form(hashMap)
                .execute();
        System.out.println(response.body());

    }

    private static void getCode() {
        final String url = "https://ciapi.qiyeos.com/apiweb/oauthToken";
        Map<String, Object> hashMap = Maps.newHashMapWithExpectedSize(3);
        hashMap.put("clientId", "3ab97e16ff00003c5a76cf01d24a3a52");
        hashMap.put("client_secret", "3ab97e16ff00003c5a76cf01136637c2");
        hashMap.put("redirectUri", "https://www.baidu.com");
        hashMap.put("code", "2e584fb0-3bc9-40fd-8c4d-4cc94bbaa706");

        HttpResponse response = HttpUtil.createPost(url)
                .form(hashMap)
                .execute();
        System.out.println(response.body());
    }


    public static void main(String[] args) {
        double random = Math.random();
        ArrayList<Object> objects = Lists.newArrayListWithExpectedSize((int) random);
        threadNotify();
    }


}
