package com.vedeng.mjx.service.version.impl;

import com.vedeng.mjx.domain.VVersion;
import com.vedeng.mjx.domain.VVersionExample;
import com.vedeng.mjx.mapper.VVersionMapper;
import com.vedeng.mjx.service.version.VersionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Resource
    private VVersionMapper vVersionMapper;

    @Override
    public VVersion getVersion(String versionNo,Integer platFormId,Integer client) {

        VVersionExample example = new VVersionExample();
        example.createCriteria().andVersionNoLike("%"+versionNo+"%").andPlatformIdEqualTo(platFormId).andClientEqualTo(client).andIsOpenEqualTo(1).andIsDelEqualTo(0);
        List<VVersion> vVersionList = vVersionMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(vVersionList)){
            return null;
        }
        return vVersionList.get(0);
    }

    @Override
    public VVersion selectVersionNew(Integer client,Integer platformId) {
        return vVersionMapper.selectVersionNew(client,platformId);
    }
}
