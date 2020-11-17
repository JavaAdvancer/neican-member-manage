package com.jiyou.nm.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.LongStream;

/**
 * 描述： 二维码/条形码原码生成器
 *       000001000001000100110000101011001110100011 0000000000
 *       时间戳                                        序列号
 * 作者： 张恒同
 * 时间： 2019-01-21   18:40
 */
public class BarCodeSnowFlakeGenerator {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 10; //序列号占用的位数
    private final static long COMPANY_BIT = 7;   //企业标识占用的位数
    private final static long YEAR_BIT = 5;      //年份标识占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_COMPANY_NUM = -1L ^ (-1L << COMPANY_BIT);
    private final static long MAX_YEAR_NUM = -1L ^ (-1L << YEAR_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long COMPANY_LEFT = SEQUENCE_BIT;
    private final static long YEAR_LEFT = SEQUENCE_BIT + COMPANY_BIT;
    private final static long TIMESTMP_LEFT = YEAR_LEFT + YEAR_BIT;

    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    private int step = 1;

    private BarCodeSnowFlakeGenerator() {

    }

    private BarCodeSnowFlakeGenerator(int step) {
        this.step = step;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public String nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return ((currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | sequence
        ) + RandomStringUtils.random(0, false, true);                             //序列号部分
    }

    public Set<String> generratorCodes(Long nums){
        Set<String> set = new HashSet<>();
        LongStream.range(0, nums).forEach(i -> set.add(nextId()));
        return set;
    }

    /**
     * 倒推条形码信息获取年份和企业
     *
     * @param code
     * @return
     * */
    public static int[] decodeBarCode(String code){
        if (StringUtils.isBlank(code)){return null;}

        int[] arr = new int[2];
        Long l = Long.parseLong(code);
        String binary = Long.toBinaryString(l);
        String company = StringUtils.substring(binary, binary.length() - 17, binary.length() - 10);
        String year = StringUtils.substring(binary, binary.length() - 22, binary.length() - 17);
        arr[0] = Integer.parseInt(company, 2);
        arr[1] = Integer.parseInt(year, 2);
        return arr;
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static class Builder{
        private int step = 1;

        public Builder setStep(int step){
            this.step = step;
            return this;
        }

        public BarCodeSnowFlakeGenerator build(){
            return new BarCodeSnowFlakeGenerator(step);
        }

    }

    public static void main(String[] args) {
        Set<String> set = new BarCodeSnowFlakeGenerator.Builder().build().generratorCodes(1L);
        for (String str : set){
            System.out.println(str);
            decodeBarCode(str);
        }
    }

}
