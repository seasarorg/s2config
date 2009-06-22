package org.seasar.config.web.action;

import javax.servlet.http.HttpSession;

import org.seasar.struts.annotation.Execute;

public class LogoutAction {
	@Execute(validator = false)
	public String index() {
		return "index.html";
	}

	public HttpSession httpSession;

	@Execute(validator = false)
	public String doLogout() {
		httpSession.invalidate();
		return "index.html";
	}

}
