package kr.co.jinibooks.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jinibooks.domain.NoticeListDomain;
import kr.co.jinibooks.service.NoticeBoardListService;
import kr.co.jinibooks.vo.BoardIndexListVO;
import kr.co.jinibooks.vo.NoticeSearchVO;
import kr.co.jinibooks.vo.NoticeVO;

@Controller
public class Notice_Controller {
	
	@RequestMapping(value="notice.do",method= {GET,POST})
	public String searchList(NoticeSearchVO nsVO, 
			@RequestParam(required = false, defaultValue = "1")String current_page, Model model) {
		//indexList���� �����ϴ� url�� current_page�� ��ȸ�� ���Ǵ� sVO�� currentPage�� �ٸ����� 
		//current_page�� parameter�� �ް� sVO�� set�Ͽ� �ݴϴ�.
		nsVO.setCurrentPage(Integer.parseInt(current_page));
		
		//��2������ ��ü�� �ڽ��� ��������
		//�����ӿ�ũ������ ������ ��ũ�� ����� �ش�.
		//�������� VO�� null���� Ȯ���� �ʿ䰡 ����.
		
		NoticeBoardListService bls = new NoticeBoardListService();
		int totalCount = bls.totalCount(nsVO);
		
		int pageScale = bls.pageScale();
		int totalPage = bls.totalPage(pageScale, totalCount);
		int startNum = bls.startNum(pageScale, nsVO.getCurrentPage());
		int endNum = bls.endNum(pageScale, startNum);
		
		
		//���� ������ ��ȸ�� ���� �� �ְ� VO�� �����մϴ�.
		nsVO.setStartNum(startNum);
		nsVO.setEndNum(endNum);
		
		List<NoticeListDomain> boardList = bls.searchList(nsVO);

		BoardIndexListVO ilVO = new BoardIndexListVO(nsVO.getCurrentPage(), totalPage, "notice.do");
		String indexList = bls.indexList(ilVO,nsVO);
		
		model.addAttribute("boardList", boardList);//�Խù� ����Ʈ
		model.addAttribute("indexList", indexList);//�ε��� ����Ʈ
		
		return "board/user_notice";
//		return "day1202/list";
	}//searchList
	
	@RequestMapping(value = "detail.do",method = GET)
	public String noticedetail(@RequestParam(required = false,defaultValue = "N_000000")String num,Model model) {
		
		NoticeBoardListService bls=new NoticeBoardListService();
		NoticeListDomain bld=bls.searchNoticeDetail(num);
		model.addAttribute("notice_detail",bld);
		return "board/user_notice_detail";
	}//searchEmpData
	
	@RequestMapping(value = "modified_process.do", method = POST)
	public String InsertNoticeProcess(NoticeVO ceiVO, Model model) {
		
		
		//����ó�� Ŭ������ ��üȭ
		NoticeBoardListService ces=new NoticeBoardListService();
		//����ó��
		boolean flag=ces.insertnotice(ceiVO);
		//view�� ������ ����.
		model.addAttribute("insertFlag", flag);
		
		return "board/admin_notice_success";
	}//addEmpProcess
	
	@RequestMapping(value = "faq.do", method = {GET,POST})
	public String Faq() {
		
		return  "board/user_notice_faq";
	}//addEmpForm
	
	@RequestMapping(value = "delete_process.do", method = POST)
	public String DeleteNoticeProcess(NoticeVO ceiVO, Model model) {
		
		
		//����ó�� Ŭ������ ��üȭ
		NoticeBoardListService ces=new NoticeBoardListService();
		//����ó��
		boolean flag=ces.deletenotice(ceiVO);
		//view�� ������ ����.
		model.addAttribute("deleteFlag", flag);
		
		return "board/admin_notice_success";
	}//addEmpProcess
	
}//class
