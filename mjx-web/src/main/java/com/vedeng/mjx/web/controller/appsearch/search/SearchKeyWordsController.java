package com.vedeng.mjx.web.controller.appsearch.search;

import com.alibaba.fastjson.JSON;
import com.vedeng.goods.api.utils.CastUtils;
import com.vedeng.goods.api.yxgParamDtos.YxgSearchParameter;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.service.feign.goods.GoodsSearchFeignApi;
import com.vedeng.mjx.service.goods.SearchKeyWordService;
import com.vedeng.mjx.web.httpclientpool.MyClientPool;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/app/search")
@RestController
public class SearchKeyWordsController {

    @Value("${solr.suggest.url}")
    protected String host;

    protected static MyClientPool myClientPool=new MyClientPool();

    @Resource
    private SearchKeyWordService searchKeyWordService;

    @Resource
    private GoodsSearchFeignApi goodsSearchFeignApi;


    @RequestMapping("/getKeyWords")
    public ResultInfo getKeyWords(){
        return ResultInfo.success(searchKeyWordService.getKeyWords());
    }

    @RequestMapping("/searchIndexName")
    public ResultInfo searchIndexName(){
        return ResultInfo.success(searchKeyWordService.searchIndexName());
    }


/*@RequestMapping("/getSuggest")
public ResultInfo getSuggest(@RequestBody YxgSearchParameter yxgSearchParameter){

    HttpClient client =myClientPool.getConnection();

    String nowkeyword = CastUtils.removeSpecialCharacters(yxgSearchParameter.getKeyword()) ;
    if (StringUtils.isEmpty(nowkeyword)){
        return ResultInfo.success(Collections.emptyList());
    }
    HttpGet httpGet = new HttpGet(host + "数"+nowkeyword.replaceAll(" ",""));
    HttpResponse response = null;
    try {
        response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream result = entity.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result));
        String suggest = "";
        String tmp = null;
        while ((tmp = bufferedReader.readLine()) != null) {
            System.out.println(tmp);
            suggest += tmp;
        }
        Map maps = (Map) JSON.parse(suggest);
        List<String> suggess=new ArrayList<>();
        if (maps.size()==1) {
            return ResultInfo.success(suggess);
        }
        Map spellcheck =(Map) maps.get("spellcheck");
        List suggestions = (List)spellcheck.get("suggestions");
        if (ListUtils.isEmpty(suggestions)) {
            return ResultInfo.success(suggess);
        }
        Map suggestionss = (Map)suggestions.get(1);
        List<String> suggestion = (List<String>) suggestionss.get("suggestion");
        suggestion.stream().forEach(s->{
            suggess.add(s.substring(1,s.length()-1));
        });

        return ResultInfo.success(suggess);
    } catch (Exception e) {
        e.printStackTrace();
        return ResultInfo.fail(e);
    }finally {
        if (client!=null) {
            myClientPool.releaseConnection(client);
        }
    }
}*/

    @RequestMapping("/getSuggest")
    public ResultInfo getNewSuggest(@RequestBody YxgSearchParameter yxgSearchParameter){


        HttpClient client =myClientPool.getConnection();
        String nowkeyword = CastUtils.removeSpecialCharacters(yxgSearchParameter.getKeyword()) ;
        if (StringUtils.isEmpty(nowkeyword)){
            return ResultInfo.success(Collections.emptyList());
        }
        HttpGet httpGet = new HttpGet(host+ "数"+nowkeyword.replaceAll(" ",""));
        HttpResponse response = null;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream result = entity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result));
            String suggest = "";
            String tmp = null;
            while ((tmp = bufferedReader.readLine()) != null) {
                System.out.println(tmp);
                suggest += tmp;
            }

            Map maps = (Map) JSON.parse(suggest);
            List<String> suggess=new ArrayList<>();
            if (maps.size()==1) {
                return ResultInfo.success(suggess);
            }
            Map spellcheck =(Map) maps.get("spellcheck");
            List suggestions = (List)spellcheck.get("suggestions");
            if (ListUtils.isEmpty(suggestions)) {
                return ResultInfo.success(suggess);
            }
            Map suggestionss = (Map)suggestions.get(1);
            List<String> suggestion = (List<String>) suggestionss.get("suggestion");
         /*   suggestion.stream().forEach(s->{
                suggess.add(s.substring(0,s.length()));
            });*/

            if (suggestion.size()>0) {
                List<String> suggestYC = goodsSearchFeignApi.getSuggestYC(suggestion);
                String regex = "[\\u4e00-\\u9fa5]+";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(nowkeyword);
                List<String> list=new ArrayList<String>();
                while(matcher.find()){
                    String e=matcher.group(0);
                    list.add(e);
                }
                for (int i=0;i<suggestYC.size();i++) {
                    String spy = suggestYC.get(i);
                    boolean regflag = list.stream().anyMatch((s) -> spy.indexOf(s) == -1);
                    if (regflag){
                        suggestYC.remove(i);
                        i--;
                    }
                }
                return ResultInfo.success(suggestYC);
            }
            return ResultInfo.success(suggess);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.fail(e);
        }finally {
            if (client!=null) {
                myClientPool.releaseConnection(client);
            }
        }
    }


}
