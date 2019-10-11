package com.vedeng.mjx.web;


import com.vedeng.mjx.domain.TRegion;
import com.vedeng.mjx.service.address.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {
    @Autowired
    private AddressService addressService;

    @Test
    public void contextLoads() {
        TRegion tRegion=new TRegion();
        tRegion.setRegionId(1);
        addressService.queryRegionInfomation(tRegion);
    }


}
