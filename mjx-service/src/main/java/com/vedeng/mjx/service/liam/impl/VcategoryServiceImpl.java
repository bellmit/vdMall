package com.vedeng.mjx.service.liam.impl;

import com.github.pagehelper.PageInfo;
import com.vedeng.goods.api.dto.CategoryDTO;
import com.vedeng.goods.api.vo.SearchSkuResult;
import com.vedeng.mjx.common.constant.RedisKeyConstant;
import com.vedeng.mjx.common.searchVo.FilterCondition;
import com.vedeng.mjx.common.searchVo.SearchResult;
import com.vedeng.mjx.domain.SearchVoCategory;
import com.vedeng.mjx.domain.VCategory;
import com.vedeng.mjx.domain.VCategoryExample;
import com.vedeng.mjx.domain.VCategoryKey;
import com.vedeng.mjx.domain.ext.VCategoryDto;
import com.vedeng.mjx.service.liam.VCategoryService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VcategoryServiceImpl implements VCategoryService {

    @Resource
    private com.vedeng.mjx.mapper.VCategoryMapper VCategoryMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public List<VCategory> queryCategoryOneList() {
        VCategoryExample VCategoryExample=new VCategoryExample();
        VCategoryExample.createCriteria().andParentIdEqualTo(0);
        List<VCategory> vCatrgories = VCategoryMapper.selectByExample(VCategoryExample);
        return vCatrgories;
    }

    @Override
    public VCategory queryCategoryById(Integer id) {
        VCategoryKey vCategoryKey = new VCategoryKey();
        vCategoryKey.setvCategoryId(id);
        return VCategoryMapper.selectByPrimaryKey(vCategoryKey);
    }


    public List<VCategoryDto> queryCategoryAllList(Integer cat1Id) {
        List<VCategoryDto> oneList = new ArrayList<>();
        List<VCategoryDto> vCatrgories1 = VCategoryMapper.queryCategorybyparentId(0,null);
        for (int i=0;i<vCatrgories1.size();i++)
        {
            if (cat1Id==null||vCatrgories1.get(i).getvCategoryId().equals(cat1Id))
            {
                VCategoryDto vCategoryDtov1 = vCatrgories1.get(i);
                List<VCategoryDto> vCatrgories2 = VCategoryMapper.queryCategorybyparentId(vCategoryDtov1.getvCategoryId(),null);
                List<VCategoryDto> twoList = new ArrayList<>();
                for(int j=0;j<vCatrgories2.size();j++)
                {
                    VCategoryDto vCategoryDtov2=vCatrgories2.get(j);
                    List<VCategoryDto> vCatrgories3 = VCategoryMapper.queryCategorybyparentId(vCategoryDtov2.getvCategoryId(),1);
                    List<VCategoryDto> threeList = new ArrayList<>();
                    for(int k=0;k<vCatrgories3.size();k++) {
                        VCategoryDto vCategoryDtov3 = vCatrgories3.get(k);
                        threeList.add(vCategoryDtov3);
                    }
                    if (threeList.size()==0){
                        continue;
                    }
                    vCategoryDtov2.setChildCategoryList(threeList);
                    twoList.add(vCategoryDtov2);
                }
                if (twoList.size()==0){
                    continue;
                }
                vCategoryDtov1.setChildCategoryList(twoList);
                oneList.add(vCategoryDtov1);
            }
            }
        return oneList;
    }



    @Override
    public List<VCategory> queryCategoryByParentId(Integer parentid) {
        VCategoryExample VCategoryExample=new VCategoryExample();
        VCategoryExample.createCriteria().andParentIdEqualTo(parentid);
        List<VCategory> vCatrgories = VCategoryMapper.selectByExample(VCategoryExample);
        return vCatrgories;
    }

    @Override
    public List<CategoryDTO> queryLevel3Category(List<CategoryDTO> categoryDTOS) {
        if (categoryDTOS==null)
        {
            return null;
        }
        List<CategoryDTO> resultlist = new ArrayList<CategoryDTO>();
        for (CategoryDTO categoryDTO:categoryDTOS) {
            RBucket<Object> keyObject  = redissonClient.getBucket(RedisKeyConstant.mjxcat3key+categoryDTO.getCategoryId());
            if (keyObject.get()==null) {
                CategoryDTO vcategoryDTO = VCategoryMapper.queryById(categoryDTO.getCategoryId());
                if (vcategoryDTO==null) {
                    continue;
                }
                keyObject.set(vcategoryDTO,1, TimeUnit.DAYS);
                resultlist.add(vcategoryDTO);
            }else{
                resultlist.add((CategoryDTO) keyObject.get());
            }
        }
        return resultlist;
    }

    @Override
    public List<CategoryDTO> queryLevel3CategoryI(List<Integer> categoryDTOS) {

        List<CategoryDTO> resultlist = new ArrayList<CategoryDTO>();
        for (Integer categoryId:categoryDTOS) {
            RBucket<Object> keyObject  = redissonClient.getBucket(RedisKeyConstant.mjxcat3key+categoryId);
            if (keyObject.get()==null) {
                CategoryDTO vcategoryDTO = VCategoryMapper.queryById(categoryId);
                keyObject.set(vcategoryDTO,1, TimeUnit.DAYS);
                resultlist.add(vcategoryDTO);
            }else{
                resultlist.add((CategoryDTO) keyObject.get());
            }
        }

        return resultlist;
    }



    public SearchResult getSearchResult(Integer currentPage, Integer pageSize, Integer navigatePages, SearchSkuResult search){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(search.getSkuList());
        pageInfo.setPageNum(currentPage);
        pageInfo.setPageSize(pageSize);
        int total = search.getPage().getTotal().intValue();
        pageInfo.setSize(total);
        pageInfo.setTotal(total);
        int pages = total / pageSize;
        if (total%pageSize != 0)
        {
            pages++;
        }
        pageInfo.setPages(pages);
        if(currentPage>1){
            pageInfo.setPrePage(currentPage-1);
            pageInfo.setHasPreviousPage(true);
        }else{
            pageInfo.setHasNextPage(false);
        }

        if(pages>currentPage)
        {
            pageInfo.setHasNextPage(true);
            pageInfo.setNextPage(currentPage+1);
        }else{
            pageInfo.setHasPreviousPage(false);
        }
        pageInfo.setNavigatePages(navigatePages);
        if (currentPage==1) {
            pageInfo.setIsFirstPage(true);
        }else{
            pageInfo.setIsFirstPage(false);
        }
        if(currentPage==pages) {
            pageInfo.setIsLastPage(true);
        }else{
            pageInfo.setIsLastPage(false);
        }

        int[] navigatepageNums=null;
        if(pages <= navigatePages){
            navigatepageNums =new int[(int) pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        }else {
            navigatepageNums = new int[navigatePages];
            int startNum = currentPage - navigatePages >> 1;
            int endNum = currentPage + navigatePages >>1;
            if(startNum < 1 ){
                startNum = 1;
                //最前端navigatepages页数
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i]=startNum++;
                }
            }else if(endNum >pages){
                endNum=pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >=0 ; i--) {
                    navigatepageNums[i]=endNum--;
                }
            }else{
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i]=startNum++;
                }
            }
        }
        pageInfo.setNavigatepageNums(navigatepageNums);


        List<CategoryDTO> categoryDTOS = queryLevel3Category(search.getCategoryList());
        search.setCategoryList(categoryDTOS);
        FilterCondition filterCondition = new FilterCondition();
        filterCondition.searchSkuResultoFilterCondition(search);
        SearchResult searchResult=new SearchResult(pageInfo, filterCondition);
        return searchResult;
    }
}
