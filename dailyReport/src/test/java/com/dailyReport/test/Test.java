package com.dailyReport.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class Test {

	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<>();
		String str = "{payTime:2018-06-25 08:56:33,paySeqId:03300010784N,invoiceAmount:1,settleDate:2018-06-25,buyerId:otdJ_uAsg9ry5fvyIQADbW1g75ms,totalAmount:1,couponAmount:0,billBizType:bills,buyerPayAmount:1,targetOrderId:4200000111201806256585701534,payDetail:现金支付0.01元。,merOrderId:396220180625085615750TF0000050,status:TRADE_SUCCESS,targetSys:WXPay}";
		List list = new ArrayList<>();
		list.add(str);
		map.put("billPayment", str);
		JSONObject jsonArray = new JSONObject(str);
		jsonArray.toString();
		System.out.println(jsonArray.toString());
	}
}
