/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月27日
 * Created by hhy
 */
package com.ctb.pharmacy.common.javaMail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctb.pharmacy.rest.entity.RecordEntity;


/**
 * @ClassName: com.ctb.pharmacy.utils.SimpleMailSender
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月27日 下午4:51:29
 */

public class SimpleMailSender {
    
    private static Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);
    
    /**    
  * 以文本格式发送邮件    
  * @param mailInfo 待发送的邮件的信息    
  */      
    public boolean sendTextMail(MailSenderInfo mailInfo) {      
      // 判断是否需要身份认证      
      MyAuthenticator authenticator = null; 
      Properties pro = mailInfo.getProperties();     
      if (mailInfo.isValidate()) {      
      // 如果需要身份认证，则创建一个密码验证器      
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());      
      }     
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session      
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);      
      try {      
          // 根据session创建一个邮件消息      
          Message mailMessage = new MimeMessage(sendMailSession);      
          // 创建邮件发送者地址      
          Address from = new InternetAddress(mailInfo.getFromAddress());      
          // 设置邮件消息的发送者      
          mailMessage.setFrom(from);      
          // 创建邮件的接收者地址，并设置到邮件消息中      
          Address to = new InternetAddress(mailInfo.getToAddress());      
          mailMessage.setRecipient(Message.RecipientType.TO,to);      
          // 设置邮件消息的主题      
          mailMessage.setSubject(mailInfo.getSubject());      
          // 设置邮件消息发送的时间      
          mailMessage.setSentDate(mailInfo.getSendDate());      
          // 设置邮件消息的主要内容      
          String mailContent = mailInfo.getContent();      
          mailMessage.setText(mailContent);      
          // 发送邮件      
          Transport.send(mailMessage);     
          return true;      
      } catch (MessagingException ex) {      
          ex.printStackTrace();   
          logger.error("向"+mailInfo.getToAddress()+"时报错："+ex.getMessage());
      }    
      return false;      
    }      
         
    /**    
      * 以HTML格式发送邮件    
      * @param mailInfo 待发送的邮件信息    
      */      
    public boolean sendHtmlMail(MailSenderInfo mailInfo){      
      // 判断是否需要身份认证      
      MyAuthenticator authenticator = null;     
      Properties pro = mailInfo.getProperties();     
      //如果需要身份认证，则创建一个密码验证器       
      if (mailInfo.isValidate()) {      
        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());     
      }      
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session      
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);      
      try {      
          // 根据session创建一个邮件消息      
          Message mailMessage = new MimeMessage(sendMailSession);      
          // 创建邮件发送者地址      
          Address from = new InternetAddress(mailInfo.getFromAddress());      
          // 设置邮件消息的发送者      
          mailMessage.setFrom(from);      
          // 创建邮件的接收者地址，并设置到邮件消息中      
          Address to = new InternetAddress(mailInfo.getToAddress());      
          // Message.RecipientType.TO属性表示接收者的类型为TO      
          mailMessage.setRecipient(Message.RecipientType.TO,to);      
          // 设置邮件消息的主题      
          mailMessage.setSubject(mailInfo.getSubject());      
          // 设置邮件消息发送的时间      
          mailMessage.setSentDate(mailInfo.getSendDate());      
          // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象      
          Multipart mainPart = new MimeMultipart();      
          // 创建一个包含HTML内容的MimeBodyPart      
          BodyPart html = new MimeBodyPart();      
          // 设置HTML内容      
          html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");      
          mainPart.addBodyPart(html);      
          // 将MiniMultipart对象设置为邮件内容      
          mailMessage.setContent(mainPart);      
          // 发送邮件      
          Transport.send(mailMessage);      
          return true;      
          } catch (MessagingException ex) {      
              ex.printStackTrace();      
          }   
          return false;      
    }
    
    /**
     * 
     * @param userName 发送邮件用户名
     * @param passWord 发送邮件密码
     * @param toAddress 接收邮件人地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param sendDate  发送时间
     * @return 发送成功，返回true，否则返回false
     */
    public static boolean sendMail(String userName, String passWord, String toAddress, String subject, String content, Date sendDate){
        //这个类主要是设置邮件     
         MailSenderInfo mailInfo = new MailSenderInfo();      

         mailInfo.setMailServerHost("smtp.exmail.qq.com");      
         mailInfo.setValidate(true);      
         mailInfo.setUserName(userName);      
         mailInfo.setPassword(passWord);
         mailInfo.setFromAddress(userName);      
         mailInfo.setToAddress(toAddress); 
         
         mailInfo.setSubject(subject);      
         mailInfo.setContent(content);
         mailInfo.setSendDate(sendDate);
         //这个类主要来发送邮件     
         SimpleMailSender sms = new SimpleMailSender();     
//       return sms.sendTextMail(mailInfo);//发送文体格式    
         return sms.sendHtmlMail(mailInfo); //发送Html格式
    }
    
    /**
     * @param toAddress 接收邮件人地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param sendDate  发送时间s
     * @return 发送成功，返回true，否则返回false
     */
    public static boolean sendMail(String toAddress, String subject, String content, Date sendDate,List <RecordEntity> entitys){
        //这个类主要是设置邮件     
         MailSenderInfo mailInfo = new MailSenderInfo();      
         //String userName = "wuwl@yx129.com";  //发送人邮箱地址
         //String passWord = "Yxw123456";         //发送人邮箱地址密码
         
         //String userName = "jiekoujk@yx129.com";  //发送人邮箱地址
         //String passWord = "5d2frjHZyxKVB7ci";
         
         String userName = "1131353875@qq.com";
         String passWord = "paaewlirpxycjceh";
         mailInfo.setMailServerHost("smtp.qq.com");
         
         //String passWord = "kwyngcerfnbrhfgb";
         //String passWord = "ezgxstehivmyhaeg";
         
         //mailInfo.setMailServerHost("smtp.exmail.qq.com");  //发送人邮箱服务器    
         mailInfo.setValidate(true);      
         mailInfo.setUserName(userName);      
         mailInfo.setPassword(passWord);
         mailInfo.setFromAddress(userName); 
         
         mailInfo.setToAddress(toAddress); 
         mailInfo.setSubject(subject);      
         mailInfo.setContent(content);
         mailInfo.setSendDate(sendDate);
         //这个类主要来发送邮件     
         SimpleMailSender sms = new SimpleMailSender();     
         //return sms.sendTextMail(mailInfo);//发送文体格式    
         //return sms.sendHtmlMail(mailInfo); //发送Html格式
         return sms.sendExcelMail(mailInfo, entitys);
    }
    
    
    /**
     * @param toAddress 接收邮件人地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param sendDate  发送时间s
     * @return 发送成功，返回true，否则返回false
     */
    public static boolean sendMail(String toAddress, String subject, String content, Date sendDate){
        //这个类主要是设置邮件     
         MailSenderInfo mailInfo = new MailSenderInfo();      
         //String userName = "wuwl@yx129.com";  //发送人邮箱地址
         //String passWord = "Yxw123456";         //发送人邮箱地址密码
         
         String userName = "jiekoujk@yx129.com";  //发送人邮箱地址
         String passWord = "5d2frjHZyxKVB7ci";
         
         //String userName = "1131353875@qq.com";
         //String passWord = "paaewlirpxycjceh";
         
         mailInfo.setMailServerHost("smtp.exmail.qq.com");  //发送人邮箱服务器    
         mailInfo.setValidate(true);      
         mailInfo.setUserName(userName);      
         mailInfo.setPassword(passWord);
         mailInfo.setFromAddress(userName); 
         
         mailInfo.setToAddress(toAddress); 
         mailInfo.setSubject(subject);      
         mailInfo.setContent(content);
         mailInfo.setSendDate(sendDate);
         //这个类主要来发送邮件     
         SimpleMailSender sms = new SimpleMailSender();     
         //return sms.sendTextMail(mailInfo);//发送文体格式    
         return sms.sendHtmlMail(mailInfo); //发送Html格式
    }
    
    public static void main(String[] args) {
        String toAddress ="huanghy@yx129.com";
        //String toAddress = "jiekoujk@yx129.com"5d2frjHZyxKVB7ci;
        //251106859@qq.com   125996270@qq.com
        //toAddress ="125996270@qq.com";
        String subject = "主题";
        String content ="<B>内容</B>  连续两次访问不到，请及时解决.谢谢.";
        Date sendDate = new Date();
        sendMail(toAddress, subject, content, sendDate);
    }
    
    
    /**    
     * 以HTML格式发送邮件    
     * @param mailInfo 待发送的邮件信息    
     */      
    public boolean sendExcelMail(MailSenderInfo mailInfo, List <RecordEntity> entitys) {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getQQProperties();
        //Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(mailInfo.getSendDate());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            MimeBodyPart body = new MimeBodyPart(); //正文
            MimeBodyPart attach = new MimeBodyPart(); //附件
            // 设置HTML内容
            body.setContent(mailInfo.getContent(), "text/html; charset=gb2312");
            //File zipFile = new File("C:\\Users\\Administrator\\Desktop\\测试.xlsx");
            //DataSource fds = createExcel();
            DataSource fds = writer(entitys);
            DataHandler attaDH = new DataHandler(fds);
            attach.setDataHandler(attaDH);
            attach.setFileName(MimeUtility.encodeText("处方订单.xlsx"));
            mainPart.addBodyPart(attach);
            mainPart.addBodyPart(body);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            mailMessage.saveChanges();
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException | IOException | IllegalArgumentException | IllegalAccessException ex ) {
            ex.printStackTrace();
        }
        return false;
    }
    
    //private static String title[] = {"姓名","手机","单位","签到时间"};
    //private static String title[] = {"订单号","医院名称","医生名字","科室名称", "卡号","患者名字","手机号"};

    private static String title[] =
        {"医院ID",
        "医院code",
        "医院名称",
        "分院ID",
        "分院code",
        "门诊ID",
        "患者唯一标识",
        "本公众号微信openId",
        "交易类型：支付（1），退费（2）",
        "平台类型(2位) 微信服务窗（01），支付宝服务窗（02），健康易（03）",
        "第三方支付单号",
        "第三方退款支付单号",
        "第三方支付时间",
        "第三方退费时间",
        "支付状态：1：未支付, 2：已支付,3：已退费",
        "订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常",
        "药店状态：1：未配药, 2：已配药,3：已发药",
        "订单号",
        "退费订单号",
        "处方单号hash值",
        "是否已被支付回调  1 是  0 否",
        "总金额",
        "支付金额",
        "接诊科室代码",
        "接诊医生代码",
        "医生名称",
        "处方科室",
        "取药方式：物流配送 ；药店自取",
        "就诊卡类型",
        "就诊卡号",
        "就诊人姓名",
        "临床诊断",
        "药店ID",
        "药房CODE",
        "药店-分店ID",
        "药房-分店CODE",
        "审查处方医师ID",
        "审查状态",
        "审查意见",
        "审药时间",
        "是否发生异常  0 否 1是",
        "是否处理成功   1是   0 否",
        "处理次数",
        "异常处理日志",
        "创建时间",
        "更新时间",
        "折扣金额"};
    public static DataSource writer(List <RecordEntity> entitys) throws IOException, IllegalArgumentException, IllegalAccessException {    
        
        //创建工作文档对象     
        Workbook wb = new XSSFWorkbook();    
           
        //创建sheet对象     
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");    
        //循环写入行数据
        
        for (int i = 0; i < entitys.size() + 1; i++) {    
            Row row = (Row) sheet1.createRow(i);
        
            Class cls = entitys.get(i==0?i:i-1).getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int j = 0; j < fields.length ; j++) {
                 Field f = fields[fields.length-1 -j];
                  f.setAccessible(true);
                  
                Cell cell = row.createCell(j);
                if(i==0){
                    cell.setCellValue(title[j]);
                }else{
                    cell.setCellValue(f.get(entitys.get(i-1))+"");
                }
            }    
        }
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos); // write excel data to a byte array
        bos.close();
        DataSource fds = new ByteArrayDataSource(bos.toByteArray(), "application/vnd.ms-excel");
        //创建文件流  
        //OutputStream stream = new FileOutputStream(path + "/" + fileName+"."+fileType);
        //wb.write(stream);  
        //关闭文件流  
        //stream.close();
        return fds;
    }
    
}
