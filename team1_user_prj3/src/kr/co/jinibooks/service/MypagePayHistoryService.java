package kr.co.jinibooks.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.jinibooks.dao.NoticeDAO;
import kr.co.jinibooks.dao.myPagePayHistoryDAO;
import kr.co.jinibooks.domain.MypagePayhistoryDomain;
import kr.co.jinibooks.vo.BoardIndexListVO;
import kr.co.jinibooks.vo.NoticeSearchVO;
import kr.co.jinibooks.vo.PayhistoryIndexListVO;
import kr.co.jinibooks.vo.pageVO;

/**
 * ���� ����
 * @author brty7
 */
public class MypagePayHistoryService {
	
	public List<MypagePayhistoryDomain> searchpayHistory(String id){
		List<MypagePayhistoryDomain> payhistoryList = null;
		
		myPagePayHistoryDAO mpphDao=myPagePayHistoryDAO.getInstance();
		try {
			payhistoryList=mpphDao.selectpayHistory(id);
			System.out.println(payhistoryList+"�ФѤФѤФ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return payhistoryList;
	}//searchpayHistory
	
	/**
	 * �˻����� �޾Ƽ� �˻� ���� ���ٸ� ��ü���� ������ ��ȸ�ϰ�, �˻� ���� �ִٸ� �˻����� �ش��ϴ� ���� ������ ��ȸ�ϴ� ��.
	 * 
	 * @param sv
	 * @return
	 */
	public int totalCount(String id) {
		int totalCnt = 0;
		myPagePayHistoryDAO mpphDao = myPagePayHistoryDAO.getInstance();
		try {
			totalCnt = mpphDao.selectTotalpage(id);
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
		int pageScale = 5;
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

	// ���� �Խ����� ������ �ε��� ����
	public String indexList(PayhistoryIndexListVO pilVO,pageVO pVO) {
		int pagenumber; // ȭ�鿡 ������ ������ �ε��� ��
		int startpage; // ȭ�鿡 ������ ���������� ��ȣ
		int endpage; // ȭ�鿡 ������ ������������ ��ȣ
		int curpage; // �̵��ϰ��� �ϴ� ������ ��ȣ

		String strList = ""; // ���ϵ� ������ �ε��� ����Ʈ

		pagenumber = 10; // �� ȭ���� ������ �ε��� ��

		// ���� ��������ȣ ���ϱ�
		startpage = ((pilVO.getCurrentPage() - 1) / pagenumber) * pagenumber + 1;

		// ������ ��������ȣ ���ϱ�
		endpage = (((startpage - 1) + pagenumber) / pagenumber) * pagenumber;

		// �� ������ ���� ���� ������������ ��ȣ���� �������

		// �� ������ ���� ������������ ��ȣ�� ��
		
		if (pilVO.getTotalPage() <= endpage) {
			endpage = pilVO.getTotalPage();
		} // end if

		// ù��° ������ �ε��� ȭ���� �ƴѰ��
		if (pilVO.getCurrentPage() > pagenumber) {
			curpage = startpage - 1; // ���������� ��ȣ���� 1 ���� �������� �̵�
			strList = strList + "<li class=\"page-item\"><a class=\"page-link\" href=" + pilVO.getUrl()
					+ "?current_page=" + curpage + " aria-label=\"Previous\">\r\n"
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
			if (curpage == pilVO.getCurrentPage()) {
				strList = strList + "<li class=\"page-item\"><a class=\"page-link\" title='����������'>"
						+ pilVO.getCurrentPage() + "</a></li>";
			} else {
				strList = strList + "<li class=\"page-item\"><a class=\"page-link\" href=" + pilVO.getUrl()
						+ "?current_page=" + curpage+  ">" + curpage + "</a></li>";
			} // end else

			curpage++;
		} // end while

		// strList = strList + " ... ";

		// �ڿ� �������� �� �ִ°��
		if (pilVO.getTotalPage() > endpage) {
			curpage = endpage + 1;
			strList = strList + "<li class=\"page-item\">\r\n" + "      <a class=\"page-link\" href=" + pilVO.getUrl()
					+ "?current_page=" + curpage+ " aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span></a></li>";
		} else {
			strList = strList + "<li class=\"page-item\">\r\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\r\n" + "      </a>\r\n" + "    </li>";
		} // end else

		return strList;
	}// indexList
	
	
}
