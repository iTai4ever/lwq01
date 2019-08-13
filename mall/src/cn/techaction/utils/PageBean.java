package cn.techaction.utils;

import java.util.List;

public class PageBean<T> {
	
	//必须已知的数据
	private int pageNum;   	//当前页
	private int pageSize;	//每页显示的数据条数
	private int totalRecord;	//总的记录数
	
	
	private int totalPage;	//��ҳ��
	private int startIndex; //��ʼ����
	
	private List<T> data;	//ÿҳҪչʾ������
	
	//Ҫ��ʾ�ķ�ҳ����
	private int prePage ;
	private int nextPage;
	
	public PageBean(int pageNum, int pageSize, int totalRecord) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		
		//��ҳ��
		if(totalRecord%pageSize==0) {
			this.totalPage = totalRecord/pageSize;
		}else {
			this.totalPage = totalRecord/pageSize+1;
		}
		//��ʼ����
		this.startIndex = (pageNum-1)*pageSize;
		//��һҳ
		this.prePage=this.pageNum-1;
		this.nextPage=this.pageNum+1;
		if(this.prePage<=0) {
			this.prePage=this.pageNum;
		}
		if(this.nextPage>this.totalPage) {
			this.nextPage=this.totalPage;
		}
	}

	
	
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

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
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
}


