package kr.co.jinibooks.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.jinibooks.domain.MypagePayhistoryDomain;
import kr.co.jinibooks.vo.BoardIndexListVO;
import kr.co.jinibooks.vo.NoticeSearchVO;
import kr.co.jinibooks.vo.pageVO;

public class myPagePayHistoryDAO {
	
	private static myPagePayHistoryDAO mpphDAO;
	private static SqlSessionFactory ssf;
	
	private myPagePayHistoryDAO() {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();	
	}//CpEmpDAO
	
	public static myPagePayHistoryDAO getInstance() {
		if (mpphDAO == null) {
			mpphDAO = new myPagePayHistoryDAO();
		}//end if
		return mpphDAO;
	}//getInstance
	
	public SqlSessionFactory getSessionFactory() throws IOException {
		if(ssf == null) {
			Reader reader = null;
			try {
				//1. ������ xml�� ��Ʈ������ ����
				reader = Resources.getResourceAsReader("kr/co/jinibooks/dao/mybatis-config.xml");
				//2. SqlSessionFactoryBuilder ����
				SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
				//3. SqlSessionFactory ���
				ssf = ssfb.build(reader);
			} finally {
				if(reader != null) {reader.close();}//end if
			}//end finally
		}//end if
		return ssf;
	}//getSessionFactory
	
	public List<MypagePayhistoryDomain> selectpayHistory(String id) throws SQLException {
		List<MypagePayhistoryDomain> payHistorylist = null;
		
		try {
			SqlSession ss = getSessionFactory().openSession();
			payHistorylist = ss.selectList("payHistory",id);
			System.out.println(payHistorylist);
			ss.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return payHistorylist;
	}//selectpayHistory
	
	
	public int selectTotalpage(String id) throws SQLException {
		int cnt = 0;
		
		try {
			//Mybatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//���� ���� �� ��� �ޱ�
			cnt = ss.selectOne("selectTotalpage", id);
			//����
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//selectTotalCount
	

}
