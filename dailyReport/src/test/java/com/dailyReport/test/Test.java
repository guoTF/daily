package com.dailyReport.test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Test {

	public static void main(String[] args) throws Exception {
		/*Map<Object, Object> map = new HashMap<>();
		String str = "{payTime:2018-06-25 08:56:33,paySeqId:03300010784N,invoiceAmount:1,settleDate:2018-06-25,buyerId:otdJ_uAsg9ry5fvyIQADbW1g75ms,totalAmount:1,couponAmount:0,billBizType:bills,buyerPayAmount:1,targetOrderId:4200000111201806256585701534,payDetail:现金支付0.01元。,merOrderId:396220180625085615750TF0000050,status:TRADE_SUCCESS,targetSys:WXPay}";
		List list = new ArrayList<>();
		list.add(str);
		map.put("billPayment", str);
		JSONObject jsonArray = new JSONObject(str);
		jsonArray.toString();
		System.out.println(jsonArray.toString());*/
		Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host","smtp.qq.com");// smtp服务器地址
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        
        Message msg = new MimeMessage(session);
        msg.setSubject("这是一个测试程序....");
        msg.setText("你好!这是我的第一个javamail程序---WQ");
        msg.setFrom(new InternetAddress("787999964@qq.com"));//发件人邮箱(我的163邮箱)
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress("gtf13760191776@163.com")); //收件人邮箱(我的QQ邮箱)
        msg.saveChanges();

        Transport transport = session.getTransport();
        transport.connect("787999964@qq.com","lpqajzdfbunsbfbj");//发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)
        
        transport.sendMessage(msg, msg.getAllRecipients());
        
        System.out.println("邮件发送成功...");
        transport.close();
	}
}
