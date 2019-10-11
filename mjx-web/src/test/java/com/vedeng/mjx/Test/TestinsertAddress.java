package com.vedeng.mjx.Test;

import com.vedeng.mjx.Db.DbInit;
import com.vedeng.mjx.basetest.Basecase;
import com.vedeng.mjx.web.controller.app.address.AppAddressController;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestinsertAddress extends Basecase {
    @Autowired
    private AppAddressController appAddressController;
    private MockMvc MockMvc;

    @Before
    public void setUp() throws Exception {
        MockMvc = MockMvcBuilders.standaloneSetup(appAddressController).build();
        this.session = new MockHttpSession();

    }

    // 正确新增收货地址
    @Test
    public void test_one() throws Exception {
        one_data();
        JSONObject obj = new JSONObject();
        obj.element("provinceCode", "16");
        obj.element("phone", "13675118317");
        obj.element("isDefAddress", "1");
        obj.element("province", "江苏");
        obj.element("city", "南京");
        obj.element("addressInfo", "民和路9号万科九都荟e区3栋1801");
        obj.element("isDefTicket", "0");
        obj.element("flag", "0");
        obj.element("districtCode", "1839");
        obj.element("cityCode", "220");
        obj.element("accountName", "童博");
        obj.element("district", "雨花台区");
        MvcResult result = MockMvc
                .perform(MockMvcRequestBuilders.post("/app/address/insertAddress").contentType(MediaType.APPLICATION_JSON_UTF8).content(obj.toString()).header("user-agent","1")
                        .session((MockHttpSession) getLoginSession("13675118317", "84588071")).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();// 返回执行请求的结果

        String Retrunvalue = result.getResponse().getContentAsString();

    }


    private void one_data() {
        String sql[] = new String[1];
        sql[0] = "delete from   V_ACCOUNT_ADDRESS where account_id = '20198' ";
        try {
            DbInit.executeBatch(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
