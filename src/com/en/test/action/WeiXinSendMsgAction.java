package com.en.test.action;

import com.en.test.utils.FileUtil;
import com.en.test.utils.SignUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by en on 2016/8/14.
 */
@Controller("weiXinSendMsgAction")
@Scope(value = "prototype")
public class WeiXinSendMsgAction extends ActionSupport {

    // 微信对接参数
    private String signature;

    // 微信对接参数
    private String timestamp;

    // 微信对接参数
    private String nonce;

    // 微信对接参数
    private String echostr;

    @Override
    public String execute() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        // 获取腾讯推送的数据流
        InputStream is = request.getInputStream();

        // 获取io流数据,如果是验证消息真实性即token，则不会有io流数据，否则一般为微信推送数据给我们，即有人关注了我们的公众号或者发送信息等推送事件
        String pushData = FileUtil.getInputStreamToString(is);
        String fromUserName="";
        if (pushData != null && (pushData.trim()).length()>0) {
            Document document = DocumentHelper.parseText(pushData);
            Element root = document.getRootElement();
            // 点击者openId
             fromUserName = root.element("FromUserName").getText();

            if (FileUtil.isEmpty(fromUserName))

                SignUtil.getUserInfo(fromUserName);
            // 接收的时间
              //int createTime = Integer.parseInt(root
              //  .element("CreateTime").getText().toString());

            FileUtil.log(pushData);
        }
        // 如果这3个参数有为空的，即不是验证消息的真实性，那么就返回空，因为是处理上面的其他时间，下面的验证无需执行
        if (signature == null || timestamp == null || nonce == null) {
            return null;
        }

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            FileUtil.log("signature : "+signature);
            FileUtil.log("timestamp : "+timestamp);
            FileUtil.log("nonce : "+nonce);
            FileUtil.log("echostr : "+echostr);

            String msg="<xml>\r\n "+
                    "<ToUserName><![CDATA[fromUserName]]></ToUserName>\r\n"+//接收方帐号（收到的OpenID）
                    "<FromUserName><![CDATA[gh_2d5ec205a1a4]]></FromUserName>\r\n "+////开发者微信号
                    "<CreateTime>createTime</CreateTime>\r\n"+//消息创建时间 （整型）
                    "<MsgType><![CDATA[text]]></MsgType>\r\n"+//文本
                    "<Content><![CDATA[你好!!!]]></Content>\r\n"+//回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
                    "</xml>";
            String ctreateTime=SignUtil.timeStamp();
            msg=msg.replace("fromUserName",fromUserName).replace("createTime",ctreateTime);
            if (!FileUtil.isEmpty(echostr)) {
                OutputStream os = response.getOutputStream();
                BufferedWriter resBr = new BufferedWriter(new OutputStreamWriter(os));
                resBr.write(msg);
                resBr.flush();
                resBr.close();
            }else {
                OutputStream os = response.getOutputStream();
                BufferedWriter resBr = new BufferedWriter(new OutputStreamWriter(os));
                resBr.write(echostr);
                resBr.flush();
                resBr.close();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(echostr);
            }
        }
        return SUCCESS;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }
}
