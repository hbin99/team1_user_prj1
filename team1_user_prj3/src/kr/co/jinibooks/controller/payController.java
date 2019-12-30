package kr.co.jinibooks.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jinibooks.service.PayService;
import kr.co.jinibooks.vo.CartPayVO;
import kr.co.jinibooks.vo.CashPointVO;
import kr.co.jinibooks.vo.PayedCartDeleteVO;
import kr.co.jinibooks.vo.paymentVO;

@Controller
public class payController {
	String cartVal="";
	
	// ���� â �̵�
	@RequestMapping(value = "book_pay.do", method = { GET, POST })
	public String payBookForm(String cartValue, String id, CartPayVO cartData, Model model) {
		PayService ps = new PayService();
		cartVal=cartValue;
		System.out.println(cartData+"controller");
		//����â���� ������ ����
		 model.addAttribute("payBook", ps.searchPayBook(cartData));
		 System.out.println("------"+cartData);
		//�Ѿ�� ����â�� �������� �� �ݾ׼���
		 model.addAttribute("total", ps.totalPrice(cartData));
		 //������� ���� ĳ�ÿ� ���� ����Ʈ ��ȸ
		 model.addAttribute("cashPoint", ps.selectCashPoint(id));


		return "book_pay";
	}// payBookForm

	// ���� -> ���� �Ϸ�
	@RequestMapping(value = "book_paySuccess.do", method = POST)
	public String payBookProcess(CashPointVO cpvo, paymentVO pv, PayedCartDeleteVO pcdvo) {
		PayService ps = new PayService();
		System.out.println(pv);
		// ���� (����,�뿩) ���̺� �߰�
		ps.addPay(pv);
		// ���� ĳ�ÿ� ����Ʈ�� ȸ�� ���̺� ����
		ps.modifyCashPoint(cpvo);
		
		if(cartVal!=null) {
			// ��ٱ��Ͽ� �ִ� ������ �����Ǹ� ��ٱ��� ���̺� ����
			ps.deletePayedCart(pcdvo);
		}//end if
	
		
		return "book_paySuccess";
	}// class

}// class
