package com.example.demo.util;

import cn.hutool.core.lang.copier.SrcToDestCopier;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * 八种排序算法及实现
 *
 * @author duanxiaoduan
 * @version 2019/6/16
 */

public final class SortingAlgorithm {

    public static void main(String[] args) {
        int[] array = {9, 3, 8, 6, 1, 0, 8, -4, 2};
        //bubbleSort(array);
        //quickSort(array, 0, array.length - 1);
        //insertSort(array);
        //shellSort(array);
        //selectSort(array);
        //viewArray(mergeSort(array));
        heapSort(array);
        //viewArray(array);
    }

    public static int findOne(int[] array) {
        int temp = array[0];
        int count = 0;
        for (int i = 1, length = array.length; i < length; i++) {
            if ((temp ^ array[i]) == 1) {
                count++;
            }
        }
        return temp;
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    public static int [] bubbleSort(int[] array) {
        if (isEmpty(array)) {
            return null;
        }
        final int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    //交换
                    swap(array, j, j + 1);
                }
            }
        }
        return array;
    }

    /**
     * 快速排序
     * 基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，
     * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     *
     * @param array
     * @param low
     * @param high
     */
    public static int [] quickSort(int[] array, int low, int high) {
        if (isEmpty(array) || low >= high) {
            return null;
        }
        int left = low, right = high;
        int temp = array[left];
        while (left < right) {
            //从右边开始找 找到大于temp的位置
            while (left < right && array[right] >= temp) {
                right--;
            }
            //插入到当前位置
            array[left] = array[right];
            //从左边开始找 找到小于temp的位置
            while (left < right && array[left] <= temp) {
                left++;
            }
            //插入当前位置
            array[right] = array[left];
        }
        //填补回去
        array[left] = temp;
        //左边+1 继续
        quickSort(array, left + 1, high);
        //右边-1 继续
        quickSort(array, low, left - 1);
        return array;
    }

    /**
     * 插入排序
     * 基本思想是：将数组中的所有元素依次跟前面已经排好的元素相比较，如果选择的元素比已排序的元素小，则交换，
     * 直到全部元素都比较过为止。
     *
     * @param array
     */
    public static int [] insertSort(int[] array) {
        if (isEmpty(array)) {
            return null;
        }
        for (int i = 1, length = array.length; i < length; i++) {
            int j = i - 1;
            int temp = array[i];
            //比之前的元素小 往前移
            while (j >= 0 && array[j] > temp) {
                //把当前的元素往后移动一位
                array[j + 1] = array[j];
                j--;
            }
            //插入到合适的位置
            array[j + 1] = temp;
        }
        return array;
    }

    /**
     * 希尔排序
     * 基本思想是：将待排序数组按照步长 gap 进行分组，然后将每组的元素利用直接插入排序的方法进行排序；
     * 每次再将 gap 折半减小，循环上述操作；当 gap=1 时，利用直接插入，完成排序。
     *
     * @param array
     */
    public static int [] shellSort(int[] array) {
        if (isEmpty(array)) {
            return null;
        }
        //增量gap，并逐步缩小增量
        for (int length = array.length, gap = length >> 1; gap > 0; gap = gap >> 1) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = 0; i < length; i++) {
                int j = i;
                while (j - gap >= 0 && array[j - gap] > array[j]) {
                    //交换
                    swap(array, j, j - gap);
                    j -= gap;
                }
            }
        }
        return array;

    }

    /**
     * 选择排序
     * 基本思想是：在未排序序列中找到最小（大）元素，存放到未排序序列的起始位置。
     * 在所有的完全依靠交换去移动元素的排序方法中，选择排序属于非常好的一种。
     *
     * @param array
     */
    public static int [] selectSort(int[] array) {
        if (isEmpty(array)) {
            return null;
        }
        for (int i = 0, length = array.length; i < length - 1; i++) {
            int min = i;
            //找到最小的元素位置
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            if (min != i) {
                //交换
                swap(array, i, min);
            }
        }
        return array;
    }

    /**
     * 归并排序
     * 基本思想是：将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，
     * 每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
     *
     * @param array
     */
    public static int[] mergeSort(int[] array) {
        int length = array.length;
        if (isEmpty(array) || length == 1) {
            return array;
        }
        int min = length >> 1;
        int[] leftArray = Arrays.copyOfRange(array, 0, min);
        int[] rightArray = Arrays.copyOfRange(array, min, length);
        return mergeTwoArray(mergeSort(leftArray), mergeSort(rightArray));
    }

    private static int[] mergeTwoArray(int[] leftArray, int[] rightArray) {
        int lLength = leftArray.length, rLength = rightArray.length, i = 0, j = 0, k = 0;
        int[] result = new int[lLength + rLength];
        //取两个数组 较小的值 存到新的数组
        while (i < lLength && j < rLength) {
            if (leftArray[i] <= rightArray[j]) {
                result[k++] = leftArray[i++];
            } else {
                result[k++] = rightArray[j++];
            }
        }
        //左边数组余下的 放入新数组
        while (i < lLength) {
            result[k++] = leftArray[i++];
        }
        //右边数组余下的 放入新数组
        while (j < rLength) {
            result[k++] = rightArray[j++];
        }
        return result;
    }

    /**
     * 堆排序
     * 基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。
     * 将其与末尾元素进行交换，此时末尾就为最大值。然后将剩余 n-1 个元素重新构造成一个堆，这样会得到 n 个元素的次小值。
     * 如此反复执行，便能得到一个有序序列了。
     *
     * @param array
     */
    public static int [] heapSort(int[] array) {
        if (isEmpty(array)) {
            return null;
        }
        //先构建大顶堆 每个结点的值都大于或等于其左右孩子结点的值 arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
        for (int i = (array.length >> 1) - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(array, i, array.length);
        }
        //调整堆结构 交换堆顶元素与末尾元素
        for (int i = array.length - 1; i > 0; i--) {
            //将堆顶元素与末尾元素进行交换
            swap(array, 0, i);
            //重新对堆进行调整
            adjustHeap(array, 0, i);
        }
        return array;
    }

    private static void adjustHeap(int[] array, int i, int length) {
        //先取出当前元素
        int temp = array[i];
        //从i结点的左子结点开始，也就是2i+1处开始
        for (int k = (i << 1) + 1; k < length; k = (k << 1) + 1) {
            //如果左子结点小于右子结点，k指向右子结点
            if (k + 1 < length && array[k] < array[k + 1]) {
                k++;
            }
            //如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
            if (array[k] > temp) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        //将temp值放到最终的位置
        array[i] = temp;
    }


    /**
     * 打印数组
     *
     * @param array
     */
    private static void viewArray(final int[] array) {
        if (isEmpty(array)) {
            return;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 判断数组是否是有效数组
     *
     * @param array
     * @return
     */
    private static boolean isEmpty(final int[] array) {
        if (null == array || array.length <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 数组交换
     *
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array, int i, int j) {
        LinkedList queue = new LinkedList();
        List<Integer> result = new ArrayList<Integer>();

       /* //临时变量交换
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;*/
        //运算交换
        array[i] = array[i] + array[j];
        array[j] = array[i] - array[j];
        array[i] = array[i] - array[j];
    }


}
