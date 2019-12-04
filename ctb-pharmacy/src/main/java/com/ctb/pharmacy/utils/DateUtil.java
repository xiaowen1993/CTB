/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月9日
 * Created by hhy
 */
package com.ctb.pharmacy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: com.ctb.pharmacy.utils.DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月9日 下午5:30:44
 */

public class DateUtil {
    
    /* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }
    
}
