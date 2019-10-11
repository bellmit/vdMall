package com.vedeng.mjx.service.version;

import com.vedeng.mjx.domain.VVersion;

public interface VersionService {

    public VVersion getVersion(String versionNo,Integer platFormId,Integer client);

    VVersion selectVersionNew(Integer client,Integer platformId);

}
