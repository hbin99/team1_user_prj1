package kr.co.jinibooks.vo;

public class NoticeSearchVO {
	
	private String field, keyword;
	private int currentPage, startNum, endNum;
	//�����ӿ�ũ������ �⺻�����ڿ� �����ִ� �����ڸ� ������ �ʿ䰡 �����ϴ�.
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	
}
