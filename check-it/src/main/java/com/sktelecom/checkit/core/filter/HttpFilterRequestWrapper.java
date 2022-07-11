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

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpFilterRequestWrapper extends HttpServletRequestWrapper {
	
	private final Log log = LogFactory.getLog(this.getClass().getName());
	private final Map<String, String[]> modifiParams;
	private Map<String, String[]> allParameters = null;
	
	public HttpFilterRequestWrapper(final HttpServletRequest request, final Map<String, String[]> addParams) {
		super(request);
		modifiParams = new TreeMap<String, String[]>();
		modifiParams.putAll(addParams);
	}
	
	@Override
	public String[] getParameterValues(final String name) {
		
		return getParameterMap().get(name);
		
	}
	
	@Override
	public String getParameter(final String name) {
		
		String[] strings = getParameterMap().get(name);
		if(strings != null){
			return strings[0];
		}
		return super.getParameter(name);
		
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {

		if(allParameters == null) {
			allParameters = new TreeMap<String, String[]>();
			allParameters.putAll(super.getParameterMap());
			allParameters.putAll(modifiParams);
		}
		
		return Collections.unmodifiableMap(allParameters);
		
	}

	@Override
	public Enumeration<String> getParameterNames() {
		
		return Collections.enumeration(getParameterMap().keySet());
		
	}
	
}