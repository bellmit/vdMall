package com.vedeng.mjx.web.controller.msearch.departmentshospitalby;


import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.domain.TDepartmentsHospital;
import com.vedeng.mjx.service.liam.TDepartmentsHospitalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/m/departmentshospital")
@RestController
public class MDepartmentsHospitalController {

    @Resource
    private TDepartmentsHospitalService tDepartmentsHospitalService;


    @RequestMapping("/getAllTDepartmentsHospital")
    public ResultInfo getAllTDepartmentsHospital(){
        List<TDepartmentsHospital> allTDepartmentsHospital = tDepartmentsHospitalService.getAllTDepartmentsHospital();
        return ResultInfo.success(allTDepartmentsHospital);
    }

    @RequestMapping("selectDepartmentsHospitalbyEntity")
    public ResultInfo selectDepartmentsHospitalbyEntity(TDepartmentsHospital tDepartmentsHospital){
        List<TDepartmentsHospital> tDepartmentsHospitals = tDepartmentsHospitalService.selectDepartmentsHospitalbyEntity(tDepartmentsHospital);
        return ResultInfo.success(tDepartmentsHospitals);
    }

}
