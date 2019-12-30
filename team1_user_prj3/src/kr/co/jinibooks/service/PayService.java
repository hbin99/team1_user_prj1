package kr.co.jinibooks.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.jinibooks.dao.PayDAO;
import kr.co.jinibooks.domain.CashPointDomain;
import kr.co.jinibooks.domain.SelectPayDomain;
import kr.co.jinibooks.vo.CartPayVO;
import kr.co.jinibooks.vo.CashPointVO;
import kr.co.jinibooks.vo.PayedCartDeleteVO;
import kr.co.jinibooks.vo.paymentVO;

public class PayService {
	
	/**
	 * ������ �������� ����â�� ��ȸ
	 * @param code
	 * @param buy
	 * @return
	 */
	public List<SelectPayDomain> searchPayBook(CartPayVO cartData){
		List<SelectPayDomain> payBookList=null;
		PayDAO pDao=PayDAO.getInstance();
		try {
			System.out.println(cartData+"service");
			payBookList=pDao.selectPayBook(cartData);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return payBookList;
	}//searchPayBook
	
	//����â�� ���� �ѱݾ� ����
	public int totalPrice(CartPayVO cartData) {
		List<SelectPayDomain> payList=null;
		
		PayDAO pDao=PayDAO.getInstance();
		int total=0;

		try {
			payList=pDao.selectPayBook(cartData);
			
			for(int i=0; i<payList.size();i++) {
				total+=payList.get(i).getPrice();
			}//end for
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		 
		 return total;
	}//totalPrice
	
	/**
	 * ������� ĳ�ÿ� ����Ʈ ��ȸ
	 * @param id
	 * @return
	 */
	public CashPointDomain selectCashPoint(String id) {
		CashPointDomain cpd=null;
		
		PayDAO pDao=PayDAO.getInstance();
		try {
			cpd=pDao.selectCashPoint(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return cpd;
	}//selectCashPoint
	
	/**
	 * �����Ǿ��� �� DB ���̺� �߰�
	 * @param pv
	 */
	public void addPay(paymentVO pv) {
		
		PayDAO pDao=PayDAO.getInstance();
		
		try {
			pDao.insertPay(pv);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
	}//addPay
	
	/**
	 * ���� ��, ���� ĳ�ÿ� ����Ʈ ����
	 * @param cpvo
	 */
	public void modifyCashPoint(CashPointVO cpvo) {
		
		PayDAO pDao=PayDAO.getInstance();
		
		try {
			pDao.updateCashPoint(cpvo);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//modifyCashPoint
	
	public void deletePayedCart(PayedCartDeleteVO pcdvo) {
		
		PayDAO pDao=PayDAO.getInstance();
		
		try {
			pDao.deletePayedCart(pcdvo);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//deletePayedCart

}//class
