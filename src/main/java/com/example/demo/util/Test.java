package com.example.demo.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.example.demo.constant.ColorEnum;
import com.example.demo.entity.StockInfo;
import com.example.demo.problem.Helper;
import com.example.demo.util.print.ConsoleTable;
import com.example.demo.util.print.enums.Align;
import com.example.demo.util.print.table.Cell;
import com.fasterxml.jackson.core.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.Collator;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duanxiaoduan
 * @version 2019-04-10
 */
@Slf4j
public class Test {
    private static final Random random = new SecureRandom();
    private static final int MAX_SET_SIZE = 10000;
    private static final Integer name = MAX_SET_SIZE;
    private final static Lock lock = new ReentrantLock();

    private DelayQueue delayQueue = new DelayQueue<>();

    private static BlockingQueue blockingQueue = new LinkedBlockingQueue<>(MAX_SET_SIZE);

    private static final AtomicInteger counter = new AtomicInteger(new Random(System.currentTimeMillis() % 10000000000L).nextInt());

    private static String hks = "int_hangseng,hk00700,hk03690,hk00772,hk01810,hk00005,hk02318,hk09988";
    private static String stocks = "sh000001,sz002310,sz002602,sz000510,sz002544,sh600588,sh600519,sz000651,sh600547,sz002405,sz002230,sh601975,sz002945,sh603687";
    private static String weights = "sh601398,sh601318,sh601939,sh600519,sh601288,sh601857,sh601988,sh600036,sh601628,sh600028," +
            "sz000858,sh600900,sh601328,sh601166,sz000333,sh601088,sh600000,sh601319,sh601601,sz000002," +
            "sz000651,sh600276,sh600104,sz002415,sh603288,sh601998,sh601138,sh600016,sh601668,sz000001," +
            "sh600030,sz300498,sh600585,sh601766,sh601818,sh600887,sh601888,sh600050,sh600048,sz002304," +
            "sz002714,sh601688,sz002352,sz001979,sh600009,sh601800,sh600018,sh601336,sh601211,sh600309," +
            "sh600019,sz000725,sz002594,sh601390,sh601229,sh601989,sh600837,sh601186,sz000063,sz002142," +
            "sh601360,sh601066,sz000568,sh600031,sh601169,sh601006,sh600015,sz002475,sz000166,sh601111," +
            "sh603259,sz002736,sh600999,sh601238,sz000538,sh600690,sz002024,sh601225,sz300015,sh600011," +
            "sh600547,sh601012,sz300059,sh601933,sz000338,sz000776,sh601985,sh600406,sh600346,sz000876";


    static String s = "sz300529,sz002007,sh603259,sz300122,sz002507,sz002352,sz002555,sh603890,sh600131,sz300212,z000938,sz002465,sz002230," +
            "sz002409,sz002916,sz300502,sh600745,sz002129,sz002583,sz300782,sz300676,sz300562,sz002252,sh688222,sz300238,sz000813,sh600682,sh688520";


    private static volatile boolean openFlag = false;

    public void printSize() {
        System.out.println(name);
    }

    public static void main(String[] args) throws Exception {
/*        final String filePath = "/Users/Cinderella/Downloads/WechatIMG15024.jpeg";
        Date imagTakeTime = getImageTakeTime(filePath);
        String format = DateUtil.format(imagTakeTime, DatePattern.NORM_DATETIME_MS_PATTERN);
        System.out.println(format);*/

        String[] str = {"利海", "李二", "里拉", "李成", "利群", "Li lin", "lin"};
        Arrays.stream(getSortOfChinese(str)).forEach(System.out::println);
        //calculationStockPrice(5.900, 6.936, true,20, 14000);
        //calculationStockPrice(4.410, 9.191, true,20, 2300);


        Helper.instance.run(() -> {
            do {
                //Helper.instance.run(() -> {
                    printStockInfo(getStockInfo(weights));
                    printStockInfo(getStockInfo(s));
                    printStockInfo(getStockInfo(stocks));

                //});

                try {
                    //Thread.currentThread().wait(15 * 1000L);
                    Thread.sleep(15 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (isOpen(true));
        });
        Helper.instance.run(() -> {
            do {
                //Helper.instance.run(() -> {
                    printStockInfo(getStockInfo(hks));
                //});
                try {
                    //Thread.currentThread().wait(15 * 1000L);
                    Thread.sleep(15 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (isOpen(false));

        });
        if (!isOpen(true) && !isOpen(false)) {
            Helper.instance.shutdown();
            return;
        }


       /* List<Integer> list = Lists.newArrayList(1,4,3,5,3);

        Collections.sort(list, Comparator.comparing(Integer::intValue));
        //去重
        ArrayList<Integer> newArrayList = Lists.newArrayList(Sets.newHashSet(list));
        //计算
        for (int i = 0,length = newArrayList.size(); i < length; i++) {
            double v = (i + 1.0) / length * 100;
            //设置到原数据中去l
            for (int j = 0; j < list.size(); j++) {
                if (newArrayList.get(i).equals(list.get(j))) {
                    //do set v
                    System.out.println(list.get(j) + " = " + v);
                }
                if (newArrayList.get(i) > list.get(j)) {
                    //都是有序的 内层不用再循环了
                    break;
                }
            }
        }*/

    }

    @SuppressWarnings("finally")
    public static Integer getResult() {
        Integer integer = 5;
        try {
            return integer = 6;
        } catch (Exception ex) {
            return integer = 7;
        } finally {
            return integer = 8;
        }
    }

    /**
     *  public static boolean getResult();
     *     Code:
     *        0: iconst_1  //将int型1推送至栈顶
     *        1: istore_0  //将栈顶int型数值存入第一个本地变量
     *        2: iconst_0  //将int型0推送至栈顶
     *        3: ireturn   //从当前方法返回int
     *        4: astore_0  //将栈顶引用型数值存入第一个本地变量
     *        5: iconst_1  //将int型1推送至栈顶
     *        6: istore_1   //将栈顶int型数值存入第二个本地变量
     *        7: iconst_0   //将int型0推送至栈顶
     *        8: ireturn    //从当前方法返回int
     *        9: astore_2   //将栈顶引用型数值存入第三个本地变量
     *       10: iconst_0   //将int型0推送至栈顶
     *       11: ireturn    //从当前方法返回int
     *     Exception table:
     *        from    to  target type
     *            0     2     4   Class java/lang/Exception
     *            0     2     9   any
     *            4     7     9   any
     */

    /**
     * 当前是否开市
     *
     * @return
     */
    private static synchronized boolean isOpen(boolean flag) {
        LocalTime now = LocalTime.now().withNano(0);
        LocalTime startAm = LocalTime.of(9, 30, 0);
        LocalTime endAm = LocalTime.of(11, 30, 16);
        LocalTime startPm = LocalTime.of(13, 0, 0);
        //hk
        LocalTime endPm = LocalTime.of(16, 10, 16);
        if (flag) {
            //sh sz
            endPm = LocalTime.of(15, 0, 16);
        }
        // 9:30 < time < 11:30
        boolean amFlag = startAm.isBefore(now) && now.isBefore(endAm);
        // 13:00 < time < 15:00
        boolean pmFlag = startPm.isBefore(now) && now.isBefore(endPm);
        if (amFlag || pmFlag) {
            return true;
        }
        return false;
    }

    private static void printStockInfo(final List<StockInfo> list) {

        List<Cell> header = new ArrayList<Cell>() {{
            add(new Cell(Align.LEFT, "序号"));
            add(new Cell(Align.LEFT, handleColor(ColorEnum.WHITE, "名称")));
            add(new Cell(Align.CENTER, handleColor(ColorEnum.YELLOW, "涨跌")));
            add(new Cell(Align.CENTER, handleColor(ColorEnum.YELLOW, "现价")));
            add(new Cell(Align.LEFT, "买一价"));
            add(new Cell(Align.LEFT, "买一量"));
            add(new Cell(Align.LEFT, "卖一"));
            add(new Cell(Align.LEFT, "卖一量"));
            //add(new Cell(Align.LEFT, "成交量/股"));
            add(new Cell(Align.LEFT, "成交额/万元"));
            add(new Cell(Align.LEFT, "成交均价"));
            //add(new Cell(Align.LEFT, "时间"));
        }};

        List<List<Cell>> body = new ArrayList<List<Cell>>(list.size());

        List<Cell> cellList = null;
        ColorEnum colorEnum;
        int no = 1;
        int rise = 0;
        int fall = 0;
        int flat = 0;
        for (StockInfo stockInfo : list) {
            if (null == stockInfo) {
                continue;
            }
            cellList = new ArrayList<>(list.size());
            cellList.add(new Cell(Align.LEFT, no + ""));
            if ("上证指数".equals(stockInfo.getName()) || "恒生指数".equals(stockInfo.getName())) {
                cellList.add(new Cell(Align.LEFT, handleColor(ColorEnum.YELLOW, stockInfo.getName())));
            } else {
                cellList.add(new Cell(Align.LEFT, handleColor(ColorEnum.WHITE, stockInfo.getName())));
            }

            //cellList.add(new Cell(Align.LEFT,stockInfo.getOpen()));
            double v = stockInfo.getRiseFallPercentage();
            colorEnum = v >= 0 ? ColorEnum.RED : ColorEnum.GREEN;
            if (v > 0) {
                rise++;
            } else if (v < 0) {
                fall++;
            } else {
                flat++;
            }
            cellList.add(new Cell(Align.LEFT, handleColor(colorEnum, getRound(v) + "%")));
            cellList.add(new Cell(Align.LEFT, handleColor(colorEnum, stockInfo.getCurrent() + "")));
            cellList.add(new Cell(Align.LEFT, stockInfo.getBuyOne()));
            cellList.add(new Cell(Align.LEFT, stockInfo.getBuyOneCount()));
            cellList.add(new Cell(Align.LEFT, stockInfo.getSellOne()));
            cellList.add(new Cell(Align.LEFT, stockInfo.getSellOneCount()));
            //cellList.add(new Cell(Align.LEFT, stockInfo.getVolumeCount()));
            cellList.add(new Cell(Align.LEFT, stockInfo.getVolumePrice()));
            cellList.add(new Cell(Align.LEFT, stockInfo.getAveragePrice()));
            //cellList.add(new Cell(Align.LEFT, stockInfo.getTime() + " " + stockInfo.getDate()));
            body.add(cellList);
            no++;
        }
        new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(false).joinSep("+").build().print();
        System.out.println("time = " + handleColor(ColorEnum.YELLOW, DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT)) + ", count = " + (no - 1) + ", rise = " + handleColor(ColorEnum.RED, rise + "") + ", fall = " + handleColor(ColorEnum.GREEN, fall + "") + ", flat = " + handleColor(ColorEnum.WHITE, flat + ""));
        Map<String, Integer> map = handleRiseFallCount(list);
        System.out.println(map);
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).lineSep("\n\n").build().print();
        //new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();
    }

    private static Map<String, Integer> handleRiseFallCount(final List<StockInfo> list) {
        Map<String, Integer> map = new HashMap<>(20);
        for (StockInfo stockInfo : list) {
            double riseFallPrice = stockInfo.getRiseFallPercentage();
            if (riseFallPrice < -7) {
                final String key = handleColor(ColorEnum.GREEN, "<-7%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (-7 < riseFallPrice && riseFallPrice < -5) {
                final String key = handleColor(ColorEnum.GREEN, "-7~-5%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (-5 < riseFallPrice && riseFallPrice < -3) {
                final String key = handleColor(ColorEnum.GREEN, "-5~-3%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (-3 < riseFallPrice && riseFallPrice < 0) {
                final String key = handleColor(ColorEnum.GREEN, "-3~0%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (0 < riseFallPrice && riseFallPrice < 3) {
                final String key = handleColor(ColorEnum.RED, "0~3%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (3 <= riseFallPrice && riseFallPrice < 5) {
                final String key = handleColor(ColorEnum.RED, "3~5%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (5 <= riseFallPrice && riseFallPrice < 7) {
                final String key = handleColor(ColorEnum.RED, "5~7%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }
            if (7 <= riseFallPrice) {
                final String key = handleColor(ColorEnum.RED, ">7%");
                Integer integer = map.get(key);
                if (null == integer || 0 == integer) {
                    map.put(key, 1);
                } else {
                    map.put(key, integer + 1);
                }
            }

        }
        return map;
    }

    private static String handleColor(final ColorEnum colorEnum, final String str) {
        //红色
        if (colorEnum == ColorEnum.RED) {
            return "\033[31;1m" + str + "\033[0m";
        }
        //绿色
        if (colorEnum == ColorEnum.GREEN) {
            return "\033[32;1m" + str + "\033[0m";
        }
        //黄色
        if (colorEnum == ColorEnum.YELLOW) {
            return "\033[33;1m" + str + "\033[0m";
        }
        //黑色
        if (colorEnum == ColorEnum.BLACK) {
            return "\033[30;1m" + str + "\033[0m";
        }
        //白色
        if (colorEnum == ColorEnum.WHITE) {
            return "\033[37;1m" + str + "\033[0m";
        }
        //蓝色
        if (colorEnum == ColorEnum.BLUE) {
            return "\033[34;1m" + str + "\033[0m";
        }
        //橘色
        if (colorEnum == ColorEnum.ORANGE) {
            return "\033[34;1m" + str + "\033[0m";
        }
        return str;
    }

    private static List<StockInfo> getStockInfo(final String ids) {
        final String url = "http://hq.sinajs.cn/list=" + ids;
        HttpResponse response = HttpUtil.createGet(url).execute();
        if (HttpStatus.HTTP_OK == response.getStatus()) {
            String[] split = response.body().split(";");
            List<StockInfo> list = new ArrayList<>(split.length);
            for (int i = 0, length = split.length; i < length; i++) {
                String str = split[i];
                if (str.contains("sh") || str.contains("sz")) {
                    list.add(handleStockInfo4sh(str));
                }
                if (str.contains("hk")) {
                    list.add(handleStockInfo4hk(str));
                }
                if (str.contains("int_hangseng")) {
                    list.add(handleStockInfo4hs(str));
                }
            }
            Collections.sort(list);
            return list;
        }
        return null;
    }

    private static StockInfo handleStockInfo4sh(String str) {
        StockInfo stockInfo;
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
            String[] strings = str.split(",");
            double riseFallPercentage = 0.00;
            String averagePrice = "";
            //0 不能为除数
            if (Double.parseDouble(strings[2]) > 0) {
                riseFallPercentage = (Double.parseDouble(strings[3]) - Double.parseDouble(strings[2])) / Double.parseDouble(strings[2]) * 100;
            }
            if (Double.parseDouble(strings[8]) > 0) {
                averagePrice = getRound(Double.parseDouble(strings[9]) / Double.parseDouble(strings[8]));
            }
            stockInfo = StockInfo.builder()
                    .name(strings[0])
                    .open(strings[1])
                    .prevClose(strings[2])
                    .current(strings[3])
                    .high(strings[4])
                    .low(strings[5])
                    .buyOne(strings[6])
                    .sellOne(strings[7])
                    .volumeCount(getRound(Double.parseDouble(strings[8])))
                    .volumePrice(getRound(Double.parseDouble(strings[9])))
                    .buyOneCount(strings[10])
                    .buyTwoCount(strings[12])
                    .buyTwoPrice(strings[13])
                    .buyThreeCount(strings[14])
                    .buyThreePrice(strings[15])
                    .buyFourCount(strings[16])
                    .buyFourPrice(strings[17])
                    .buyFiveCount(strings[18])
                    .buyFivePrice(strings[19])
                    .sellOneCount(strings[20])
                    .sellTwoCount(strings[22])
                    .sellTwoPrice(strings[23])
                    .sellThreeCount(strings[24])
                    .sellThreePrice(strings[25])
                    .sellFourCount(strings[26])
                    .sellFourPrice(strings[27])
                    .sellFiveCount(strings[28])
                    .sellFivePrice(strings[29])
                    .date(strings[30])
                    .time(strings[31])
                    .status(strings[32])
                    .riseFallPercentage(riseFallPercentage)
                    .averagePrice(averagePrice)
                    .build();
            return stockInfo;
        }
        return null;
    }

    private static StockInfo handleStockInfo4hk(String str) {
        StockInfo stockInfo;
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
            String[] strings = str.split(",");
            String averagePrice = "";
            double riseFallPercentage = 0.00;
            if (Double.parseDouble(strings[12]) > 0) {
                averagePrice = getRound(Double.parseDouble(strings[11]) / Double.parseDouble(strings[12]));
            }
            if (Double.parseDouble(strings[6]) > 0) {
                riseFallPercentage = (Double.parseDouble(strings[6]) - Double.parseDouble(strings[3])) / Double.parseDouble(strings[6]) * 100;
            }
            stockInfo = StockInfo.builder()
                    .name(strings[1])
                    .open(strings[2])
                    .prevClose(strings[3])
                    .current(strings[6])
                    .high(strings[4])
                    .sellOneCount("-")
                    .buyOneCount("-")
                    .low(strings[5])
                    .buyOne(strings[9])
                    .sellOne(strings[10])
                    .volumeCount(getRound(Double.parseDouble(strings[12])))
                    .volumePrice(getRound(Double.parseDouble(strings[11])))
                    .date(strings[17])
                    .time(strings[18])
                    .riseFallPercentage(riseFallPercentage)
                    .averagePrice(averagePrice)
                    .build();
            return stockInfo;
        }
        return null;
    }

    @Nullable
    private static StockInfo handleStockInfo4hs(String str) {
        StockInfo stockInfo;
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
            String[] strings = str.split(",");
            String rfp = strings[3];
            if (rfp.contains("%")) {
                rfp = rfp.replace("%", "");
            }
            stockInfo = StockInfo.builder()
                    .name(strings[0])
                    .open("0")
                    .prevClose("0")
                    .current(strings[1])
                    .riseFallPercentage(Double.parseDouble(rfp))
                    .averagePrice("0")
                    .date(LocalDate.now().toString().replace("-", "/"))
                    .time(LocalTime.now().toString().substring(0, 5))
                    .build();
            return stockInfo;
        }
        return null;
    }

    @NotNull
    @Contract(pure = true)
    private static Comparator<StockInfo> getStockInfoComparator() {
        return (o1, o2) -> -o1.getRiseFallPercentage().compareTo(o2.getRiseFallPercentage());
    }

    /**
     * 保留三位小数
     *
     * @param num
     * @return
     */
    private static String getRound(double num) {
        String format = "0";
        try {
            if (Double.NaN == num || 0 == num) {
                return "0";
            }
            format = new DecimalFormat("#,##0.000").format(num);
            if (StringUtils.isBlank(format) && format.length() < 2) {
                return "0";
            }
            if (".000".equals(format.substring(format.lastIndexOf("."), format.length()))) {
                format = format.substring(0, format.lastIndexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * @param price
     * @param cost
     * @param changeFlag
     * @param topNum
     * @param count
     */
    private static void calculationStockPrice(double price, double cost, final boolean changeFlag, final int topNum, final int count) {
        int day = 0;
        System.out.println("********************************");
        System.out.printf("%s %s %6s %8s %n", "涨停次数", "股价", "市值", "盈利");
        while (day <= topNum) {
            System.out.printf("%4s %6s %10s %10s %n", 0 == day ? "初始" : ("第" + day + "次"), getRound(price), getRound(price * count), getRound((price - cost) * count));
            if (changeFlag) {
                price = price + price * 0.1;
            } else {
                price = price - price * 0.1;
            }
            day++;
            System.out.println();
        }
    }


    public static String[] getSortOfChinese(String[] a) {
        // Collator 类是用来执行区分语言环境这里使用CHINA
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
        // JDKz自带对数组进行排序。
        Arrays.sort(a, cmp);
        return a;
    }

    public static Date getImageTakeTime(final String filePath) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new File(filePath));
            Iterator<Directory> iterator = metadata.getDirectories().iterator();
            Directory directory = null;
            while (iterator.hasNext()) {
                directory = iterator.next();
                if ("ExifSubIFDDirectory".equalsIgnoreCase(directory.getClass().getSimpleName())) {
                    return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串中 字母倒置
     *
     * @param param asdw,wq2
     * @return wdsa, qw2
     */
    public static String alphabetReverse(final String param) {
        if (null == param) {
            return "param is null";
        }
        Stack stack = new Stack();
        char[] chars = param.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, length = chars.length; i < length; i++) {
            char temp = chars[i];
            if (isEnglish(temp)) {
                //字母 入栈
                stack.push(temp);
            } else {
                //非字母 把已入栈的字母出栈
                while (!stack.empty()) {
                    stringBuilder.append(stack.pop());
                }
                //并加上当前非字母
                stringBuilder.append(temp);
            }
        }
        //确保栈里没数据了
        while (!stack.empty()) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.toString();
    }

    /**
     * 是否是字母
     *
     * @param c
     * @return
     */
    private static boolean isEnglish(final char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    public static int hash(Object key) {
        int h;
        // key.hashCode()：返回散列值也就是hashcode
        // ^ ：按位异或
        // >>>:无符号右移，忽略符号位，空位都以0补齐
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 数组倒置
     *
     * @param o
     * @return
     */
    public static Object[] arrayReverse(Object[] o) {
        for (int i = 0; i < o.length >> 1; i++) {
            Object temp = o[i];
            o[i] = o[o.length - 1 - i];
            o[o.length - 1 - i] = temp;
        }
        return o;
    }

    private static int minDeletionSize(String[] A) {
        int a = 0;
        for (int i = 0; i < A[0].length(); i++) {
            for (int j = 0; j < A.length - 1; j++) {
                if (A[a].charAt(j) > A[a + 1].charAt(j)) {
                    a++;
                }
            }
        }
        return a;
    }

    private static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        return null;
    }

    private static int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    private static int[] diStringMatch(String S) {
        int n = S.length();
        int[] a = new int[n + 1];
        int i = 0, d = n;
        for (int j = 0; j < n; j++) {
            if (S.charAt(j) == 'I') {
                a[j] = i++;
            } else {
                a[j] = d--;
            }
        }
        a[n] = i;
        return a;
    }

    private static boolean judgeCircle(String moves) {
        char[] chars = moves.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            if ('U' == chars[i]) {
                sum += -1;
            }
            if ('D' == chars[i]) {
                sum += 1;
            }
            /*if ('L' == chars[i]) {
                sum += -1;
            }
            if ('R' == chars[i]) {
                sum += 1;
            }*/
        }
        return sum == 0 ? true : false;
    }

    private static int[][] flipAndInvertImage(int[][] A) {
        int n = A.length;
        for (int[] row : A) {
            for (int i = 0; i * 2 < n; i++) {
                if (row[i] == row[n - i - 1]) {
                    row[i] = row[n - i - 1] ^= 1;
                }
            }
        }
        return A;
    }

    private static int uniqueMorseRepresentations(String[] words) {
        Set<String> unique = new HashSet<>(words.length);
        final String[] a = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        //final Character [] b = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        //Map<Character, String> map = new HashMap<>(26);
        //for (int i = 0; i < b.length; i++) {
        //    map.put(b[i], a[i]);
        //}
        String str = "";
        for (String temp : words) {
            char[] chars = temp.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                //str += map.get(chars[i]);
                str += a[chars[i] - 'a'];
            }
            unique.add(str);
            str = "";
        }
        return unique.size();
    }


    private static int[] sortArrayByParity(int[] A) {
        for (int i = 0, j = 0; j < A.length; j++) {
            if (A[j] % 2 == 0) {
                int tmp = A[i];
                A[i++] = A[j];
                A[j] = tmp;
            }
        }
        return A;
    }

    private static int[] sortedSquares(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] *= A[i];
        }
        Arrays.sort(A);
        return A;
    }

    private static int repeatedNTimes(int[] A) {
        int[] count = new int[10000];
        for (int a : A) {
            if (count[a]++ == 1) {
                return a;
            }
        }
        return -1;
    }

    private static int numUniqueEmails(String[] s) {
        if (null == s || s.length < 1) {
            return 0;
        }
        Set<String> set = new HashSet<>(s.length);
        for (int i = 0; i < s.length; i++) {
            int indexOf = s[i].indexOf('@');
            String temp = s[i].substring(0, indexOf);
            int index = temp.indexOf('+');
            if (index > 0) {
                temp = temp.substring(0, index);
            }
            while (temp.indexOf('.') > 0) {
                temp = temp.replace(".", "");
            }
            set.add(temp + s[i].substring(indexOf, s[i].length()));
        }
        return set.size();
    }

    private static String toLowerCase(String str) {
        if (null == str) {
            return null;
        }
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char) (chars[i] - 'A' + 'a');
            }
        }
        return new String(chars);

    }

    private static int getSize(long num) {
        int size = 0;
        while (num > 0) {
            num /= 10;
            size++;
        }
        return size;
    }

    private static long getZero(int n) {
        /*int result = 0;
        long k = 5;
        while (n >= k) {
            //能分解出 多少个
            result += n / k;
            //
            k = k * 5;
        }
        return result;*/
        return n == 0 ? 0 : n / 5 + getZero(n / 5);
    }

    /**
     * 递归算阶乘
     *
     * @param num
     * @return
     */
    private static long recursion(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("num must >= 0");
        }
        if (1 == num) {
            return 1;
        }
        return num * recursion(num - 1);
    }

    private String getContent(final String filePath) {
        return null;
    }

    private static String login() {
        final String url = "https://mp.weixin.qq.com/cgi-bin/bizlogin?action=startlogin&username=kefu%40gintong.com&pwd=2ff1db10ea5f8a6cfec05412428319b6&imgcode=&f=json&userlang=zh_CN&redirect_url=&token=&lang=zh_CN&ajax=1";
        HttpResponse response = HttpUtil.createPost(url)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Origin", "https://mp.weixin.qq.com")
                .header("Referer", "https://mp.weixin.qq.com/")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .execute();
        if (200 == response.getStatus()) {
            System.out.println(response.body());
            System.out.println(JSONUtil.parseObj(response.getCookies()).toString());
            return JSONUtil.parseObj(response.body()).get("redirect_url").toString();
        }
        return null;
    }

    private static String second() {
        HttpResponse response = HttpUtil.createGet("https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=getqrcode&param=4300&rd=861")
                .execute();
        System.out.println(response.body());
        return null;
    }

    /**
     * 对象里 为空字符串的属性 重新赋值为 null
     *
     * @param t
     * @return
     */
    protected static <T> T checkNullStr(T t) {
        Class<? extends Object> clazz = t.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 可访问私有变量
            field.setAccessible(true);
            // 获取属性类型
            String type = field.getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                // 将属性的首字母大写
                String methodName = field.getName().replaceFirst(field.getName().substring(0, 1),
                        field.getName().substring(0, 1).toUpperCase());
                System.out.println(methodName);
                try {
                    Method methodGet = clazz.getMethod("get" + methodName);
                    // 调用getter方法获取属性值
                    String str = (String) methodGet.invoke(t);
                    if (StringUtils.isBlank(str)) {
                        // 如果为空字符串的String类型的属性则重新赋值为null
                        field.set(t, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

}
