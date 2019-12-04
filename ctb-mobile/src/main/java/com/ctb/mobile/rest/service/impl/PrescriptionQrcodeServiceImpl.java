package com.ctb.mobile.rest.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.PrescriptionQrcode;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.mobile.rest.service.PrescriptionQrcodeService;
import com.ctb.resources.mapper.biz.PrescriptionQrcodeMapper;

@Service
public class PrescriptionQrcodeServiceImpl implements PrescriptionQrcodeService {

    private static Logger logger = LoggerFactory.getLogger(PrescriptionQrcodeServiceImpl.class);

	@Autowired
	private PrescriptionQrcodeMapper prescriptionQrcodeMapper;

	@Override
	public String updateByOrderNo(String orderNo) {
		
		PrescriptionQrcode record = new PrescriptionQrcode();
		record.setOrderNo(orderNo);
		
		PrescriptionQrcode prescriptionQrcode = prescriptionQrcodeMapper.selectOne(record);
		
		if( prescriptionQrcode==null ){
			PrescriptionQrcode prescriptionQrcode1 = new PrescriptionQrcode();
			prescriptionQrcode1.setId( PKGenerator.generateId() );
			prescriptionQrcode1.setOrderNo(orderNo);
			prescriptionQrcode1.setValue(PKGenerator.generateId());
			prescriptionQrcode1.setCreateTime(new Date());
			prescriptionQrcode1.setExpirationTime( new Date(System.currentTimeMillis()+10000) );
			
			prescriptionQrcodeMapper.insert(prescriptionQrcode1);
			
		}else{
			prescriptionQrcode.setValue(PKGenerator.generateId());
			prescriptionQrcode.setCreateTime(new Date());
			prescriptionQrcode.setExpirationTime( new Date(System.currentTimeMillis()+10000) );
			
			prescriptionQrcodeMapper.updateByPrimaryKeySelective(prescriptionQrcode);
		}
		
		return prescriptionQrcode.getValue();
	}
	
	
	public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
        Date now = new Date();
        System.out.println("当前时间：" + dateFormat.format(now));
 
        Date newDate = addSeconds(now, 25);
        System.out.println("25秒后：" + dateFormat.format(newDate));
    }
 
    private static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
	
	
	
}
