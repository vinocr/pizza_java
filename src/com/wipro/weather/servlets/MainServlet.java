package com.wipro.weather.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipro.weather.bean.WeatherBean;
import com.wipro.weather.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		String action=req.getParameter("operation");
		if(action.equalsIgnoreCase("newForecast")){
			if(addForecast(req).equalsIgnoreCase("FAIL")){
				req.getRequestDispatcher("error.html").forward(req, resp);
			}
			else{
				req.getRequestDispatcher("success.html").forward(req, resp);
			}
		}
		else if(action.equalsIgnoreCase("viewForecast")){
			WeatherBean wb=viewForecast(req);
			if(wb==null){
				req.getRequestDispatcher("displayForecast.jsp").forward(req, resp);
			}
			else{
				req.setAttribute("bean", wb);
				req.getRequestDispatcher("displayForecast.jsp").forward(req, resp);
			}
		}
		
	}
	
	public String addForecast(HttpServletRequest request){
		WeatherBean wb=new WeatherBean();
		wb.setLocation(request.getParameter("location"));
		wb.setDate(new Date(request.getParameter("date")));
		wb.setTemperature(Integer.parseInt("temperature"));
		wb.setHumidity(Integer.parseInt("humidity"));
		wb.setWind(request.getParameter("wind"));
		wb.setForecast(request.getParameter("forecast"));
		return new Administrator().addForecast(wb);
	}

	public WeatherBean viewForecast(HttpServletRequest request){
		return new Administrator().viewForecast(request.getParameter("location"), new Date(request.getParameter("date")));
	}
	
	
	
}
