package com.vedeng.mjx.basetest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vedeng.mjx.web.WebApplication;
import com.vedeng.mjx.web.controller.app.login.AppLoginController;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Spring注入
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {WebApplication.class})
@Configuration

@WebAppConfiguration
public class Basecase extends TestCase {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    @Autowired
    private AppLoginController appLoginController;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SecurityManager securityManager;
    @Autowired
    private SessionDAO sessionDAO;
    private Subject subject;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;
    @Autowired
    private WebSubject webSubject;

    private void login(String username, String password) {
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        subject.login(token);
        ThreadContext.bind(subject);
    }


    // 登录方法获取session
    protected HttpSession getLoginSession(String mobile, String password) throws Exception {

        mvc = MockMvcBuilders.standaloneSetup(appLoginController).build();
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        JSONObject obj = new JSONObject();
        obj.element("mobile", mobile);
        obj.element("password", password);
        obj.element("isCheckImgCode", "0");
        obj.element("imgCode", "");

        MvcResult result = this.mvc.perform((MockMvcRequestBuilders.post("/app/signInOrUp/signInWithPwd").content(obj.toString()).header("user-agent", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))).andExpect(status().isOk()).andReturn();
        return result.getRequest().getSession();
    }


    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    protected <T> T mock(Class<T> classes) {
        return EasyMock.createMock(classes);
    }

    protected void replay(Object... mocks) {
        EasyMock.replay(mocks);
    }

    protected void verify(Object... mocks) {
        EasyMock.verify(mocks);
    }

    protected <T> IExpectationSetters<T> expect(T value) {
        return EasyMock.expect(value);
    }


    // 将指定时间转换为时间戳

    public static String Timetostamp(String user_time) throws ParseException {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        d = sdf.parse(user_time);
        long l = d.getTime();

        String str = String.valueOf(l);

        re_time = str.substring(0, 10);

        return re_time;
    }

    // 指定时间戳转换为时间
    private static String Stamptotime(int Stamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(Stamp * 1000L));

        return date;

    }

    // 当前时间转换为时间戳
    public static String GetTimeStamp() throws ParseException {

        Date now = new Date();
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now);

        Date d;
        d = sdf.parse(time);

        long l = d.getTime();
        String str = String.valueOf(l);
        re_time = str.substring(0, 10);

        return re_time;
    }

    // 获取当前时间
    public static String Getnow() throws ParseException {

        Date now = new Date();
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now);
        return time;
    }

    public static String getTimeMs() {
        Calendar Cld = Calendar.getInstance();
        int YY = Cld.get(Calendar.YEAR);
        int MM = Cld.get(Calendar.MONTH) + 1;
        int DD = Cld.get(Calendar.DATE);
        int HH = Cld.get(Calendar.HOUR_OF_DAY);
        int mm = Cld.get(Calendar.MINUTE);
        int SS = Cld.get(Calendar.SECOND);
        int MI = Cld.get(Calendar.MILLISECOND);
        // 由整型而来,因此格式不加0,如 2016/5/5-1:1:32:694
        System.out.println(YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI);

        // func2
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        // 2016/05/05-01:01:34:364
        System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));

        // func3 2016/05/05-01:01:34:364
        System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
        return "";
    }

    // 时间处理方法，获取当前时间加减天数或小时的时间戳，type 0为处理小时，1为天
    public static String getDate(int date, int type) throws ParseException {
        String re_time = null;
        Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        if (type == 0) {
            now.set(Calendar.HOUR, now.get(Calendar.HOUR) + date);
        }
        if (type == 1) {
            now.set(Calendar.DATE, now.get(Calendar.DATE) + date);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date e;
        String user_time = sdf.format(now.getTime());

        e = sdf.parse(user_time);
        long l = e.getTime();

        String str = String.valueOf(l);

        re_time = str.substring(0, 10);

        return re_time;
    }

    // 时间处理方法，获取当前时间加减天数或小时的时间戳，type 0为处理小时，1为天,2为年
    public static String gettime(int date, int type) throws ParseException {
        String re_time = null;
        Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        if (type == 0) {
            now.set(Calendar.HOUR, now.get(Calendar.HOUR) + date);
        }
        if (type == 1) {
            now.set(Calendar.DATE, now.get(Calendar.DATE) + date);
        }
        if (type == 2) {
            now.set(Calendar.DATE, now.get(Calendar.YEAR) + date);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date e;
        String user_time = sdf.format(now.getTime());


        return user_time;
    }

    // 生成随机数
    // 按位数生成随机数
    public static int random(int num) {

        int ans = 0;
        while (Math.log10(ans) + 1 < num)
            ans = (int) (Math.random() * Math.pow(10, num));
        return ans;

    }

    // 计算2个时间戳相差是否在多少毫秒之内，返回boolean
    public static boolean Istimedifference(String time1, String time2, int seconds) {
        int a = Integer.parseInt(time1);
        int b = Integer.parseInt(time2);
        long interval = (b - a);
        if (interval <= seconds) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合相等
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

    public static List<Map> rsToList(ResultSet rs) throws SQLException {
        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();

        int columnCount = md.getColumnCount();

        while (rs.next()) {

            Map rowData = new HashMap();

            for (int i = 1; i <= columnCount; i++) {

                rowData.put(md.getColumnName(i), rs.getObject(i));

            }

            list.add(rowData);

        }

        return list;
    }

    public static Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {

                if (f.getName().endsWith(key)) {
                    System.out.println("单个对象的某个键的值==反射==" + f.get(obj));
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }

    private String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    protected Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {

            return null;
        }
    }

    public Object[] getFiledValues(Object o) {
        String[] fieldNames = this.getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        Basecase Basecase = new Basecase();

        System.out.println(Basecase.getLoginSession("13675118317", "84588071"));
    }

}