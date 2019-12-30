package kr.co.jinibooks.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.jinibooks.domain.NoticeListDomain;
import kr.co.jinibooks.vo.NoticeSearchVO;
import kr.co.jinibooks.vo.NoticeVO;


public class NoticeDAO {
    
	private static NoticeDAO nDAO;
	private static SqlSessionFactory ssf;
	
	private NoticeDAO() {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();	
	}//SelectDAO
	
	public static NoticeDAO getInstance() {
		if (nDAO == null) {
			nDAO = new NoticeDAO();
		}//end if
		return nDAO;
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
	
	public int selectTotalCount(NoticeSearchVO sVO) throws SQLException {
		int cnt = 0;
		
		try {
			//Mybatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//���� ���� �� ��� �ޱ�
			cnt = ss.selectOne("selectTotalCount", sVO);
			//����
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//selectTotalCount
	
	public List<NoticeListDomain> selectBoardList(NoticeSearchVO sVO) throws SQLException {
		List<NoticeListDomain> list = null;
		
		try {
			SqlSession ss = getSessionFactory().openSession();
			list = ss.selectList("selectBoardList", sVO);
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return list;
	}//selectBoardList
	
	
	public NoticeListDomain selectEmpDetail(String num)throws SQLException {
		NoticeListDomain edd=null;
		
		try {
			SqlSession ss=getSessionFactory().openSession();
			edd=ss.selectOne("selectEmpDetail",num);
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return edd;
	}//selectEmpDetail
	
	public List<NoticeListDomain> multiColumnMultiRow(){
		List<NoticeListDomain> empList=null;
		
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
	
	public NoticeListDomain selectDetailNotice(String num)throws SQLException{
		
		NoticeListDomain bld=null;
		
		SqlSession ss;
		try {
			ss = getSessionFactory().openSession();
			bld=ss.selectOne("NoticeDetail",num);
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bld;
		
	}//
	
	public int insertNotice(NoticeVO ceiVO) throws SQLException {
		int cnt = 0;
		try {
			//4. MyBatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//5. id�� �־� mapper xml���� �ش� ������ parsing�Ͽ� �����ϰ� ����� ����ϴ�.
			cnt = ss.update("NoticeUpdate",ceiVO);
			//transactionó��
			ss.commit();
			//Handler�� ��� �Ϸ� �ߴٸ� �����Ѵ�.
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//insertCpEmp
	
	public int insertNotice2(NoticeVO ceiVO) throws SQLException {
		int cnt = 0;
		try {
			//4. MyBatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//5. id�� �־� mapper xml���� �ش� ������ parsing�Ͽ� �����ϰ� ����� ����ϴ�.
			cnt = ss.insert("NoticeInsert",ceiVO);
			//transactionó��
			ss.commit();
			//Handler�� ��� �Ϸ� �ߴٸ� �����Ѵ�.
			ss.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
		
		return cnt;
	}//insertCpEmp
	
	public int deleteNotice(NoticeVO ceiVO) throws SQLException {
		int cnt = 0;
		try {
			//4. MyBatis Handler ���
			SqlSession ss = getSessionFactory().openSession();
			//5. id�� �־� mapper xml���� �ش� ������ parsing�Ͽ� �����ϰ� ����� ����ϴ�.
			cnt = ss.delete("NoticeDelete",ceiVO);
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
