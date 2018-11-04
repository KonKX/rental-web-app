package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserAccount;
import bean.*;

/**
 * Servlet implementation class for Servlet: Controller
 *
 */

public class Controller extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Connection connection = null;
		String host = "localhost";
		String dbName = "airbnb";
		int port = 3306;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?verifyServerCertificate=false&useSSL=true";

		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				out.println(e.toString());
				return;
			}
			connection = DriverManager.getConnection(url, "root", "123kodikos");
		} catch (SQLException e) {
			out.println(e.toString());
			System.out.println("Unable to connect to database.");
			return;
		}

		UserAccount account = new UserAccount(connection);

		String action = request.getParameter("action");

		if (action == null) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else if (action.equals("register")) {
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} else if (action.equals("login")) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else if (action.equals("profile")) {
			String username = request.getParameter("user");
			User user;
			try {
				user = account.getUser(username);
				request.setAttribute("user", user);
			} catch (SQLException e) {
				out.println(e.toString());
				System.out.println("Unable to show profile.");
				return;
			}
			request.getRequestDispatcher("/profile.jsp").forward(request, response);
		} else if (action.equals("getcharacteristics")) {
			String id = request.getParameter("id");
			Listing listing;

			try {
				listing = account.getCharacteristics(id);
				request.setAttribute("listing", listing);
				request.getRequestDispatcher("/characteristics.jsp").forward(request, response);
			} catch (SQLException e) {
				out.println(e.toString());
				System.out.println("Unable to show results.");
				return;
			}
		} else {
			out.println("Oops error");
			return;
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		Connection connection = null;
		String host = "localhost";
		String dbName = "airbnb";
		int port = 3306;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?verifyServerCertificate=false&useSSL=true";

		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				out.println(e.toString());
				return;
			}
			connection = DriverManager.getConnection(url, "root", "123kodikos");
		} catch (SQLException e) {
			out.println(e.toString());
			System.out.println("Unable to connect to database.");
			return;
		}

		UserAccount account = new UserAccount(connection);

		if (action.equals("getresults")) {
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String people = request.getParameter("people");
			List<?> list;

			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("user".equals(cookie.getName())) {
						String username = cookie.getValue();	
						try {		
							Cookie role = new Cookie("role", account.getRole(username));
							role.setMaxAge(3600);
							response.addCookie(role);
							Cookie date1 = new Cookie("checkin", checkin);
							date1.setMaxAge(3600);
							response.addCookie(date1);
							Cookie date2 = new Cookie("checkout", checkout);
							date2.setMaxAge(3600);
							response.addCookie(date2);
							Cookie guests = new Cookie("people", people);
							guests.setMaxAge(3600);
							response.addCookie(guests);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}

			try {
				list = account.getResults(checkin, checkout, people);
				request.setAttribute("list", list);
				request.getRequestDispatcher("/resultlist.jsp").forward(request, response);
			} catch (SQLException e) {
				out.println(e.toString());
				System.out.println("Unable to show results.");
				return;
			}
		} 
		else if (action.equals("book")){
			
			String username = request.getParameter("username");
			String checkin = request.getParameter("checkin");
			String checkout = request.getParameter("checkout");
			String people = request.getParameter("people");
			String id = request.getParameter("id");
			
			try {
				account.book(username, checkin, checkout, people, id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("Listing with id " + id + " has been booked");
		}
		
		else if (action.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			request.setAttribute("message", " ");

			try {
				if (account.login(username, password)) {
					if (account.getRole(username).equals("Admin")) {

						Cookie cookie = new Cookie("user", "Admin");
						cookie.setMaxAge(3600);
						response.addCookie(cookie);

						List<?> list = account.getList();
						request.setAttribute("list", list);
						request.getRequestDispatcher("/adminpage.jsp").forward(request, response);
						// response.sendRedirect("/TED_alpha/adminpage.jsp");
					} else {

						Cookie cookie = new Cookie("user", username);
						cookie.setMaxAge(3600);
						response.addCookie(cookie);
						response.sendRedirect("/TED_alpha/index.jsp");
						// request.getRequestDispatcher("/index.jsp").forward(request,
						// response);
					}
				} else {
					request.setAttribute("message", "Wrong username or password");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				out.println(e.toString());
				System.out.println("Unable to connect to database.");
				return;
			}
		}

		else if (action.equals("new account")) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String repeatPassword = request.getParameter("password_repeat");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String role = request.getParameter("role");

			if (!password.equals(repeatPassword)) {
				request.setAttribute("pmessage", "The passwords you entered don't match.");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			} else {
				try {
					if (account.exists(username)) {
						request.setAttribute("umessage", "This username already exists, please choose another one.");
						request.getRequestDispatcher("/register.jsp").forward(request, response);
					} else {
						if (role.equals("Host")) {
							account.insert(username, password, name, surname, email, phone, "Pending");
							request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
						} else {
							account.insert(username, password, name, surname, email, phone, role);
							out.println("Thanks for joining, " + username + "!");
						}
					}

				} catch (SQLException e) {
					out.println(e.toString());
					return;
				}
			}
		}

		else if (action.equals("confirm")) {
			String username = request.getParameter("username");
			try {
				account.userConfirm(username);
			} catch (SQLException e) {

				out.println("Couldn't confirm");
				return;
			}
			out.println("User confirmed");
		} else if (action.equals("updateprofile")) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			try {
				account.updateUser(username, password, name, surname, email, phone);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("Your profile has been updated");
		}

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
