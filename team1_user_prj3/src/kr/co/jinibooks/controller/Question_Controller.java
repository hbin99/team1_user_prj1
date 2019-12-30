package kr.co.jinibooks.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.co.jinibooks.domain.QuestionListDomain;
import kr.co.jinibooks.service.QuestionListService;
import kr.co.jinibooks.vo.BoardIndexListVO;
import kr.co.jinibooks.vo.QuestionSearchVO;
import kr.co.jinibooks.vo.QuestionVO;

@SessionAttributes("id")
@Controller
public class Question_Controller {

	/**
	 * �α��� ���� Ȯ��
	 * @return
	 */
	private String ChkLoginSession(HttpSession session) {
		String destination = null;
		
		if((String)session.getAttribute("user_id") == null) {
			destination = "redirect:/member/login.do";
		}//end if
		return destination;
	}//ChkLoginSession
	
	@RequestMapping(value = "question.do", method = { GET, POST })
	public String searchList(QuestionSearchVO sVO,
			@RequestParam(required = false, defaultValue = "1") String current_page, HttpSession session, Model model) {
		
		String destination = ChkLoginSession(session);
		if(destination == null) {
			
			// indexList���� �����ϴ� url�� current_page�� ��ȸ�� ���Ǵ� sVO�� currentPage�� �ٸ�����
			// current_page�� parameter�� �ް� sVO�� set�Ͽ� �ݴϴ�.
			sVO.setCurrentPage(Integer.parseInt(current_page));

			String id =(String)session.getAttribute("user_id");
			sVO.setId(id);
			// ��2������ ��ü�� �ڽ��� ��������
			// �����ӿ�ũ������ ������ ��ũ�� ����� �ش�.
			// �������� VO�� null���� Ȯ���� �ʿ䰡 ����.
			
			QuestionListService bls = new QuestionListService();
			int totalCount = bls.totalCount(sVO);
	
			int pageScale = bls.pageScale();
			int totalPage = bls.totalPage(pageScale, totalCount);
			int startNum = bls.startNum(pageScale, sVO.getCurrentPage());
			int endNum = bls.endNum(pageScale, startNum);
	
			// ���� ������ ��ȸ�� ���� �� �ְ� VO�� �����մϴ�.
			sVO.setStartNum(startNum);
			sVO.setEndNum(endNum);
	
			List<QuestionListDomain> boardList = bls.searchList(sVO);
	
			BoardIndexListVO ilVO = new BoardIndexListVO(sVO.getCurrentPage(), totalPage, "question.do");
			String indexList = bls.indexList(ilVO, sVO);
	
			model.addAttribute("boardList", boardList);// �Խù� ����Ʈ
			model.addAttribute("indexList", indexList);// �ε��� ����Ʈ
			
			destination = "board/user_question";
	    }//end if
	    return destination;
//		return "day1202/list";
	}// searchList

	@RequestMapping(value = "add_form.do", method = { GET, POST })
	public String addEmpForm(@RequestParam(required = false, defaultValue = "i_000000") String num, HttpSession session, Model model) {
		String destination = ChkLoginSession(session);
		if(destination == null) {
			QuestionListService bls = new QuestionListService();
			QuestionListDomain bld = bls.searchNoticeDetail(num);
			model.addAttribute("question_detail", bld);
			//return "board/user_question_write";
		    destination = "board/user_question_write";
		}//end if
        return destination;
	}// addEmpForm

	@RequestMapping(value = "detail2.do", method = GET)
	public String noticedetail(@RequestParam(required = false, defaultValue = "i_000000") String num, HttpSession session, Model model) {
		String destination = ChkLoginSession(session);
		if (destination == null) {
			QuestionListService bls = new QuestionListService();
			QuestionListDomain bld = bls.searchNoticeDetail(num);
			model.addAttribute("question_detail", bld);
			// return "board/user_question_detail";
			destination = "board/user_question_detail";
		} // end if
		return destination;
	}// searchEmpData

	@RequestMapping(value = "write_process.do", method = POST)
	public String InsertNoticeProcess(QuestionVO qVO, Model model, HttpSession session) {
		String destination = ChkLoginSession(session);
		if (destination == null) {
			
			String id =(String)session.getAttribute("user_id");
			qVO.setId(id);
			//System.out.println(id);
			
			//System.out.println(qVO);
			// ����ó�� Ŭ������ ��üȭ
			QuestionListService ces = new QuestionListService();
			// ����ó��
			boolean flag = ces.insertnotice(qVO); // view�� ������ ����.
			model.addAttribute("insertFlag", flag);

			// return "board/user_question_success";
			destination = "board/user_question_success";
		} // end if
		return destination;
	}// addEmpProcess

}// class
