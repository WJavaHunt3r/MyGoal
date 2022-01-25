package com.ktk.duka.mygoal.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyGoalRequestCache extends HttpSessionRequestCache {
	
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}
}
