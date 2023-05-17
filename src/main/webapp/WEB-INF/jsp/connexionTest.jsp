<%
	boolean isConnected = false;
	Cookie[] cookies = request.getCookies();

	if (session.getAttribute("noUtilisateur") != null) {
		isConnected = true;
	}
	
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cookieLogin")) {
				isConnected = true;
				session.setAttribute("noUtilisateur", cookie.getValue().trim());
			}
		}
	}
%>