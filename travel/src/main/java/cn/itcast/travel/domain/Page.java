package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页对象
 * @author shich
 */
public class Page {
	
	// 数据总数
	private int totalCount;
	// 总页码
	private int totalPage;
	// 当前页数据列表
	private List comtantList;
	// 当前页码
	private int currnetPageNum;
	// 每页显示的数据数目
	private int numPage;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List getComtantList() {
		return comtantList;
	}
	public void setComtantList(List comtantList) {
		this.comtantList = comtantList;
	}
	public int getCurrnetPageNum() {
		return currnetPageNum;
	}
	public void setCurrnetPageNum(int currnetPageNum) {
		this.currnetPageNum = currnetPageNum;
	}
	public int getNumPage() {
		return numPage;
	}
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	
	@Override
	public String toString() {
		return "Page [totalCount=" + totalCount + ", totalPage=" + totalPage + ", comtantList=" + comtantList
				+ ", currnetPageNum=" + currnetPageNum + ", numPage=" + numPage + "]";
	}
}
