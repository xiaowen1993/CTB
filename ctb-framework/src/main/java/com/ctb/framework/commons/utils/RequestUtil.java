package com.ctb.framework.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @ClassName: com.ctb.framework.commons.utils.RequestUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月3日 下午6:35:07
 */
public class RequestUtil {

	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParamsByQueryString(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				String[] queryStringSplitArray = queryString.split("&");

				for (String item : queryStringSplitArray) {
					String[] itemSplitArray = item.split("=");
					String key = itemSplitArray[0];
					String value = "";
					if (itemSplitArray.length == 2) {
						value = itemSplitArray[1];
					} else if (itemSplitArray.length > 2) {
						itemSplitArray[0] = "";
						value = StringUtils.join(itemSplitArray, "");
					}
					params.put(key, value);
				}
			}
		}

		return params;
	}

	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			@SuppressWarnings("unchecked")
			Map<String, String[]> reqParams = request.getParameterMap();
			for (Map.Entry<String, String[]> entry : reqParams.entrySet()) {
				// System.out.println(entry.getKey() + "=" + StringUtils.join(entry.getValue(),
				// ","));
				params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
			}
		}

		return params;
	}

	/**
	 * 获取所有 request 请求参数 key-value
	 * 
	 * @param request HttpServletRequest
	 * @return Map<String, String>
	 */
	public static Map<String, String> getRequestParamsNotBlank(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();

		if (null != request) {
			@SuppressWarnings("unchecked")
			Set<String> keys = request.getParameterMap().keySet();
			for (String key : keys) {
				String value = request.getParameter(key);
				if (StringUtils.isNotBlank(value)) {
					params.put(key, value);
				}
			}
		}

		return params;
	}

	/**
	 * 获取 request 输入流参数
	 * 
	 * @param request HttpServletRequest
	 * @param charset
	 * @return String
	 */
	public static String getInputStreamAsString(HttpServletRequest request, String charset) {
		String s = "";

		try {
			ServletInputStream inStream = request.getInputStream();
			if (inStream == null)
				return null;

			s = IOUtils.toString(inStream, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	public static String getInputStreamAsString(HttpServletRequest request) {
		return getInputStreamAsString(request, "utf-8");
	}

	public static Map<String, String> getInputStreamAsMap(HttpServletRequest request) throws DocumentException {
		return xmlToMap(getInputStreamAsString(request, "utf-8"));
	}

	public static String mapToXml(Map<String, String> params) {
		Element xml = DocumentHelper.createElement("xml");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			xml.addElement(entry.getKey()).addCDATA(entry.getValue());
		}

		return xml.asXML();
	}

	public static Map<String, String> xmlToMap(Element root) {
		if (root == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		List<Element> elsments = root.elements();
		for (Element element : elsments) {
			map.put(element.getName(), element.getText());
		}

		return map;
	}

	public static Map<String, String> xmlToMap(String xml) throws DocumentException {
		if (StringUtils.isNotBlank(xml)) {
			Document doc = DocumentHelper.parseText(xml);
			return xmlToMap(doc.getRootElement());
		} else {
			return new HashMap<String, String>();
		}
	}

	/**
	 * 	解析发来的请求（XML）
	 * @Title: parseXml
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月9日 下午3:15:28
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		String requestXml = document.asXML();
		String subXml = requestXml.split(">")[0] + ">";
		requestXml = requestXml.substring(subXml.length());
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		map.put("requestXml", requestXml);
		inputStream.close();
		inputStream = null;
		return map;
	}

}
