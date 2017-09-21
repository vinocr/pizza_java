package com.wipro.weather.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.wipro.weather.bean.WeatherBean;
import com.wipro.weather.util.DBUtil;

public class WeatherDAO {

	public String createForecast(WeatherBean weatherBean){
		try{
			Connection con=DBUtil.getDBConnection();
			String sql="insert into WEATHER_TB values(?,?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, weatherBean.getReportId());
			ps.setString(2, weatherBean.getLocation());
			ps.setDate(3, new java.sql.Date(weatherBean.getDate().getTime()));
			ps.setInt(4, weatherBean.getTemperature());
			ps.setInt(5, weatherBean.getHumidity());
			ps.setString(6, weatherBean.getWind());
			ps.setString(7, weatherBean.getForecast());
			int i=ps.executeUpdate();
			if(i==0)
				return "FAIL";
			else
				return weatherBean.getReportId();
		}
		catch(Exception e){
			return "FAIL";
		}
	}
	
	public WeatherBean fetchForecast(String location, Date date){
		try{
			Connection con=DBUtil.getDBConnection();
			String sql="select * from WEATHER_TB where LOCATION=? and R_DATE=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, location);
			ps.setDate(2, date);
			ResultSet rs=ps.executeQuery();
			WeatherBean bean=null;
			while(rs.next()){
				bean=new WeatherBean();
				bean.setReportId(rs.getString(1));
				bean.setLocation(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setTemperature(rs.getInt(4));
				bean.setHumidity(rs.getInt(5));
				bean.setWind(rs.getString(6));
				bean.setForecast(rs.getString(7));
			}
			return bean;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public String generateReportID(String location,java.util.Date date){
		try{
			Connection con=DBUtil.getDBConnection();
			String sql="select WEATHER_SEQ.NEXTVAL from dual";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			int id=0;
			while(rs.next()){
				id=rs.getInt(1);
			}
			DateFormat format=new SimpleDateFormat("yyyyMMdd");
			return format.format(date)+location.substring(0,2).toUpperCase()+id;
		}
		catch(Exception e){
			return "";
		}
	}
	
	public boolean reportExists(String location,Date date){
		if(fetchForecast(location, date)==null){
			return false;
		}
		else{
			return true;
		}
	}
	
}
