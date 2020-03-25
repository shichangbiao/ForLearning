package cn.itcast.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDao;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDao;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

public class RouteServiceImpl implements RouteService {

	@Override
	public Route getRouteBean(String rid, String sid) {
		// 商品列表数据库操作类
		RouteImgDao routeImageDao = new RouteImgDaoImpl();
		// 销售商数据库操作类
		SellerDao sellerDao = new SellerDaoImpl();
		List<RouteImg> routeImagDaoList = new ArrayList<RouteImg>();
		Seller seller = new Seller();
		RouteDao routeDao = new RouteDaoImpl();
		routeImagDaoList = routeImageDao.getRouteList(rid);
		seller = sellerDao.findSellerBySid(sid);
		Route retRoute = routeDao.findRouteByRid(rid);
		retRoute.setSeller(seller);
		retRoute.setRouteImgList(routeImagDaoList);
		return retRoute;
	}
	
	public static void main(String[] args) {
		RouteService routeServiceImpl = new RouteServiceImpl();
		System.out.println(routeServiceImpl.getRouteBean("5", "1"));
	}

}
