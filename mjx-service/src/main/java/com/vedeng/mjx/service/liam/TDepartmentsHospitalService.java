package com.vedeng.mjx.service.liam;


import com.vedeng.mjx.domain.TDepartmentsHospital;

import java.util.List;

public interface TDepartmentsHospitalService {



    public List<TDepartmentsHospital> getAllTDepartmentsHospital();


    public List<TDepartmentsHospital> selectDepartmentsHospitalbyEntity(TDepartmentsHospital tDepartmentsHospital);
}
