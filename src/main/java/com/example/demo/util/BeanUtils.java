package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.CustomProtocol;
import org.openjdk.jol.info.ClassLayout;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteOrder;
import java.util.*;

/**
 * @author duanxiaoduan
 * @version 2019-05-16
 */
public class BeanUtils {

    public static void main(String[] args) {
        int[] arr = {6, 9, 1, 3, 1, 2, 2, 5, 6, 1, 3, 5, 9, 7, 2, 5, 6, 1, 9};
        System.out.println(getMinKthByBFPRT(arr, 10));

        Object object = new Object();
        //打印hashcode
        System.out.println(object.hashCode());
        //查看字节序
        System.out.println(ByteOrder.nativeOrder());
        final URL systemResource = ClassLoader.getSystemResource("");

        //打印当前jvm信息
        String classLayout = ClassLayout.parseInstance(BeanUtils.class).toPrintable();
        System.out.println(classLayout);

        String s = "{ \"a\": 1, \"b\": { \"c\": 2, \"d\": [3,4] } }";
        Map<String, Object> stringObjectMap = parseJson("", s);


    }

    /**
     * 【第一题】JSON格式转换
     * 在某个特定应用场景中，我们有一个从JSON获取的内容，比如：
     * m = { "a": 1, "b": { "c": 2, "d": [3,4] } }
     * 现在需要把这个层级的结构做展开，只保留一层key/value结构。对于上述输入，需要得到的结构是：
     * o = {"a": 1, "b.c": 2, "b.d": [3,4] }
     * 也就是说，原来需要通过 m["b"]["c"] 访问的值，在展开后可以通过 o["b.c"] 访问。
     * 请实现这个“层级结构展开”的代码。
     * 输入：任意JSON（或者map/dict）
     * 输出：展开后的JSON（或者map/dict）
     * @param fre
     * @param jsonString
     * @return
     */
    public static Map<String, Object> parseJson(String fre, String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Map<String, Object> objectHashMap = new HashMap<>();
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        final Iterator<Map.Entry<String, Object>> iterator = innerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            //有下一级
            if (next.getValue() instanceof JSONObject) {
                objectHashMap.putAll(parseJson(next.getKey(), next.getValue().toString()));
            } else {
                String key = "".equals(fre) ? next.getKey() : fre + "." + next.getKey();
                objectHashMap.put(key, next.getValue());
            }
        }
        return objectHashMap;
    }

    /**
     * 【第二题】数据存取
     * 我们的程序运行过程中用到了一个数组a，数组元素是一个map/dict。
     * 数组元素的“键”和“值”都是字符串类型。在不同的语言中，对应的类型是：
     * PHP的array, Java的HashMap, C++的std::map, Objective-C的NSDictionary, Swift的Dictionary, Python的dict, JavaScript的object, 等等
     * 示例：
     * a[0]["key1"]="value1"
     * a[0]["key2"]="value2"
     * a[1]["keyA"]="valueA"
     * ...
     * 为了方便保存和加载，我们使用了一个基于文本的存储结构，数组元素每行一个：
     * text="key1=value1;key2=value2\nkeyA=valueA\n..."
     *
     * 要求：请实现一个“保存”函数、一个“加载”函数。
     * text=store(a);  //把数组保存到一个文本字符串中
     * a=load(text); //把文本字符串中的内容读取为数组
     * 必须严格按照上述的“每行一个、key=value”的格式保存。
     * @param arrayMap
     * @return
     */
    public static String store(Map<String, String> [] arrayMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayMap.length; i++) {
            stringBuilder.append(arrayMap[i].toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static Map<String, String> [] load(final String str) {
        final String[] split = str.split("\n");

        return null;
    }

    public static int getMinKthByBFPRT(int[] array, int k) {
        return select(array, 0, array.length - 1, k - 1);
    }

    /**
     * select方法就是BFTRP算法核心内容
     * <p>
     * （1）如果寻找的数组开始和结束位置一样，那么直接返回
     * <p>
     * （2）medianOfMedians获取中位数，也就是我们的基准值。
     * <p>
     * （3）以这个基准值整理数组，比它小的在左边，比它大的在右边，相等的在中间。
     * <p>
     * （4）pivotRange[0]表示中间基准值的左边界，pivotRange[1]表示中间基准值的右边界，如果在这之内直接返回，否则向左边或者右边递归调用。
     *
     * @param arr
     * @param begin
     * @param end
     * @param i
     * @return
     */
    public static int select(int[] arr, int begin, int end, int i) {
        if (begin == end) {
            return arr[begin];
        }
        //中位数
        int pivot = medianOfMedians(arr, begin, end);

        int[] pivotRange = partition(arr, begin, end, pivot);
        if (i >= pivotRange[0] && i <= pivotRange[1]) {
            return arr[i];
        } else if (i < pivotRange[0]) {
            return select(arr, begin, pivotRange[0] - 1, i);
        } else {
            return select(arr, pivotRange[1] + 1, end, i);
        }
    }

    public static int medianOfMedians(int[] arr, int begin, int end) {
        int num = end - begin + 1;
        //以五个数为一组
        int offset = num % 5 == 0 ? 0 : 1;
        //中位数数组
        int[] mArr = new int[num / 5 + offset];
        //填充中位数数组
        for (int i = 0; i < mArr.length; i++) {
            int beginI = begin + i * 5;
            int endI = beginI + 4;
            mArr[i] = getMedian(arr, beginI, Math.min(end, endI));
        }
        //递归获取中位数
        return select(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedian(int[] arr, int begin, int end) {
        insertionSort(arr, begin, end);
        int sum = end + begin;
        int mid = (sum / 2) + (sum % 2);
        return arr[mid];
    }

    /**
     * 排序小数组
     */
    public static void insertionSort(int[] arr, int begin, int end) {
        for (int i = begin + 1; i != end + 1; i++) {
            for (int j = i; j != begin; j--) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 以pivotValue为中心，比他小的放左边，比他大的放右边
     * 返回一个两个元素的数组，0位置是中间数的左边，1位置是中间数的右边
     *
     * @param arr
     * @param begin
     * @param end
     * @param pivotValue
     * @return
     */
    public static int[] partition(int[] arr, int begin, int end, int pivotValue) {
        // 用于记录小于 pivotValue 的区域的右下标，初始为-1，代表不存在
        int small = begin - 1;
        //用于正在遍历的元素的下标，初始值为0
        int cur = 0;
        //用于记录大于 pivotValue 区域的左下标，初始为，代表不存在
        int big = end + 1;
        while (cur != big) {
            //当前位置的数 小于中心位置数 放左边
            if (arr[cur] < pivotValue) {
                swap(arr, ++small, cur++);
            } else if (arr[cur] < pivotValue) {
                //当前位置的数 小于中心位置数 放右边
                swap(arr, cur, --big);
            } else {
                cur++;
            }
        }
        return new int[]{small + 1, big - 1};
    }

    public static void swap(int[] arr, int m, int n) {
        arr[m] = arr[m] + arr[n];
        arr[n] = arr[m] - arr[n];
        arr[m] = arr[m] - arr[n];
    }


}
