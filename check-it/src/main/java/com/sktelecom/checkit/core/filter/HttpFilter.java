/*
 * Copyright 2008-2009 MOPAS(MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sktelecom.checkit.core.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpFilter implements Filter{
	
	private final Log log = LogFactory.getLog(this.getClass().getName());
	
	private FilterConfig config;	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// log.info("################################");
		// log.info("#>> Filter 진입...");
		
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httprequest = (HttpServletRequest) request;
			Map<String, String[]> addParam = new HashMap<String, String[]>();
			
//			addParam.putAll(flashMap);
			
			request = new HttpFilterRequestWrapper(httprequest, addParam);
	    }
		
		chain.doFilter(request, response);
		
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	public void destroy() {
		// destroy
	}

	public FilterConfig getConfig() {
		return config;
	}

	public void setConfig(FilterConfig config) {
		this.config = config;
	}
	
}
