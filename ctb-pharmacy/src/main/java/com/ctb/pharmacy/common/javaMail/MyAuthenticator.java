/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月27日
 * Created by hhy
 */
package com.ctb.pharmacy.common.javaMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @ClassName: com.ctb.pharmacy.common.javaMail.MyAuthenticator
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月27日 下午5:09:36
 */

public class MyAuthenticator extends Authenticator{     
    private String userName;     
    private String password;     
          
    public MyAuthenticator(){}    
    
    public MyAuthenticator(String username, String password) {      
        this.userName = username;      
        this.password = password;      
    }      
    protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
    }      
     
} 
