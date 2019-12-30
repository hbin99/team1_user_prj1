package kr.co.jinibooks.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.jinibooks.dao.MypageDAO;
import kr.co.jinibooks.domain.BookDomain;

public class MypageService {

	public List<BookDomain> searchPayBookList_5(String id){
		List<BookDomain> list = null;
		
		MypageDAO mDAO = MypageDAO.getInstance();
		try {
			list = mDAO.selectpayBookList_5(id);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setCategory(cateCodeToEnglish(list.get(i).getCategory()));
			}//end for
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return list;
	}//searchPayBookList_5
	
	public List<BookDomain> searchPayBookList_All(String id){
		List<BookDomain> list = null;
		
		MypageDAO mDAO = MypageDAO.getInstance();
		try {
			list = mDAO.selectpayBookList_All(id);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return list;
	}//selectpayBookList_All
	
	public String cateCodeToEnglish(String cateCode) {
		String cateEnglish = null;
		switch (cateCode) {
			case "BC_000001": cateEnglish = "novel"; break;
			case "BC_000002": cateEnglish = "business_economy"; break;
			case "BC_000003": cateEnglish = "humanities"; break;
			case "BC_000004": cateEnglish = "self-improvement"; break;
			case "BC_000005": cateEnglish = "essay_poem"; break;
			case "BC_000006": cateEnglish = "travle"; break;
			case "BC_000007": cateEnglish = "science_it"; break;
			case "BC_000008": cateEnglish = "health_diet"; break;
			case "BC_000009": cateEnglish = "comic"; break;
	    }//end switch case
	    //System.out.println("ī�װ� �ڵ�: "+cateCode);
	    //System.out.println("ī�װ� ��: "+cateEnglish);
		System.out.println(cateEnglish);
		
		return cateEnglish;
	}//cateCodeToEnglish
	
	public String cateCodeToKorean(String cateCode) {
		String cateKorean = null;
		switch (cateCode) {
			case "BC_000001": cateKorean = "�Ҽ�"; break;
			case "BC_000002": cateKorean = "�濵/����"; break;
			case "BC_000003": cateKorean = "�ι�/��ȸ/����"; break;
			case "BC_000004": cateKorean = "�ڱ���"; break;
			case "BC_000005": cateKorean = "������/��"; break;
			case "BC_000006": cateKorean = "����"; break;
			case "BC_000007": cateKorean = "����/��ǻ��/IT"; break;
			case "BC_000008": cateKorean = "�ǰ�/���̾�Ʈ"; break;
			case "BC_000009": cateKorean = "��ȭ"; break;
	    }//end switch case
		//System.out.println("ī�װ� �ڵ�: "+cateCode);
	    //System.out.println("ī�װ� ��: "+cateKorean);
		
		return cateKorean;
	}//cateCodeToKorean
	
}
