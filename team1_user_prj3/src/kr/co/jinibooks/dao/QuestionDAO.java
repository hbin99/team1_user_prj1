package kr.co.jinibooks.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.jinibooks.domain.QuestionListDomain;
import kr.co.jinibooks.vo.QuestionSearchVO;
import kr.co.jinibooks.vo.QuestionVO;


public class QuestionDAO {
    
	private static QuestionDAO qDAO;
	private static SqlSessionFactory ssf;
	
	private QuestionDAO() {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();	
	}//SelectDAO
	
	public static QuestionDAO getInstance() {
		if (qDAO == null) {
			qDAO = new QuestionDAO();
		}//end if
		return qDAO;
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
	
	public int selectTotalCount(QuestionSearchVO sVO) throws SQLException {
		int cnt = 0;
		
		try {
			//Mybatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//���� ���� �� ��� �ޱ�
			cnt = ss.selectOne("selectTotalCount2", sVO);
			//����
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//selectTotalCount
	
	public List<QuestionListDomain> selectBoardList(QuestionSearchVO sVO) throws SQLException {
		List<QuestionListDomain> list = null;
		
		try {
			SqlSession ss = getSessionFactory().openSession();
			list = ss.selectList("selectBoardList2", sVO);
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return list;
	}//selectBoardList
	
	
//	public BoardListDomain2 selectEmpDetail(String num)throws SQLException {
//		BoardListDomain2 edd=null;
//		
//		try {
//			SqlSession ss=getSessionFactory().openSession();
//			edd=ss.selectOne("selectEmpDetail",num);
//			ss.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return edd;
//	}//selectEmpDetail
//	
	public List<QuestionListDomain> multiColumnMultiRow(){
		List<QuestionListDomain> empList=null;
		
		try {
			//Handler ���
			SqlSession ss=getSessionFactory().openSession();
			//�ԷµǴ� �� ���� ��� ���ڵ带 ��ȸ
			empList=ss.selectList("multiColumnMultiRow",30);
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return empList;
	}//multiColumnMultiRow
	
	public QuestionListDomain selectDetailNotice(String num)throws SQLException{
		
		QuestionListDomain bld=null;
		
		SqlSession ss;
		try {
			ss = getSessionFactory().openSession();
			bld=ss.selectOne("QuestionDetail",num);
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bld;
		
	}//
	
	public int insertNotice(QuestionVO ceiVO) throws SQLException {
		int cnt = 0;
		try {
			//4. MyBatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//5. id�� �־� mapper xml���� �ش� ������ parsing�Ͽ� �����ϰ� ����� ����ϴ�.
			cnt = ss.insert("QuestionInsert",ceiVO);
			//transactionó��
			ss.commit();
			//Handler�� ��� �Ϸ� �ߴٸ� �����Ѵ�.
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//insertCpEmp
	
	
	
}//class
