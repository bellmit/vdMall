package com.vedeng.mjx.service.goods.impl;

import com.vedeng.mjx.service.goods.SearchKeyWordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class SearchKeyWordServiceImpl implements SearchKeyWordService {
    @Override
    public List<String> searchIndexName() {
        List<String> list = new ArrayList<>();
        list.add("呼吸机");
        list.add("心电图机");
        list.add("监护仪");
        list.add("制氧机");
        list.add("血压计");
        list.add("除颤仪");
        list.add("麻醉剂");
        list.add("黑白超");
        return list;
    }

    public  String[] getKeyWords(){
        List<String> keywordlist=new ArrayList<>();
        keywordlist.add("呼吸机");
        keywordlist.add("制氧机");
        keywordlist.add("血压计");
        keywordlist.add("除颤仪");
        keywordlist.add("显微镜");
        keywordlist.add("洗胃机");
        keywordlist.add("离心机");
        keywordlist.add("彩超");
        keywordlist.add("迈瑞");
        keywordlist.add("鱼跃");
        Random r =new Random();
        /**重新赋值
         */
        String[] keywords=new String[10];
        for (int i = 0; i <10 ; i++) {
            int index = r.nextInt(keywordlist.size());
            keywords[i]=keywordlist.get(index);
            keywordlist.remove(index);
        }
        return keywords;
    }
}
