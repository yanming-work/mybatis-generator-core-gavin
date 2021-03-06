package org.mybatis.generator.plugins.my;

import java.io.Serializable;
import java.util.List;

public class PageView<T> implements Serializable {

	    
	    
	  	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//当前页  
	    private int pageNum;  
	    //每页的数量  
	    private int pageSize;  
	    //当前页的数量  
	    private int size;  
	    //由于startRow和endRow不常用，这里说个具体的用法  
	    //可以在页面中"显示startRow到endRow 共size条数据"  
	  
	    //当前页面第一个元素在数据库中的行号  
	    private int startRow;  
	    //当前页面最后一个元素在数据库中的行号  
	    private int endRow;  
	    //总记录数  
	    private long total;  
	    //总页数  
	    private int pages;  
	    //结果集  
	    private List<T> list;  
	  
	    //第一页  
	    private int firstPage;
	    private int lastPage;
	    //前一页  
	    private int prePage; 
	    //后一页  
	    private int nextPage; 
	  
	    //是否为第一页  
	    private boolean isFirstPage = false;  
	    //是否为最后一页  
	    private boolean isLastPage = false;  
	    //是否有前一页  
	    private boolean hasPreviousPage = false;  
	    //是否有下一页  
	    private boolean hasNextPage = false;  
	    //导航页码数  
	    private int navigatePages;  
	    //所有导航页号  
	    private int[] navigatepageNums;
		public int getPageNum() {
			return pageNum;
		}
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public int getStartRow() {
			return startRow;
		}
		public void setStartRow(int startRow) {
			this.startRow = startRow;
		}
		public int getEndRow() {
			return endRow;
		}
		public void setEndRow(int endRow) {
			this.endRow = endRow;
		}
		public long getTotal() {
			return total;
		}
		public void setTotal(long total) {
			this.total = total;
		}
		public int getPages() {
			return pages;
		}
		public void setPages(int pages) {
			this.pages = pages;
		}
		public List<T> getList() {
			return list;
		}
		public void setList(List<T> list) {
			this.list = list;
		}
		public int getFirstPage() {
			return firstPage;
		}
		public void setFirstPage(int firstPage) {
			this.firstPage = firstPage;
		}
		public int getPrePage() {
			return prePage;
		}
		public void setPrePage(int prePage) {
			this.prePage = prePage;
		}
		public int getNextPage() {
			return nextPage;
		}
		public void setNextPage(int nextPage) {
			this.nextPage = nextPage;
		}
		public boolean getIsFirstPage() {
			return isFirstPage;
		}
		public void setIsFirstPage(boolean isFirstPage) {
			this.isFirstPage = isFirstPage;
		}
		public boolean getIsLastPage() {
			return isLastPage;
		}
		public void setIsLastPage(boolean isLastPage) {
			this.isLastPage = isLastPage;
		}
		public boolean getIsHasPreviousPage() {
			return hasPreviousPage;
		}
		public void setIsHasPreviousPage(boolean hasPreviousPage) {
			this.hasPreviousPage = hasPreviousPage;
		}
		public boolean getIsHasNextPage() {
			return hasNextPage;
		}
		public void setIsHasNextPage(boolean hasNextPage) {
			this.hasNextPage = hasNextPage;
		}
		public int getNavigatePages() {
			return navigatePages;
		}
		public void setNavigatePages(int navigatePages) {
			this.navigatePages = navigatePages;
		}
		public int[] getNavigatepageNums() {
			return navigatepageNums;
		}
		public void setNavigatepageNums(int[] navigatepageNums) {
			this.navigatepageNums = navigatepageNums;
		}
		public int getLastPage() {
			return lastPage;
		}
		public void setLastPage(int lastPage) {
			this.lastPage = lastPage;
		}  
	    
	    
	    
}
