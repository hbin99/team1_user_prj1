package kr.co.jinibooks.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.jinibooks.domain.CashPointDomain;
import kr.co.jinibooks.domain.SelectPayDomain;
import kr.co.jinibooks.vo.CartPayVO;
import kr.co.jinibooks.vo.CashPointVO;
import kr.co.jinibooks.vo.PayedCartDeleteVO;
import kr.co.jinibooks.vo.PayedCartDeleteVO2;
import kr.co.jinibooks.vo.paymentVO;
import kr.co.jinibooks.vo.paymentVO2;

public class PayDAO {
	
	private static PayDAO pDao;
	private static SqlSessionFactory ssf;
	
	private PayDAO() {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}
	
	public static PayDAO getInstance() {
		if(pDao==null) {
			pDao=new PayDAO();
		}//end if
		return pDao;
	}//getInstance
	
	public SqlSessionFactory getSessionFactory() throws IOException{
		if(ssf==null) {
			Reader reader=null;
			try {
				//1. ������ xml�� ��Ʈ������ ����
				reader=Resources.getResourceAsReader("kr/co/jinibooks/dao/mybatis-config.xml");
				//2. SqlSessionFactoryBuilder ����
				SqlSessionFactoryBuilder ssfb=new SqlSessionFactoryBuilder();
				//3. SqlSessionFactory ���
				ssf=ssfb.build(reader);
			}finally {
				if(reader!=null) {reader.close();}//end if
			}//end finally
		}//end if
		return ssf;
	}//getSessionFactory
	
	////////////////////////////////////////////////////////////////////
	
	/**
	 * �����ڵ带 ������ ���������� ���� â�� ����ִ� ��
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public List<SelectPayDomain> selectPayBook(CartPayVO cartData) throws SQLException{
		List<SelectPayDomain> selectPayList=new ArrayList<SelectPayDomain>();
		SelectPayDomain spd=null;
		
		String[]  codeTemp=cartData.getCode();
		String[]  buyTemp=cartData.getBuy();
		System.out.println(Arrays.toString(buyTemp));
		String query="";
		String code="";
		try {
		SqlSession ss=getSessionFactory().openSession();
		
		for(int i=0; i<buyTemp.length;i++) {
			query="selectSalePayBook";
			code=codeTemp[i];
			if("r".equals(buyTemp[i])) {
				query="selectRentalPayBook";
			}//end if
			spd=ss.selectOne(query, code);
			System.out.println(spd.getBook_code());
			spd.setBuy(buyTemp[i]);
			selectPayList.add(spd);
		}//end for
		ss.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		return selectPayList;
	}//selectPayBook
	
	/**
	 * ������� ĳ�ÿ� ����Ʈ�� ��ȸ�ϴ� ��
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CashPointDomain selectCashPoint(String id) throws SQLException{
		CashPointDomain selectcpd=null;
		
		try {
			SqlSession ss=getSessionFactory().openSession();
			selectcpd=ss.selectOne("selectCashPoint",id);
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		return selectcpd;
	}//selectCashPoint
	
	/**
	 * �����Ǿ��� �� DB ���̺� �߰��ϴ� ��
	 * @param pv
	 * @return
	 * @throws SQLException
	 */
	public void insertPay(paymentVO pv)throws SQLException{
		
		String[]  buyTemp=pv.getBuyClass();
		String[]  codeTemp=pv.getBookCode();
		String id=pv.getId();
		int useCash=pv.getUseCash();
		int usePoint=pv.getUsePoint();
		String payMethod=pv.getPayMethod();
		String payDay=pv.getPayDay();
		
		String query="";
		
		try {
			SqlSession ss=getSessionFactory().openSession();
			
			for(int i=0; i<buyTemp.length; i++) {
				paymentVO2 pv2=new paymentVO2(useCash,usePoint,id,payMethod,payDay,codeTemp[i],buyTemp[i]);
				
				query="insertPurchase";
				if("r".equals(buyTemp[i])) {
					query="insertRental";
				}//end if
				ss.insert(query,pv2);
			}//end for
			ss.commit();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
	}//insertPay
	
	
	/**
	 * ������, ���� ĳ�ÿ� ����Ʈ�� ȸ�����̺� ����
	 * @param cpvo
	 * @throws SQLException
	 */
	public void updateCashPoint(CashPointVO cpvo)throws SQLException{
		
		try {
			SqlSession ss=getSessionFactory().openSession();
			
			ss.update("updateCashPoint", cpvo);
			ss.commit();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
	}//updateCashPoint
	
	/**
	 * ��ٱ��Ͽ��� ���õ� ������ �������� �� ������ ������ ��ٱ��Ͽ� ����
	 * @throws SQLException
	 */
	public void deletePayedCart(PayedCartDeleteVO pcdvo)throws SQLException{
		
		String[] codeTemp=pcdvo.getBookCode();
		String id=pcdvo.getId();
		
		
		try {
			SqlSession ss=getSessionFactory().openSession();
			
			for(int i=0; i<codeTemp.length; i++) {
				PayedCartDeleteVO2 pcdvo2=new PayedCartDeleteVO2(id, codeTemp[i]);
				ss.delete("deletePayedCart", pcdvo2);
			}//end for
			ss.commit();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//deletePayedCart

}//class
