package kr.co.jinibooks.vo;

public class SearchRangeVO {

	private int startNum, endNum ;

	
	public SearchRangeVO() {

	}

	public SearchRangeVO(int startNum, int endNum) {
		this.startNum = startNum;
		this.endNum = endNum;
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
	
	
} // class
