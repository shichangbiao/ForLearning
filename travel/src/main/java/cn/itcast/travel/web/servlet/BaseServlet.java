package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 获取请求的URI
    	String uriString = request.getRequestURI();
    	// 获取请求的方法名称
    	String methodName = uriString.substring(uriString.lastIndexOf("/")+1);
    	//this谁调用我？我代表谁
        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 回写信息到前台
     * @param obj 需要序列化的对象
     * @param response 响应
     */
    protected void writeMsg(Object obj, HttpServletResponse response) {
    	ObjectMapper objMapper = new ObjectMapper();
    	try {
    		response.setCharacterEncoding("utf-8");
    		response.setContentType("application/json;charset=utf-8");
			objMapper.writeValue(response.getOutputStream(), obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
