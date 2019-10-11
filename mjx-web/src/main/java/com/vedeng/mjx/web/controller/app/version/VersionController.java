package com.vedeng.mjx.web.controller.app.version;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.enumUtils.ClientEnum;
import com.vedeng.mjx.common.enumUtils.PlatFormEnum;
import com.vedeng.mjx.domain.VVersion;
import com.vedeng.mjx.service.version.VersionService;
import com.vedeng.mjx.web.controller.app.version.vo.VersionVo;
import com.vedeng.mjx.web.controller.base.CommonController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app/version")
@Api(value="app版本控制controller",tags={"app版本控制层"})
@RestController
public class VersionController extends CommonController {

    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VersionService versionService;

    @PostMapping(value = "/getVersionAndroid")
    public ResultInfo getVersionAndroid(@RequestBody VersionVo versionNo){
        log.info("获取Android版本信息，版本号:",versionNo.getVersionNo());
        //查询当前版本
        VVersion vVersion = versionService.getVersion(versionNo.getVersionNo(),PlatFormEnum.VEDENG_APP.getValue(), ClientEnum.ANDROID.getValue());
        if(vVersion == null){
            //当前版本不存在，查询最新的版本，并且强更
            vVersion = versionService.selectVersionNew(ClientEnum.ANDROID.getValue(),PlatFormEnum.VEDENG_APP.getValue());
            if(vVersion != null) {
                vVersion.setUpdateType(1);
                vVersion.setIsCpm(1);
            }
            return ResultInfo.success(vVersion);
        }else{
            //当前版本存在，取是否强更字段，返回最新版本信息
            VVersion vVersionNew = versionService.selectVersionNew(ClientEnum.ANDROID.getValue(),PlatFormEnum.VEDENG_APP.getValue());
            if(vVersionNew != null) {
                vVersionNew.setUpdateType(vVersion.getUpdateType());
            }
            return ResultInfo.success(vVersionNew);
        }

    }

    @PostMapping(value = "/getVersionIOS")
    public ResultInfo getVersionIOS(@RequestBody VersionVo versionNo){
        log.info("获取IOS版本信息，版本号:",versionNo.getVersionNo());
        //查询当前版本
        VVersion vVersion = versionService.getVersion(versionNo.getVersionNo(),PlatFormEnum.VEDENG_APP.getValue(),ClientEnum.IOS.getValue());
        if(vVersion == null){
            //当前版本不存在，查询最新的版本，并且强更
            vVersion = versionService.selectVersionNew(ClientEnum.IOS.getValue(),PlatFormEnum.VEDENG_APP.getValue());
            if(vVersion != null) {
                vVersion.setUpdateType(1);
                vVersion.setIsCpm(1);
            }
            return ResultInfo.success(vVersion);
        }else{
            //当前版本存在，取是否强更字段，返回最新版本信息
            VVersion vVersionNew = versionService.selectVersionNew(ClientEnum.IOS.getValue(),PlatFormEnum.VEDENG_APP.getValue());
            if(vVersionNew != null) {
                vVersionNew.setUpdateType(vVersion.getUpdateType());
            }
            return ResultInfo.success(vVersionNew);
        }

    }

}
