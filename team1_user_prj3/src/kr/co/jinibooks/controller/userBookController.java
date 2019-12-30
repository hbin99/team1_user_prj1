package kr.co.jinibooks.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jinibooks.domain.SearchBookListDomain;
import kr.co.jinibooks.domain.SearchNewBookDomain;
import kr.co.jinibooks.domain.SearchRandomBookDomain;
import kr.co.jinibooks.domain.SelectCateBookDomain;
import kr.co.jinibooks.service.SearchBookPageService;
import kr.co.jinibooks.service.SearchBookService;
import kr.co.jinibooks.service.UserBookService;
import kr.co.jinibooks.vo.SearchBookPageVO;
import kr.co.jinibooks.vo.indexListVO;

@Controller
public class userBookController {
	
	//���� ����//////////////////////////////////////////////////////////////////////////////////////////////////
	
	//����� ����ȭ��
	@RequestMapping(value = "home.do", method = GET)
	public String userMain(@RequestParam(required = false, defaultValue = "�濵/����")String keyword, Model model) {
		UserBookService ubs=new UserBookService();
		
		
		//��õ ����1
		model.addAttribute("mainRecomend1", ubs.searchRecomendBook1());
		//��õ ����2
		model.addAttribute("mainRecomend2", ubs.searchRecomendBook2());
		//���� ����Ʈ top5
		model.addAttribute("mainTotalBest", ubs.searchTotalBestBook());
		//Ű���庰 ����
		model.addAttribute("mainPopular", ubs.searchPopularBook());
		//�Ű� ����
		model.addAttribute("mainNew", ubs.searchMainNewBook());
		//�������� ����Ʈ
		model.addAttribute("mainNotice", ubs.searchNotice());
		//�����ϴ� ���� ����Ʈ
		model.addAttribute("mainQuestion", ubs.searchQuestion());
		
		return "home";
	}//userMain
	
	//�̴��� �Ű�///??
	@RequestMapping(value="book_newList.do", method=GET)
	public String mainNewBook(Model model) {
		UserBookService ubs=new UserBookService();
		
		model.addAttribute("newbookData",ubs.searchNewBook());
		return "main_newBook";
	}//mainNewBook
	
	//����Ʈ
	@RequestMapping(value="book_bestList.do", method=GET)
	public String mainBestBook(Model model) {
		UserBookService ubs=new UserBookService();
		
		model.addAttribute("bestWeekbookData", ubs.searchWeekBestBook());
		model.addAttribute("bestMonthbookData", ubs.searchMonthBestBook());
		return "main_bestBook";
	}//mainBestBook
	
	//���� ��//////////////////////////////////////////////////////////////////////////////////////////////////
	
	//���� ����//////////////////////////////////////////////////////////////////////////////////////////////////
	
	//ī�װ�
	@RequestMapping(value = "book_categoryList.do", method = GET) 
	public String bookCateSearchProcess(Model model, String cate) {
		SearchBookService sbs= new SearchBookService();
		List<SelectCateBookDomain> cateList= sbs.selectCateList(cate);
		List<SelectCateBookDomain> cateNewList= sbs.selectCateNew(cate);
		model.addAttribute("cateBestList",cateList);
		model.addAttribute("cateNewList",cateNewList);
		
		return "main_categoryBook";
	}//bookCateSearchProcess
	
	/*
	 * //�˻� ��ȸ (å ��ȸ)
	 * 
	 * @RequestMapping(value ="book_searchList.do", method = GET) public String
	 * searchBookProcess(String keyword,Model model, SearchBookVO sbVO) {
	 * SearchBookService sbs= new SearchBookService(); List<SearchBookListDomain>
	 * bookList=sbs.searchBookList(sbVO); List<SearchNewBookDomain>
	 * newRangeList=sbs.selectNewRange(keyword);
	 * List<SearchBookListDomain>ratingRangeList=sbs.selectRatingRange(keyword);
	 * model.addAttribute("searchBookList",bookList);
	 * model.addAttribute("newRangeList",newRangeList);
	 * model.addAttribute("ratingRangeList",ratingRangeList); return
	 * "main_searchBook"; }//bookCateSearchProcess
	 */	
	//å �� ����//����� å ��ȸ // ����� ���� ��ȸ  2019-12-07 ///////////////-
	@RequestMapping(value = "book_detail.do", method = GET)
	public String bookDetailProcess(String code, Model model, String cateCode) {
		SearchBookService sbs=new SearchBookService();
		List<SearchRandomBookDomain> randomList1=sbs.randomBookList1(cateCode);
		List<SearchRandomBookDomain> randomList2=sbs.randomBookList2(cateCode);
		model.addAttribute("detailBook",sbs.selectDetailBook(code));
		model.addAttribute("randomList1",randomList1);
		model.addAttribute("randomList2",randomList2);
		System.out.println("cateCode"+cateCode);
		return "book_detail";
	}//bookCateSearchProcess
	
////////////////////////////////////////////////////
	@RequestMapping(value="book_searchList.do",method = GET )
	public String searchList(SearchBookPageVO sbVO,@RequestParam(required = false, defaultValue = "1") String current_page,String url, String keyword,String pageView,Model model) {
		SearchBookPageService bls= new SearchBookPageService();
		SearchBookService sbs= new SearchBookService();
		
		sbVO.setCurrentPag(Integer.parseInt(current_page));
		int totalCount=bls.totalCount(sbVO);
		int pageScale=bls.pageScale(); 
		int totalPage=bls.totalPage(pageScale, totalCount);
		int startNum=bls.startNum(pageScale, sbVO.getCurrentPag());
		int endNum=bls.endNum(pageScale, startNum);
		sbVO.setStartNum(startNum);
		sbVO.setEndNum(endNum);
		System.out.println("-----------------"+ pageView);
		indexListVO ilVO= new indexListVO(sbVO.getCurrentPag(),totalPage,url,pageView,keyword);//////
		
		
		List<SearchBookListDomain> bookList=sbs.searchBookList(sbVO);
		//������ ���� �������� �α��, �ֽż�, ���������� ���������..
		List<SearchNewBookDomain> newRangeList=sbs.selectNewRange(sbVO);
		List<SearchNewBookDomain> nameRangeList=sbs.selectNameRange(sbVO);
		List<SearchBookListDomain>ratingRangeList=sbs.selectRatingRange(sbVO);
		
		String indexList=bls.indexList(ilVO);

		model.addAttribute("indexList", indexList);
		
		model.addAttribute("searchBookList",bookList);
		model.addAttribute("newRangeList",newRangeList);
		model.addAttribute("nameRangeList",nameRangeList);
		model.addAttribute("ratingRangeList",ratingRangeList);
		
		return "main_searchBook";
	}//searchList
	//���� ��//////////////////////////////////////////////////////////////////////////////////////////////////

}
