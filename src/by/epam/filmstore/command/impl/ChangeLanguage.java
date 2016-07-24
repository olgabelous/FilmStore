package by.epam.filmstore.command.impl;


import by.epam.filmstore.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		session.setAttribute("locale", request.getParameter("language"));

		String prev_query = (String) request.getSession(false).getAttribute("prev_query");

		if (prev_query != null) {
			response.sendRedirect(prev_query);
		} else {
			request.getRequestDispatcher("main.jsp").forward(request, response);
		}
	}

}
