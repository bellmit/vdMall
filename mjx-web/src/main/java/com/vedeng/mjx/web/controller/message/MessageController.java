package com.vedeng.mjx.web.controller.message;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vedeng.mjx.common.PushMessageVo.SearchMessagePageInfo;
import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.common.exception.VedengErrorCode;
import com.vedeng.mjx.common.orderVo.OrderListData;
import com.vedeng.mjx.common.page.PageModel;
import com.vedeng.mjx.domain.MessageCount;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.domain.Vmessage;
import com.vedeng.mjx.service.message.MessageService;
import com.vedeng.mjx.service.util.ShiroSessionUtil;
import com.vedeng.mjx.web.controller.base.BaseController;
import com.vedeng.mjx.web.controller.message.vo.MessageVo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RequestMapping("/m")
@RestController
@Api(value = "M站内部信息controller", tags = {"信息控制层"})
public class MessageController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;

    @PostMapping("/message/index")
    public ResultInfo messageCount() {
        List<MessageCount> result = messageService.selectCount(getAccountId());
        return ResultInfo.success(result);
    }

    /**
     * 获取所有类型第一条消息以及没有读取的消息数量
     *
     * @return
     */
    @PostMapping("/message/getMessage")
    public ResultInfo index(@RequestBody MessageVo messageVo) {

        PageHelper.startPage(messageVo.getPageNo(), messageVo.getPageSize());
        //查询该用户的所有未读消息以及数量
        List<Vmessage> listNoRead = messageService.getNoReadMessage(getAccountId(),messageVo.getMessageType());
        PageInfo<Vmessage> pageInfo = new PageInfo<Vmessage>(listNoRead);
        return ResultInfo.success(VedengErrorCode.SUCCESS_CODE,pageInfo);

    }

    /**
     * 将消息全部设为已读
     * @return
     */
    @PostMapping("/message/readMessage")
    public ResultInfo readMessage(@RequestBody MessageVo messageVo){
        if(ObjectUtils.isEmpty(messageVo.getMessageType())){
            return ResultInfo.fail("1001","消息类型不能为空");
        }
        return ResultInfo.success(messageService.updateMessage(getAccountId(),messageVo.getMessageType()));
    }

    /**
     * 查询所有未读消息
     * @return
     */
    @PostMapping(value = "/message/getNoReadCount")
    public ResultInfo getNoReadCount(){
        return ResultInfo.success(messageService.selectNoReadCount(getAccountId()));
    }

    /**
     * 获取一条消息
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/message/getMessageOne")
    public ResultInfo getOneMessage(@RequestBody Vmessage vmessage, HttpServletRequest request, HttpServletResponse response) {
        try {
            Vmessage vm = messageService.getOneMessage(vmessage.getMessageId());
            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE,vm);
        } catch (Exception e) {
            log.error("获取获取一条消息服务接口/message/getMessageOne：发生异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        }
    }

    /**
     * 插入一条消息
     *
     * @param vmessage
     * @return
     */
    @PostMapping("/message/insertMessage")
    public ResultInfo insertOneMessage(@RequestBody Vmessage vmessage) {

        vmessage.setUserId(getAccountId());
        Integer result = messageService.insertVmessage(vmessage);
        if(result == 0){
            return ResultInfo.fail("fail","消息存储失败");
        }
        return ResultInfo.success("消息存储成功");
    }


    /**
     * 修改一条信息内容
     *
     * @param request
     * @param response
     * @param vmessage
     * @return
     */
    @PostMapping("/message/updateMessage")
    @ResponseBody
    public ResultInfo updateMessage(HttpServletRequest request, HttpServletResponse response, @RequestBody Vmessage vmessage) {
        try {
            TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
            vmessage.setUpdater(jx.getAccountId());
            messageService.updateVmessage(vmessage);
            return ResultInfo.success("成功修改一条消息");
        } catch (Exception e) {
            log.error(" 修改消息服务接口/message/updateMessage：发生异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        }
    }


    /**
     * 读取某条消息或者某个类型信息，修改已读状态和读取时间
     *
     * @return
     */
    @PostMapping("/message/updateMessageRead")
    @ResponseBody
    public ResultInfo updateMessage(@RequestBody Vmessage vmessage) {
        try {
            String reStr = messageService.updateVmessageRead(vmessage);
            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE,reStr);
        } catch (Exception e) {
            log.error("读取消息，修改已读状态和读取时间服务接口/message/updateMessage：发生异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);
        }
    }


    /**
     * 根据类型分页查询消息
     *
     * @return
     */
    @PostMapping("/message/getPageMessage")
    @ResponseBody
    public ResultInfo getPageMessage(@RequestBody SearchMessagePageInfo messagePageInfo) {
        try {
            // 根据shiro的session获取用户信息
            TopAccountJx jx = ShiroSessionUtil.getSessionTopAccountJx();
            //消息分页查询
            PageHelper.startPage(messagePageInfo.getPageNo(), messagePageInfo.getPageSize());
            PageModel page = new PageModel();
            page.setPageNo(messagePageInfo.getPageNo());
            page.setPageSize(messagePageInfo.getPageSize());
            List<Vmessage> listMessage = messageService.getPageMessage(jx.getAccountId(), messagePageInfo.getMessageType(), page);

            //返回的是一个PageInfo,包含了分页的所有信息
            PageInfo<Vmessage> pageInfo = new PageInfo<Vmessage>(listMessage);

            return ResultInfo.success(VedengErrorCode.SUCCESS_CODE,pageInfo);
        } catch (Exception e) {
            log.error("分页获取所有消息服务接口/message/getPageMessage：发生异常", e);
            return ResultInfo.fail(VedengErrorCode.FAIL_CODE);

        }

    }




}
