package com.example.demo.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author duanxiaoduan
 * @version 2019-08-06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class StockInfo implements Serializable, Comparable<StockInfo> {

    /**
     * 股票名称
     */
    private String name;

    /**
     * 今开盘价
     */
    private String open;

    /**
     * 上个交易日收盘价
     */
    private String prevClose;

    /**
     * 当前价
     */
    private String current;

    /**
     * 今日最高价
     */
    private String high;

    /**
     * 今日最低价
     */
    private String low;

    /**
     * 竞买价，即买一价
     */
    private String buyOne;

    /**
     * 竞卖价，即卖一价
     */
    private String sellOne;

    /**
     * 成交量/股
     */
    private String volumeCount;

    /**
     * 成交额/万元
     */
    private String volumePrice;

    /**
     * 买一量/股
     */
    private String buyOneCount;

    /**
     * 买二量/股
     */
    private String buyTwoCount;

    /**
     * 买二价
     */
    private String buyTwoPrice;

    /**
     * 买三量/股
     */
    private String buyThreeCount;

    /**
     * 买三价
     */
    private String buyThreePrice;

    /**
     * 买四量/股
     */
    private String buyFourCount;

    /**
     * 买四价
     */
    private String buyFourPrice;

    /**
     * 买五量/股
     */
    private String buyFiveCount;

    /**
     * 买五价
     */
    private String buyFivePrice;

    /**
     * 卖一量/股
     */
    private String sellOneCount;

    /**
     * 卖二量/股
     */
    private String sellTwoCount;

    /**
     * 卖二价
     */
    private String sellTwoPrice;

    /**
     * 卖三量/股
     */
    private String sellThreeCount;

    /**
     * 卖三价
     */
    private String sellThreePrice;

    /**
     * 卖四量/股
     */
    private String sellFourCount;

    /**
     * 卖四价
     */
    private String sellFourPrice;

    /**
     * 卖五量/股
     */
    private String sellFiveCount;

    /**
     * 卖五价
     */
    private String sellFivePrice;

    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 状态
     */
    private String status;

    /**
     * 涨跌值 现价-上个交易日收盘价
     */
    @Builder.Default
    private Double riseFallPercentage = 0.00;

    /**
     * 成交均价
     */
    private String averagePrice;

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param stockInfo the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(StockInfo stockInfo) {
        return - this.getRiseFallPercentage().compareTo(stockInfo.getRiseFallPercentage());
    }
}
