package com.jerry.interceptor;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.jerry.exception.BusinessException;

public class WebsiteInterceptor implements WebRequestInterceptor {
	
	private Logger logger = Logger.getLogger(getClass());

	private static final String UNKNOWN_ERROR = "未知错误，请联系管理员!";
	
	@Override
	public void afterCompletion(WebRequest request, Exception exception)
			throws Exception {
		if(exception==null)
			return;
		logger.debug("Enter website interceptor");
		String message = null;
		if(exception instanceof BusinessException){
			BusinessException be = (BusinessException)exception;
			message = be.getMessage();
		}else{
			message = UNKNOWN_ERROR;
		}
		request.setAttribute("errorMessage", message, WebRequest.SCOPE_REQUEST);
	}

	@Override
	public void postHandle(WebRequest arg0, ModelMap arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preHandle(WebRequest arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
