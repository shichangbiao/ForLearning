package cn.itcast.travel.service.impl;

import java.util.List;
import java.util.Map;

import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Page;
import cn.itcast.travel.domain.Route;

public class PageServiceImpl implements PageService {

	RouteDao routeDao = new RouteDaoImpl();

	@Override
	public int findAllRoute(int cid) {
		return routeDao.findAllRoute(cid);
	}

	@Override
	public Page getDividePageBean(int currentPageNum, int pageSize, int cid) {
		Page retPage = new Page();
		// 返回数据列表
		retPage.setComtantList(getRouteListByCurrentPageNumAndPageSize(currentPageNum, pageSize, cid));
		// 当前页码
		retPage.setCurrnetPageNum(currentPageNum);
		// 分页大小
		retPage.setNumPage(pageSize);
		// 分页数目
		retPage.setTotalCount(findAllRoute(cid));
		// 一共可以分多少页
		retPage.setTotalPage((findAllRoute(cid) % pageSize) == 0 ? (findAllRoute(cid) / pageSize)
				: (findAllRoute(cid) / pageSize) + 1);
		return retPage;
	}

	@Override
	public List<Route> getRouteListByCurrentPageNumAndPageSize(int currentPageNum, int pageSize, int cid) {
		return routeDao.getRouteByCurrentPageAndPageSize(currentPageNum, pageSize, cid);
	}

	@Override
	public List<Route> getRouteListByCurrentPageNumAndPageSizeAndQueryCondition(int currentPageNum, int pageSize,
			int cid, Map<String, String> conditionMapper) {
		return routeDao.getRouteByCurrentPageAndPageSizeAndQueryCondition(currentPageNum, pageSize, cid,
				conditionMapper);
	}

	@Override
	public int findAllRoute(int cid, Map<String, String> conditionMapper) {
		return routeDao.findAllRouteByQueryCondition(cid, conditionMapper);
	}

	@Override
	public Page getDividePageBean(int currentPageNum, int pageSize, int cid, Map<String, String> conditionMapper) {
		Page retPage = new Page();
		// 返回数据列表
		retPage.setComtantList(getRouteListByCurrentPageNumAndPageSizeAndQueryCondition(currentPageNum, pageSize, cid, conditionMapper));
		// 当前页码
		retPage.setCurrnetPageNum(currentPageNum);
		// 分页大小
		retPage.setNumPage(pageSize);
		// 分页数目
		retPage.setTotalCount(findAllRoute(cid, conditionMapper));
		// 一共可以分多少页
		retPage.setTotalPage((findAllRoute(cid, conditionMapper) % pageSize) == 0 ? (findAllRoute(cid, conditionMapper) / pageSize)
				: (findAllRoute(cid, conditionMapper) / pageSize) + 1);
		return retPage;
	}
}
