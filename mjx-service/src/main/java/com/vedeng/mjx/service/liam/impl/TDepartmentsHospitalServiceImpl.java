package com.vedeng.mjx.service.liam.impl;

import com.vedeng.mjx.domain.TDepartmentsHospital;
import com.vedeng.mjx.domain.TDepartmentsHospitalExample;
import com.vedeng.mjx.mapper.TDepartmentsHospitalMapper;
import com.vedeng.mjx.service.liam.TDepartmentsHospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TDepartmentsHospitalServiceImpl implements TDepartmentsHospitalService {


    @Resource
    private TDepartmentsHospitalMapper tDepartmentsHospitalMapper;


    public List<TDepartmentsHospital> getAllTDepartmentsHospital(){

        TDepartmentsHospitalExample tDepartmentsHospitalExample = new TDepartmentsHospitalExample();
        tDepartmentsHospitalExample.createCriteria().andIsDeleteEqualTo(Boolean.valueOf("0"));
        List<TDepartmentsHospital> tDepartmentsHospitals = tDepartmentsHospitalMapper.selectByExample(tDepartmentsHospitalExample);
        return tDepartmentsHospitals;
    }

    public List<TDepartmentsHospital> selectDepartmentsHospitalbyEntity(TDepartmentsHospital tDepartmentsHospital){

        List<TDepartmentsHospital> tDepartmentsHospitals = tDepartmentsHospitalMapper.selectDepartmentsHospitalbyEntity(tDepartmentsHospital);
        return tDepartmentsHospitals;
    }
}
