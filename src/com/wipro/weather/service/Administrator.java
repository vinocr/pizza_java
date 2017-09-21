package com.wipro.weather.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyStore.Entry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.wipro.weather.bean.WeatherBean;
import com.wipro.weather.dao.WeatherDAO;
import com.wipro.weather.util.DBUtil;
import com.wipro.weather.util.InvalidInputException;

public class Administrator {

	public String addForecast(WeatherBean weatherBean){
		try{
			if(weatherBean==null){
				throw new InvalidInputException();
			}
			else if(weatherBean.getLocation()==null||weatherBean.getDate()==null){
				throw new InvalidInputException();
			}
			else if(weatherBean.getLocation().length()<2){
				return "INVALID LOCATION";
			}
			else if(weatherBean.getDate().getTime()<new Date().getTime()){
				return "INVALID DATE";
			}
			else if(new WeatherDAO().reportExists(weatherBean.getLocation(), new java.sql.Date(weatherBean.getDate().getTime()))){
				return "ALREADY EXISTS";
			}
			else{
				String i=new WeatherDAO().generateReportID(weatherBean.getLocation(), weatherBean.getDate());
				weatherBean.setReportId(i);
				return new WeatherDAO().createForecast(weatherBean);
			}
		}
		catch (InvalidInputException e) {
			return "INVALID INPUT";
		}
	}
	
	public WeatherBean viewForecast(String location,Date date){
		return new WeatherDAO().fetchForecast(location, new java.sql.Date(date.getTime()));
	}
	
	public static void main(String[] args){
		
	}
	
}
