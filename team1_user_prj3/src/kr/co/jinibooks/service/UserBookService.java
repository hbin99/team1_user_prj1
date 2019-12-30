package kr.co.jinibooks.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.jinibooks.dao.UserBookDAO;
import kr.co.jinibooks.domain.MainNoticeDomain;
import kr.co.jinibooks.domain.QuestionListDomain;
import kr.co.jinibooks.domain.cate_best_bookDomain;
import kr.co.jinibooks.domain.mainBookDomain;
import kr.co.jinibooks.domain.newBookDomain;

public class UserBookService {
	
	//////���� ����////////////////////////////////////
	/**
	 * ����- ��õ ����1
	 * @return
	 */
	public List<mainBookDomain> searchRecomendBook1(){
		List<mainBookDomain> recomendBookList1=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			recomendBookList1=ubDao.selectRecomendBook1();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return recomendBookList1;
	}//searchRecomendBook1
	
	/**
	 * ����- ��õ ����2
	 * @return
	 */
	public List<mainBookDomain> searchRecomendBook2(){
		List<mainBookDomain> recomendBookList2=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			recomendBookList2=ubDao.selectRecomendBook2();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return recomendBookList2;
	}//searchRecomendBook1
	
	/**
	 * ����- ���� ����Ʈ top5
	 * @return
	 */
	public List<mainBookDomain> searchTotalBestBook(){
		List<mainBookDomain> totalBestBookList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			totalBestBookList=ubDao.selectTotalBestBook();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return totalBestBookList;
	}//searchTotalBestBook
	
	/**
	 * ����- �α� ����
	 * @return
	 */
	public List<mainBookDomain> searchPopularBook(){
		List<mainBookDomain> popularBookList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			popularBookList=ubDao.selectPopularBook();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return popularBookList;
	}//searchPopularBook
	
	/**
	 * ����- �Ű� ����
	 * @return
	 */
	public List<mainBookDomain> searchMainNewBook(){
		List<mainBookDomain> mainNewBookList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			mainNewBookList=ubDao.selectMainNewBook();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return mainNewBookList;
	}//searchMainNewBook
	
	/**
	 * ����- �������� ����Ʈ
	 * @return
	 */
	public List<MainNoticeDomain> searchNotice(){
		List<MainNoticeDomain> mainNoticeList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			mainNoticeList=ubDao.selectNotice();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return mainNoticeList;
	}//searchNotice
	
	/**
	 * ����- �����ϴ� ���� ����Ʈ
	 * @return
	 */
	public List<MainNoticeDomain> searchQuestion(){
		List<MainNoticeDomain> mainQuestionList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			mainQuestionList=ubDao.selectQuestion();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return mainQuestionList;
	}//searchQuestion
	
	
	//////���� ��////////////////////////////////////
	
	/**
	 * �̴��� �Ű� ������ ��ȸ
	 * @return
	 */
	public List<newBookDomain> searchNewBook(){
		List<newBookDomain> newBookList=null;
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			String intro=null;
			
			newBookList=ubDao.selectNewBook();
			
			for(int i=0; i<newBookList.size(); i++) {
				intro=newBookList.get(i).getIntro();
				if(intro.length()>301) {
					intro=intro.substring(0, 300)+"...";
					newBookList.get(i).setIntro(intro);
				}//end if
			}//end for
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return newBookList;
	}//searchNewBook
	
	/**
	 * ����Ʈ ������ �ְ� ��ȸ
	 * @return
	 */
	public List<cate_best_bookDomain> searchWeekBestBook(){
		List<cate_best_bookDomain> bestWeekBookList=null;
		
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			bestWeekBookList=ubDao.selectBestWeekBook();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return bestWeekBookList;
	}//searchWeekBestBook
	
	/**
	 * ����Ʈ ������ ���� ��ȸ
	 * @return
	 */
	public List<cate_best_bookDomain> searchMonthBestBook(){
		List<cate_best_bookDomain> bestMonthBookList=null;
		
		
		UserBookDAO ubDao=UserBookDAO.getInstance();
		try {
			bestMonthBookList=ubDao.selectBestMonthBook();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return bestMonthBookList;
	}//searchWeekBestBook

}//class
