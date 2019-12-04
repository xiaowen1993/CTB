package com.ctb.framework.commons.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.framework.commons.utils.StringUtils;

public class ReqInfoLogInterceptor implements HandlerInterceptor, Serializable {

	private static final long serialVersionUID = -1571926888764806581L;
	
	private static Logger logger = LoggerFactory.getLogger(ReqInfoLogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject)
			throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			long begin = System.currentTimeMillis();

			String uri = paramHttpServletRequest.getRequestURI();

			if (paramObject instanceof HandlerMethod) {
				Method method = ( (HandlerMethod) paramObject ).getMethod();

				logger.debug(StringUtils.buildInfoString("", "开始请求", uri, ".", method.getName(), ""));

				logger.debug(StringUtils.map2String(paramHttpServletRequest.getParameterMap(), "" + "请求数据", "",
						"--------------------------------------------------------", true, "="));
			}
			paramHttpServletRequest.setAttribute("begin", begin);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			long begin = (Long) paramHttpServletRequest.getAttribute("begin");
			paramHttpServletRequest.removeAttribute("startTime");
			String uri = paramHttpServletRequest.getRequestURI();
			logger.debug(StringUtils.buildInfoString("", "", "结束", uri, ",共花费", System.currentTimeMillis() - begin, "毫秒"));
		}
	}


	@Override
	public void afterCompletion(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Object paramObject,
			Exception paramException) throws Exception {
		// TODO Auto-generated method stub
	}

}
