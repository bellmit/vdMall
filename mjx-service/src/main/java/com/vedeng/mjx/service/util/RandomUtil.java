package com.vedeng.mjx.service.util;

import com.vedeng.mjx.service.goods.impl.FloorVo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtil {

    public static Set<Integer> getRandom(Integer size){

        Set<Integer> numSet = new HashSet<>();
        while (numSet.size() < 6){
            Random r = new Random();
            int num = r.nextInt(size);
            numSet.add(num);
        }
        return numSet;
    }

    public static Set<Integer> getRandomMApp(Integer size,Integer len){

        Set<Integer> numSet = new HashSet<>();
        while (numSet.size() < len){
            Random r = new Random();
            int num = r.nextInt(size);
            numSet.add(num);
        }
        return numSet;
    }

    public static void main(String[] args) {

        System.out.println(RandomUtil.getRandomMApp(16,8));
    }

}
