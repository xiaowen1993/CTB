/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月30日
 * Created by cwq
 */
package com.ctb.syncdata.rest.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.JO;
import com.ctb.syncdata.commons.constants.BizConstants;
import com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO;
import com.ctb.syncdata.rest.services.HospitalService;

/**
 * @ClassName: com.ctb.syncdata.rest.controller.YXWController
 * @Description: TODO(从医享网拿数据)
 * @author cwq
 * @date 2019年3月30日 上午9:44:47
 */

@RefreshScope
@RestController
@RequestMapping("/yxwout")
public class YXWOutController {
    
    private static Logger logger = LoggerFactory.getLogger(YXWOutController.class);
    
    @Autowired
    private HospitalService hospitalService;
    
    /**
     * 
     * @Title: loadMZListData
     * @Description: TODO(查询门诊代缴费记录列表)
     * @author cwq
     * @date 2019年4月9日 下午3:22:31
     * @param hospitalCodes
     * @param unionId
     * @param openId
     * @param cardNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadMZListData")
    public RespBody loadMZListData(@RequestParam(required = true, value = "hospitalCodes") String[] hospitalCodes,
            @RequestParam(required = true, value = "unionId") String unionId,
            @RequestParam(required = false, value = "openId") String openId,
            @RequestParam(required = false, value = "cardNo") String cardNo) {
    	
    	String mzFeeId = "444444444";
    	String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"2\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"2\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\""+mzFeeId+"\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
        
        return new RespBody(Status.OK, JSONArray.parseArray(result));
        /*
        List<HospitalPlatformVO> hspVols = hospitalService.findHospitalPlatformSettingsByCode(hospitalCodes);
        
        if (!CollectionUtils.isEmpty(hspVols)) {// 查询医享网门诊代缴费记录数据
            
            if (StringUtils.isNotBlank(openId)) {// 从医院服务号进来的查询（此入口预留）
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516078")) {
                    
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\"15951140\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516079")) {
                    
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑松成\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"mzFeeId\":\"15951141\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516080")) {
                    
                    String result = "[{\"zsdxdsfsyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"李四\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"42068219830705001X\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374989\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李四\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"mzFeeId\":\"15951142\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"95\",\"medicareAmout\":\"0\",\"totalAmout\":\"95\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516081")) {
                    
                    String result = "[{\"zsdxdsfsyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"郑灏帆\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050019\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374990\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑灏帆\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"mzFeeId\":\"15951143\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"105\",\"medicareAmout\":\"0\",\"totalAmout\":\"105\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }else if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516078")) {
                    
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuFAro9cIl1O4bnUZMpkGn78\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymntwsbG5H81z5MDvxaPkmOjIE\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\"15951140\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516079")) {
                    
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuFAro9cIl1O4bnUZMpkGn78\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymntwsbG5H81z5MDvxaPkmOjIE\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑松成\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"mzFeeId\":\"15951141\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516080")) {
                    
                    String result = "[{\"zsdxdsfsyy\":[{\"unionId\":\"oxIUxuFAro9cIl1O4bnUZMpkGn78\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"李四\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"42068219830705001X\",\"address\":\"湖北省\",\"openId\":\"o9ymntwsbG5H81z5MDvxaPkmOjIE\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374989\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李四\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"mzFeeId\":\"15951142\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"95\",\"medicareAmout\":\"0\",\"totalAmout\":\"95\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516081")) {
                    
                    String result = "[{\"zsdxdsfsyy\":[{\"unionId\":\"oxIUxuFAro9cIl1O4bnUZMpkGn78\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"郑灏帆\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050019\",\"address\":\"湖北省\",\"openId\":\"o9ymntwsbG5H81z5MDvxaPkmOjIE\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374990\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑灏帆\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"mzFeeId\":\"15951143\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"105\",\"medicareAmout\":\"0\",\"totalAmout\":\"105\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")) {
                    
                    // String result =
                        // "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\"15951140\",\"clinicTime\":\"2019-02-14
                        // 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]},{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑松成\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"mzFeeId\":\"15951141\",\"clinicTime\":\"2019-02-14
                        // 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\"15951140\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]},{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑松成\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"mzFeeId\":\"15951141\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]},{\"zsdxdsfsyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"李四\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"42068219830705001X\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374989\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李四\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"mzFeeId\":\"15951142\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"95\",\"medicareAmout\":\"0\",\"totalAmout\":\"95\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]},{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"zsdxdsfsyy\",\"platformMode\":\"1\",\"name\":\"郑灏帆\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050019\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374990\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"zsdxdsfsyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑灏帆\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"mzFeeId\":\"15951143\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"105\",\"medicareAmout\":\"0\",\"totalAmout\":\"105\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else {// 通过hospitalCode、openId、cardNo查询代缴费记录
                    HospitalPlatformVO hspVo = hspVols.get(0);
                    List<Map<String, JSONArray>> MZFeeList = new LinkedList<Map<String, JSONArray>>();
                    Map<String, JSONArray> MZFeeMap = new HashMap<String, JSONArray>();
                    
                    if (hspVo.getState() != null && hspVo.getState().intValue() == BizConstants.HOSPITAL_VALID_STATUS) {
                        
                        if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_WECHAT_VAL) {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("hospitalCode", "");
                            params.put(unionId, "");
                            String rs = HttpClientUtil.getInstance().post(BizConstants.PLATFORM_SYNC_DATA_GATEWAY,
                                    params);
                            if (StringUtils.isNotBlank(rs)) {
                                
                                JSONArray jsonArray = JSONObject.parseArray(rs);
                                
                                MZFeeMap.put(hspVo.getCode(), jsonArray);
                            }
                            
                        } else if (hspVo.getPlatformCode()
                                .intValue() == BizConstants.PLATFORM_MODE_TYPE_HIS_WECHAT_VAL) {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("hospitalCode", "");
                            params.put(unionId, "");
                            String rs = HttpClientUtil.getInstance().post(BizConstants.PROJECT_SYNC_DATA_GATEWAY,
                                    params);
                            if (StringUtils.isNotBlank(rs)) {
                                
                                JSONArray jsonArray = JSONObject.parseArray(rs);
                                
                                MZFeeMap.put(hspVo.getCode(), jsonArray);
                            }
                        }
                        
                        MZFeeList.add(MZFeeMap);
                        
                        if (!CollectionUtils.isEmpty(MZFeeList)) {
                            return new RespBody(Status.OK, JSONArray.parseArray(JSON.toJSONString(MZFeeList)));
                        } else {
                            return new RespBody(Status.PROMPT, "未查询到数据。");
                        }
                        
                    } else {
                        return new RespBody(Status.PROMPT, "所传递医院未上线处方外流。");
                    }
                }
            }
            
            if (StringUtils.isNotBlank(unionId)) {// 从处方平台入口进来的查询（目前只有这个入口）
                if (StringUtils.equals(unionId, "oxIUxuOt_Xra5LGnEdE-Sb6UWhlE")) {
                    String result = "[{\"gzhszhyy\":[{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"李海刚\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"mzFeeId\":\"15951140\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]},{\"unionId\":\"oxIUxuOt_Xra5LGnEdE-Sb6UWhlE\",\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"branchId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchCode\":\"gzhszhyy\",\"platformMode\":\"1\",\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"mobile\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"ownership\":\"5\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"isMedicare\":\"\",\"medicareNo\":\"\",\"mark\":\"\",\"state\":\"1\",\"patientId\":\"2374988\",\"bindWay\":\"-1\",\"createTime\":\"\",\"updateTime\":\"\",\"MZDataList\":[{\"hospitalId\":\"1c64a9b4d62b4316823344ba7dfa2e93\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchHospitalId\":\"cd18dcb0f2904cd29dd851bd01b4cdd9\",\"branchHospitalCode\":\"gzhszhyy\",\"branchHospitalName\":\"\",\"storageTime\":\"1550130536668\",\"name\":\"郑松成\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"mzFeeId\":\"15951141\",\"clinicTime\":\"2019-02-14 15:48:21\",\"deptName\":\"神经内科\",\"doctorName\":\"超级用户\",\"payType\":\"自费\",\"payAmout\":\"85\",\"medicareAmout\":\"0\",\"totalAmout\":\"85\",\"sSClinicNo\":\"\",\"sSBillNumber\":\"\",\"canUseInsurance\":\"\",\"recipeType\":\"\",\"recipeId\":\"\",\"mzBillId\":\"10045245\",\"insuranceAmout\":\"\"}]}]}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                } else {
                    // JSONArray====>>List<Map<String, Object>>
                    List<Map<String, JSONArray>> MZFeeList = new LinkedList<Map<String, JSONArray>>();
                    for (HospitalPlatformVO hspVo : hspVols) {
                        
                        Map<String, JSONArray> MZFeeMap = new HashMap<String, JSONArray>();
                        
                        if (hspVo.getState() != null
                                && hspVo.getState().intValue() == BizConstants.HOSPITAL_VALID_STATUS) {// 通过hospitalCode、unionId查询代缴费记录
                            
                            if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_WECHAT_VAL) {
                                *//** yxw通过hospitalCode、unionId查询一个医院的openId所关联的卡号，然后遍历卡信息去查询待缴费记录 *//*
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("hospitalCode", "");
                                params.put(unionId, "");
                                String rs = HttpClientUtil.getInstance().post(BizConstants.PLATFORM_SYNC_DATA_GATEWAY,
                                        params);
                                if (StringUtils.isNotBlank(rs)) {
                                    
                                    JSONArray jsonArray = JSONObject.parseArray(rs);
                                    
                                    MZFeeMap.put(hspVo.getCode(), jsonArray);
                                }
                                
                            } else if (hspVo.getPlatformCode()
                                    .intValue() == BizConstants.PLATFORM_MODE_TYPE_HIS_WECHAT_VAL) {
                                
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("hospitalCode", "");
                                params.put(unionId, "");
                                String rs = HttpClientUtil.getInstance().post(BizConstants.PROJECT_SYNC_DATA_GATEWAY,
                                        params);
                                if (StringUtils.isNotBlank(rs)) {
                                    
                                    JSONArray jsonArray = JSONObject.parseArray(rs);
                                    
                                    MZFeeMap.put(hspVo.getCode(), jsonArray);
                                }
                                
                            }
                            
                            MZFeeList.add(MZFeeMap);
                            
                        }
                    }
                    
                    if (!CollectionUtils.isEmpty(MZFeeList)) {
                        return new RespBody(Status.OK, JSONArray.parseArray(JSON.toJSONString(MZFeeList)));
                    } else {
                        return new RespBody(Status.PROMPT, "未查询到数据。");
                    }
                }
            }
            
            return new RespBody(Status.PROMPT, "未查询到数据。");
            
        } else {
            return new RespBody(Status.PROMPT, "所传递医院未上线处方外流。");
        }
        */
    }
    
    /**
     * 
     * @Title: loadMedicalCardInfo
     * @Description: TODO(获取yxw患者绑卡信息)
     * @author cwq
     * @date 2019年4月10日 上午9:58:29
     * @param openId
     * @param hospitalCode
     * @param cardNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadMedicalCardInfo")
    public RespBody loadMedicalCardInfo(@RequestParam(required = true, value = "openId") String openId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "cardNo") String cardNo) {
        
        List<HospitalPlatformVO> hspVols = hospitalService.findHospitalPlatformSettingsByCode(hospitalCode);
        
        if (!CollectionUtils.isEmpty(hspVols)) {
            // 查询医享网患者绑卡记录数据
            if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(cardNo)) {
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516078")) {
                    String result = "{\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"440103195112243912\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchCode\":\"gzhszhyy\",\"branchName\":\"广州市红十字会医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516079")) {
                    String result = "{\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchCode\":\"gzhszhyy\",\"branchName\":\"广州市红十字会医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516080")) {
                    String result = "{\"name\":\"李四\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"42068219830705001X\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchCode\":\"zsdxdsfsyy\",\"branchName\":\"中山大学第三附属医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516081")) {
                    String result = "{\"name\":\"郑灏帆\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050019\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchCode\":\"zsdxdsfsyy\",\"branchName\":\"中山大学第三附属医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                
                
                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516078")) {
                    String result = "{\"name\":\"李海刚\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"440103195112243912\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516078\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchCode\":\"gzhszhyy\",\"branchName\":\"广州市红十字会医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516079")) {
                    String result = "{\"name\":\"郑松成\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050013\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516079\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"gzhszhyy\",\"hospitalName\":\"广州市红十字会医院\",\"branchCode\":\"gzhszhyy\",\"branchName\":\"广州市红十字会医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516080")) {
                    String result = "{\"name\":\"李四\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258048\",\"idType\":\"1\",\"idNo\":\"42068219830705001X\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516080\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchCode\":\"zsdxdsfsyy\",\"branchName\":\"中山大学第三附属医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516081")) {
                    String result = "{\"name\":\"郑灏帆\",\"sex\":\"1\",\"age\":\"35\",\"birth\":\"1983-07-05\",\"moblie\":\"13922258049\",\"idType\":\"1\",\"idNo\":\"420682198307050019\",\"address\":\"湖北省\",\"cardType\":\"1\",\"cardNo\":\"4401000004516081\",\"guardName\":\"\",\"guardIdType\":\"\",\"guardIdNo\":\"\",\"guardMobile\":\"\",\"hospitalCode\":\"zsdxdsfsyy\",\"hospitalName\":\"中山大学第三附属医院\",\"branchCode\":\"zsdxdsfsyy\",\"branchName\":\"中山大学第三附属医院\",\"platformMode\":\"1\"}";
                    return new RespBody(Status.OK, JSONObject.parseObject(result));
                }
                
                HospitalPlatformVO hspVo = hspVols.get(0);
                if (hspVo.getState() != null && hspVo.getState().intValue() == BizConstants.HOSPITAL_VALID_STATUS) {
                    
                    if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_WECHAT_VAL) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hospitalCode", "");
                        params.put("openId", "");
                        params.put("cardNo", "");
                        String rs = HttpClientUtil.getInstance().post(BizConstants.PLATFORM_SYNC_DATA_GATEWAY, params);
                        medicalCardFormatData(rs);
                        
                    } else if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_HIS_WECHAT_VAL) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hospitalCode", "");
                        params.put("openId", "");
                        params.put("cardNo", "");
                        String rs = HttpClientUtil.getInstance().post(BizConstants.PROJECT_SYNC_DATA_GATEWAY, params);
                        medicalCardFormatData(rs);
                    }
                    
                } else {
                    return new RespBody(Status.PROMPT, "所传递医院未上线处方外流。");
                }
                
                return new RespBody(Status.PROMPT, "未查询到数据。");
                
            } else {
                return new RespBody(Status.PROMPT, "所传递参数有误。");
            }
            
        } else {
            return new RespBody(Status.PROMPT, "该医院未上线处方外流。");
        }
        
    }
    
    private RespBody medicalCardFormatData(String data) {
        // TODO Auto-generated method stub
        if (StringUtils.isNotBlank(data)) {
            JO jo = new JO(data);
            if (jo.getInt("resultCode", -1) == 0) {
                return new RespBody(Status.OK, jo.getJObject("result"));
            } else {
                return new RespBody(Status.PROMPT, jo.getStr("resultMessage"));
            }
        } else {
            return new RespBody(Status.PROMPT, "查询就诊卡信息为空。");
        }
    }
    
    /**
     * 
     * @Title: loadMZDetailsData
     * @Description: TODO(查询门诊代缴费详情)
     * @author cwq
     * @date 2019年4月10日 上午10:58:44
     * @param openId
     * @param hospitalCode
     * @param branchHospitalCode
     * @param cardNo
     * @param cardType
     * @param mzFeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadMZDetailsData")
    public RespBody loadMZDetailsData(@RequestParam(required = false, value = "openId") String openId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "branchHospitalCode") String branchHospitalCode,
            @RequestParam(required = true, value = "cardNo") String cardNo,
            @RequestParam(required = true, value = "cardType") String cardType,
            @RequestParam(required = true, value = "mzFeeId") String mzFeeId) {
        
    	String itemId = "1790082912";
    	String itemId1 = "179008290";
    	
    	
    	
    	String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\""+mzFeeId+"\",\"itemTime\":null,\"itemId\":\""+itemId+"\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\""+mzFeeId+"\",\"itemTime\":null,\"itemId\":\""+itemId1+"\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"2\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
        return new RespBody(Status.OK, JSONArray.parseArray(result));
    	/*
        List<HospitalPlatformVO> hspVols = hospitalService.findHospitalPlatformSettingsByCode(hospitalCode);
        
        if (!CollectionUtils.isEmpty(hspVols)) {
            // 查询yxw代缴费详情数据
            if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(cardNo)) {
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516078") && StringUtils.equals(mzFeeId, "15951140")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"1790082912\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }
                
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516079") && StringUtils.equals(mzFeeId, "15951141")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008291\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }
                
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516080") && StringUtils.equals(mzFeeId, "15951142")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"1790082912\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }
                
                if (StringUtils.equals(openId, "o9ymnt7Fknhj3kHTvgR3b0ASDRgU")
                        && StringUtils.equals(cardNo, "4401000004516081") && StringUtils.equals(mzFeeId, "15951143")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008291\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }
                
                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516078") && StringUtils.equals(mzFeeId, "15951140")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"1790082912\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951140\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }

                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516079") && StringUtils.equals(mzFeeId, "15951141")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008291\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951141\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }

                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516080") && StringUtils.equals(mzFeeId, "15951142")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"1790082912\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951142\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }

                if (StringUtils.equals(openId, "o9ymntwsbG5H81z5MDvxaPkmOjIE")
                        && StringUtils.equals(cardNo, "4401000004516081") && StringUtils.equals(mzFeeId, "15951143")) {
                    String result = "[{\"id\":\"00008f5d86c44f5ea36e16d379e16676\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008291\",\"itemName\":\"润肤乳膏\",\"itemType\":\"西药费\",\"itemUnit\":\"盒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"3\",\"itemTotalFee\":\"3348\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"07abb32e499e408ea52c711765030717\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008288\",\"itemName\":\"环孢素软胶囊\",\"itemType\":\"西药费\",\"itemUnit\":\"粒\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"120\",\"itemTotalFee\":\"62340\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"11c817712c8646888777e9650f4d1dae\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008290\",\"itemName\":\"脾氨肽口服冻干粉(复可托)\",\"itemType\":\"西药费\",\"itemUnit\":\"支\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"15\",\"itemTotalFee\":\"36570\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"80151318129741d59ad76be3e90f90c0\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008289\",\"itemName\":\"复方甘草酸苷片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"180\",\"itemTotalFee\":\"24187\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"821196f8fb1c4c3c9ff3be9954e26097\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008292\",\"itemName\":\"吡硫翁锌气雾剂\",\"itemType\":\"西药费\",\"itemUnit\":\"瓶\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"1\",\"itemTotalFee\":\"25658\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null},{\"id\":\"ca3194174b794cf297b21ab692e5dc91\",\"mzFeeId\":\"15951143\",\"itemTime\":null,\"itemId\":\"179008287\",\"itemName\":\"奥洛他定片\",\"itemType\":\"西药费\",\"itemUnit\":\"片\",\"itemPrice\":null,\"itemSpec\":null,\"itemNumber\":\"70\",\"itemTotalFee\":\"28405\",\"deptName\":null,\"doctorName\":null,\"doctorCode\":null,\"socialInsuranceItemid\":null,\"medicareItemid\":null}]";
                    return new RespBody(Status.OK, JSONArray.parseArray(result));
                }
                
                HospitalPlatformVO hspVo = hspVols.get(0);
                if (hspVo.getState() != null && hspVo.getState().intValue() == BizConstants.HOSPITAL_VALID_STATUS) {
                    
                    if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_WECHAT_VAL) {
                        
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hospitalCode", "");
                        params.put("branchHospitalCode", "");
                        params.put("cardType", "");
                        params.put("cardNo", "");
                        params.put("mzFeeId", "");
                        params.put("channelType", BizConstants.WECHAT_CHANNEL_TYPE_VAL);
                        
                        String rs = HttpClientUtil.getInstance().post(BizConstants.PLATFORM_SYNC_DATA_GATEWAY, params);
                        mzDetailsFormatData(rs);
                        
                    } else if (hspVo.getPlatformCode().intValue() == BizConstants.PLATFORM_MODE_TYPE_HIS_WECHAT_VAL) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hospitalCode", "");
                        params.put("branchHospitalCode", "");
                        params.put("cardType", "");
                        params.put("cardNo", "");
                        params.put("mzFeeId", "");
                        params.put("channelType", BizConstants.WECHAT_CHANNEL_TYPE_VAL);
                        
                        String rs = HttpClientUtil.getInstance().post(BizConstants.PROJECT_SYNC_DATA_GATEWAY, params);
                        mzDetailsFormatData(rs);
                    }
                    
                } else {
                    return new RespBody(Status.PROMPT, "所传递医院未上线处方外流。");
                }
                
                return new RespBody(Status.PROMPT, "未查询到数据。");
                
            } else {
                return new RespBody(Status.PROMPT, "所传递参数有误。");
            }
            
        } else {
            return new RespBody(Status.PROMPT, "该医院未上线处方外流。");
        }
        */
        
    }
    
    private RespBody mzDetailsFormatData(String data) {
        // TODO Auto-generated method stub
        if (StringUtils.isNotBlank(data)) {
            JO jo = new JO(data);
            if (jo.getInt("resultCode", -1) == 0) {
                return new RespBody(Status.OK, jo.getJObject("result"));
            } else {
                return new RespBody(Status.PROMPT, jo.getStr("resultMessage"));
            }
        } else {
            return new RespBody(Status.PROMPT, "查询代缴费详情信息为空。");
        }
    }
    
}
