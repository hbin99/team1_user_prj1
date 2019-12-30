package kr.co.jinibooks.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.jinibooks.dao.NoticeDAO;
import kr.co.jinibooks.domain.NoticeListDomain;
import kr.co.jinibooks.vo.BoardIndexListVO;
import kr.co.jinibooks.vo.NoticeSearchVO;
import kr.co.jinibooks.vo.NoticeVO;

/**
 * XxxAction�� ��ü�� �����ϰ� ȣ���Ͽ� ��������(Business Logic)�� �����ϰ� Model���� XxxDAO�� ����Ͽ� DB
 * ���� ����ϴ� ��.
 * 
 * @author owner
 */
public class NoticeBoardListService {

	/**
	 * �˻����� �޾Ƽ� �˻� ���� ���ٸ� ��ü���� ������ ��ȸ�ϰ�, �˻� ���� �ִٸ� �˻����� �ش��ϴ� ���� ������ ��ȸ�ϴ� ��.
	 * 
	 * @param sv
	 * @return
	 */
	public int totalCount(NoticeSearchVO nsVO) {
		int totalCnt = 0;
		NoticeDAO nDAO = NoticeDAO.getInstance();
		try {
			totalCnt = nDAO.selectTotalCount(nsVO);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
		return totalCnt;
	}// totalCount

	/**
	 * �� ȭ�鿡 ������ �Խù��� ��
	 * 
	 * @return
	 */
	public int pageScale() {
		int pageScale = 10;
		return pageScale;
	}// pageScale

	/**
	 * �� �������� �����ֱ� ���� �ʿ��� ������ ��
	 * 
	 * @param pageScale  ��ȭ�鿡 ������ �Խù��� ��
	 * @param totalCount �ѰԽù��� ��
	 * @return
	 */
	public int totalPage(int pageScale, int totalCount) {
		int totalPage = totalCount / pageScale;// ��� �Խù��� �����ֱ����� �� ������ ��

		if (totalCount % pageScale != 0) {// pageScale�� �� �������� ������ ������ �Խù��� �����ֱ����� ���� �� �ʿ��ϴ�.
			totalPage++;
		} // end if
			// int totalPage=(int)Math.ceil( (double)totalCount / pageScale);

		return totalPage;
	}// totalPage

	/**
	 * ������ ���۹�ȣ ���ϴ� ��
	 * 
	 * @param pageScale
	 * @param currentPage
	 * @return
	 */
	public int startNum(int pageScale, int currentPage) {
		int startNum = currentPage * pageScale - pageScale + 1;

		return startNum;
	}// startNum

	/**
	 * �������� ����ȣ ���ϴ� ��
	 * 
	 * @param pageScale
	 * @param startNum
	 * @return
	 */
	public int endNum(int pageScale, int startNum) {
		int endNum = startNum + pageScale - 1;
		return endNum;
	}// endNum

	/**
	 * �˻����� ���۹�ȣ, ����ȣ�� �ش��ϴ� �Խù��� ����Ʈ ��ȸ
	 * 
	 * @param srVO ���۹�ȣ, ����ȣ�� ���� VO
	 * @param sVO  Ű����, �÷���, ���������� ��ȣ���� VO
	 * @return
	 */
	public List<NoticeListDomain> searchList(NoticeSearchVO sVO) {
		List<NoticeListDomain> list = null;
		
	    NoticeDAO s_dao3=NoticeDAO.getInstance(); 
	    try{
		    list=s_dao3.selectBoardList(sVO);
//		    if(list !=null) {
//		    	String temp="";
//		    	for (int i = 0; i < list.size(); i++) {
//					temp=list.get(i).getNum().substring(2) ;
//					list.get(i).setNum(temp);
//				}//end if
//		    }//end if 
		} catch (SQLException sqle) {
		    sqle.printStackTrace(); 
		}//end catch

		return list;
	}// searchList

	// ���� �Խ����� ������ �ε��� ����
	public String indexList(BoardIndexListVO ilVO,NoticeSearchVO sVO) {
		int pagenumber; // ȭ�鿡 ������ ������ �ε��� ��
		int startpage; // ȭ�鿡 ������ ���������� ��ȣ
		int endpage; // ȭ�鿡 ������ ������������ ��ȣ
		int curpage; // �̵��ϰ��� �ϴ� ������ ��ȣ

		String strList = ""; // ���ϵ� ������ �ε��� ����Ʈ

		pagenumber = 10; // �� ȭ���� ������ �ε��� ��

		// ���� ��������ȣ ���ϱ�
		startpage = ((ilVO.getCurrentPage() - 1) / pagenumber) * pagenumber + 1;

		// ������ ��������ȣ ���ϱ�
		endpage = (((startpage - 1) + pagenumber) / pagenumber) * pagenumber;

		// �� ������ ���� ���� ������������ ��ȣ���� �������

		// �� ������ ���� ������������ ��ȣ�� ��
		
		String field=sVO.getField()!=null?sVO.getField():"";
		String keyword=sVO.getKeyword()!=null?sVO.getKeyword():"";
		String params="&field="+field+"&keyword="+keyword;

		if (ilVO.getTotalPage() <= endpage) {
			endpage = ilVO.getTotalPage();
		} // end if

		// ù��° ������ �ε��� ȭ���� �ƴѰ��
		if (ilVO.getCurrentPage() > pagenumber) {
			curpage = startpage - 1; // ���������� ��ȣ���� 1 ���� �������� �̵�
			strList = strList + "<li class=\"page-item\"><a class=\"page-link\" href=" + ilVO.getUrl()
					+ "?current_page=" + curpage +params+ " aria-label=\"Previous\">\r\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span></a></li>";
		} else {
			strList = strList + "<li class=\"page-item\">\r\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Previous\">\r\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span>\r\n" + "      </a>\r\n" + "    </li>";
		} // end else

		// strList = strList + " ..;

		// ���������� ��ȣ���� ������������ ��ȣ���� ȭ�鿡 ǥ��
		curpage = startpage;

		while (curpage <= endpage) {
			if (curpage == ilVO.getCurrentPage()) {
				strList = strList + "<li class=\"page-item\"><a class=\"page-link\" title='����������'>"
						+ ilVO.getCurrentPage() + "</a></li>";
			} else {
				strList = strList + "<li class=\"page-item\"><a class=\"page-link\" href=" + ilVO.getUrl()
						+ "?current_page=" + curpage+ params + ">" + curpage + "</a></li>";
			} // end else

			curpage++;
		} // end while

		// strList = strList + " ... ";

		// �ڿ� �������� �� �ִ°��
		if (ilVO.getTotalPage() > endpage) {
			curpage = endpage + 1;
			strList = strList + "<li class=\"page-item\">\r\n" + "      <a class=\"page-link\" href=" + ilVO.getUrl()
					+ "?current_page=" + curpage+params + " aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span></a></li>";
		} else {
			strList = strList + "<li class=\"page-item\">\r\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\r\n" + "      </a>\r\n" + "    </li>";
		} // end else

		return strList;
	}// indexList
	
	public List<NoticeListDomain> multiColumnMultiRow(){
		List<NoticeListDomain> list=null;
		
		NoticeDAO sDao3=NoticeDAO.getInstance();
		list=sDao3.multiColumnMultiRow();
		
		return list;
	}//multiColumnMultiRow
	
	public NoticeListDomain searchNoticeDetail(String num) {
		NoticeListDomain bld=null;
		NoticeDAO nDAO= NoticeDAO.getInstance();
		try {
			bld=nDAO.selectDetailNotice(num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bld;
	}//searchNoticeDetail
	
	public boolean insertnotice(NoticeVO ceiVO){
		boolean flag = false;
		
		//DAO�� ����Ͽ� 
		NoticeDAO ceDAO = NoticeDAO.getInstance();
		try {
			//�������� �����ϰ� ����� ��´�.
			int cnt = ceDAO.insertNotice(ceiVO);
			if(cnt == 1) {
				flag = true;
			}//end if
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return flag;
	}//addCpEmp
	

	
	public boolean deletenotice(NoticeVO ceiVO){
		boolean flag = false;
		
		//DAO�� ����Ͽ� 
		NoticeDAO ceDAO = NoticeDAO.getInstance();
		try {
			//�������� �����ϰ� ����� ��´�.
			int cnt = ceDAO.deleteNotice(ceiVO);
			if(cnt == 1) {
				flag = true;
			}//end if
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return flag;
	}//addCpEmp

}// class
