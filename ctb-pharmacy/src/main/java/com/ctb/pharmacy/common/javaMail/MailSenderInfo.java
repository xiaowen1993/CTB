/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月27日
 * Created by hhy
 */
package com.ctb.pharmacy.common.javaMail;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @ClassName: com.ctb.pharmacy.common.javaMail.MailSenderInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月27日 下午4:56:34
 */

public class MailSenderInfo {     
    // 发送邮件的服务器的IP和端口      
    private String mailServerHost;      
    private String mailServerPort;      
    // 邮件发送者的地址      
    private String fromAddress;      
    // 邮件接收者的地址      
    private String toAddress;      
    // 登陆邮件发送服务器的用户名和密码      
    private String userName;      
    private String password;      
    // 是否需要身份验证      
    private boolean validate = false;      
    // 邮件主题      
    private String subject;      
    // 邮件的文本内容      
    private String content;      
    // 邮件附件的文件名      
    private String[] attachFileNames;     
    //发送邮件时间
    private Date sendDate;
    
    /**    
      * 获得邮件会话属性    
      */      
    public Properties getProperties(){      
      Properties p = new Properties();      
      //设置邮件服务器主机名
      p.put("mail.smtp.host", this.mailServerHost);      
//      p.put("mail.smtp.port", this.mailServerPort);  
      //发送服务器需要身份验证
      p.put("mail.smtp.auth", validate ? "true" : "false"); 
      //开启debug调试
//      p.setProperty("mail.debug", "true");
      
      try{
          MailSSLSocketFactory sf = new MailSSLSocketFactory();
          sf.setTrustAllHosts(true);
          p.put("mail.smtp.ssl.enable", "true");
          p.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
      return p;      
    }
    
    public Properties getQQProperties(){      
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.qq.com");   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
 
        // 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送
        //QQ邮箱端口有两个，可以百度。
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);    
      
        return props;
    }
    
    public String getMailServerHost() {      
      return mailServerHost;      
    }      
    public void setMailServerHost(String mailServerHost) {      
      this.mailServerHost = mailServerHost;      
    }     
    public String getMailServerPort() {      
      return mailServerPort;      
    }     
    public void setMailServerPort(String mailServerPort) {      
      this.mailServerPort = mailServerPort;      
    }     
    public boolean isValidate() {      
      return validate;      
    }     
    public void setValidate(boolean validate) {      
      this.validate = validate;      
    }     
    public String[] getAttachFileNames() {      
      return attachFileNames;      
    }     
    public void setAttachFileNames(String[] fileNames) {      
      this.attachFileNames = fileNames;      
    }     
    public String getFromAddress() {      
      return fromAddress;      
    }      
    public void setFromAddress(String fromAddress) {      
      this.fromAddress = fromAddress;      
    }     
    public String getPassword() {      
      return password;      
    }     
    public void setPassword(String password) {      
      this.password = password;      
    }     
    public String getToAddress() {      
      return toAddress;      
    }      
    public void setToAddress(String toAddress) {      
      this.toAddress = toAddress;      
    }      
    public String getUserName() {      
      return userName;      
    }     
    public void setUserName(String userName) {      
      this.userName = userName;      
    }     
    public String getSubject() {      
      return subject;      
    }     
    public void setSubject(String subject) {      
      this.subject = subject;      
    }     
    public String getContent() {      
      return content;      
    }     
    public void setContent(String textContent) {      
      this.content = textContent;      
    }
    public Date getSendDate() {
        return sendDate;
    }
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }     
    
}
