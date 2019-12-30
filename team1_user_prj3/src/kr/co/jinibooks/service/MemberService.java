package kr.co.jinibooks.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import kr.co.jinibooks.dao.MemberDAO;
import kr.co.jinibooks.domain.MemberInfoDomain;
import kr.co.jinibooks.vo.IDandEmailandTempPWVO;
import kr.co.jinibooks.vo.JoinVO;
import kr.co.jinibooks.vo.LoginVO;
import kr.co.sist.util.cipher.DataEncrypt;

@Component
public class MemberService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired(required = false)
	private MemberDAO mDAO;
	
	public JSONObject searchIDOverlap(String inputID) {
		JSONObject json = new JSONObject();
		
		boolean result_flag;
		try {
			result_flag = mDAO.selectIDOverlap(inputID);
			
			// ã�Ƴ� �����ͷ� JSONObject ����
			json.put("result_flag", result_flag);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return json;
	}//searchIDOverlap
	
	public JSONObject searchEmailOverlap(String inputEmail) {
		JSONObject json = new JSONObject();
		
		boolean result_flag;
		try {
			result_flag = mDAO.selectEmailOverlap(inputEmail);
			
			// ã�Ƴ� �����ͷ� JSONObject ����
			json.put("result_flag", result_flag);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return json;
	}//searchEmailOverlap
	
	public JSONObject addJoin(JoinVO jVO){
		JSONObject json = new JSONObject();
		boolean flag = false;
		
		try {
			//JoinVO�� ��й�ȣ ��ȣȭ
			String key ="1111111111111111";
			DataEncrypt de = new DataEncrypt(key);
			
			String encryptPW = de.encryption(jVO.getInputPW());
			jVO.setInputPW(encryptPW);
		
			// JoinVO�� ���� null �� ó��
			if (jVO.getGender() == null) {
				jVO.setGender("-");
				;
			}//end if
		
		    //DAO�� ����Ͽ� 
			//�������� �����ϰ� ����� ��´�.
			int cnt = mDAO.insertJoin(jVO);
			if(cnt == 1) {
				flag = true;
			}//end if
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		json.put("joinFlag", flag);
		
		return json;
	}//addCpEmp
	
	public JSONObject searchIDPW(LoginVO lVO) {
		System.out.println("���񽺿��� lVO Ȯ��: "+ lVO);
		JSONObject json = new JSONObject();
		
		String result_name;
		try {
			//��й�ȣ ��ȣȭ
			String key ="1111111111111111";
			DataEncrypt de = new DataEncrypt(key);
			
			String encryptPW = de.encryption(lVO.getInputPW());
			lVO.setInputPW(encryptPW);

			System.out.println(lVO);
			result_name = mDAO.selectIDPW(lVO);
			//System.out.println(result_name);
			
			// ã�Ƴ� �����ͷ� JSONObject ����
			json.put("result_flag", result_name != null ? true : false);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();	
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return json;
	}//searchIDPW
	
	public JSONObject searchIDEMail(IDandEmailandTempPWVO idAndEmailVO) {
		JSONObject json = new JSONObject();
		
		String result_name;
		try {
			result_name = mDAO.selectIDEmail(idAndEmailVO);
			//System.out.println(result_name);
			
			// ã�Ƴ� �����ͷ� JSONObject ����
			json.put("result_flag", result_name != null ? true : false);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return json;
	}//searchIDEMail
	
	public JSONObject searchIDWithEmail(String inputEmail) {
		JSONObject json = new JSONObject();
		
		String outputID;
		try {
			outputID = mDAO.selectIDWithEmail(inputEmail);
			//System.out.println("����: "+outputID);
			
			// ã�Ƴ� �����ͷ� JSONObject ����
			json.put("outputID", outputID);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		return json;
	}//searchIDWithEmail
	
	public JSONObject modifyPW(IDandEmailandTempPWVO idAndEmailAndTempPWVO) {
		JSONObject json = new JSONObject();
		
		//��й�ȣ�� ���� �����(8�� �̻�, ����/����/Ư������ �� 2���� �̻� �Է����ּ���.)
		String tempPW = getRamdomPassword(8);
		//System.out.println("tempPW: "+tempPW);
		
		//��ȣȭ �Ͽ� DB�� ����ϰ�
		String encryptPW = "";
		try {
			String key ="1111111111111111";
			DataEncrypt de = new DataEncrypt(key);
			encryptPW = de.encryption(tempPW);
			//System.out.println("encryptPW: "+encryptPW);

			idAndEmailAndTempPWVO.setTempPW(encryptPW);
			
			boolean flag = (mDAO.updateTempPW(idAndEmailAndTempPWVO)==1);
			json.put("flag", flag);
			
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch

		//�̸��Ϸ� ������
		sendPWMail(idAndEmailAndTempPWVO.getInputEmail(),idAndEmailAndTempPWVO.getInputID(),tempPW);
		
		//����� �˷� �ݴϴ�.
		return json;
	}//modifyPW
	
	private String getRamdomPassword(int len) { 
		//char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' }; 
		String abc = "abcdefghijklmnopqrstuvwxyz";
		String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String special = "`~!@#$%^&*()-=_+{}[];:'\'<>,./?|\\";
		String total = abc.concat(ABC).concat(special);
		
		int idx = 0; 
		StringBuilder sb = new StringBuilder(); 
		//System.out.println("charSet.length :::: "+charSet.length); 
		for (int i = 0; i < len; i++) { 
			idx = (int) (total.length() * Math.random()); // 36 * ������ ������ Int�� ���� (�Ҽ�������) 
			//System.out.println("idx :::: "+idx); 
			sb.append(total.charAt(idx)); 
		}//end for
		return sb.toString(); 
	}//getRamdomPassword
	//��ó: https://mkil.tistory.com/331 [����Ͳɱ⸰]
	
	public void sendIDMail(String outputID, String inputEmail) {
		//System.out.println("���� ������ ����");
		
		String setfrom = "jinibooks1212@gmail.com"; // ������ ��� �̸���
		String tomail = inputEmail; // �޴� ��� �̸���
		String title = "���ϺϽ� - ������ ���̵� �˷��帳�ϴ�."; // ����
		StringBuilder content = new StringBuilder();
		content
		.append("ȸ����, �ȳ��ϼ���!\r\n") 
		.append("���̵� �����̳���?\r\n") 
		.append("ȸ������ ���̵� �ȳ��ص帳�ϴ�.\r\n") 
		.append("\r\n") 
		.append(outputID+"\r\n") 
		.append("\r\n") 
		.append("�׻� ���ϺϽ��� ������ֽô� ���Բ� ����帮��,\r\n") 
		.append("���� ���� ���ϺϽ��� �Ǳ� ���� �ּ��� ���ϰڽ��ϴ�.\r\n") 
		.append("\r\n") 
		.append("���ϺϽ� �帲 ^^"); // ����

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // �����»�� �����ϸ� �����۵��� ����
			messageHelper.setTo(tomail); // �޴»�� �̸���
			messageHelper.setSubject(title); // ���������� ������ �����ϴ�
			messageHelper.setText(content.toString()); // ���� ����

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			//System.out.println("���� ������ ����");
		}//end catch
		
		//System.out.println("���� ������ ��");
	}//sendIDMail
	
	public void sendPWMail(String inputEmail, String inputID, String tempPW) {
		//System.out.println("���� ������ ����");
		
		String setfrom = "jinibooks1212@gmail.com"; // ������ ��� �̸���
		String tomail = inputEmail; // �޴� ��� �̸���
		String title = "����Ͻ� - "+inputID+" ���� �� ��й�ȣ ������ �ȳ��ص帳�ϴ�."; // ����
		StringBuilder content = new StringBuilder();
		content
		.append("ȸ����, �ȳ��ϼ���!\r\n") 
		.append("ȸ������ �ӽ� ��й�ȣ�� �ȳ��ص帳�ϴ�.\r\n") 
		.append("\r\n") 
		.append(tempPW+"\r\n") 
		.append("\r\n") 
		.append("�׻� ���ϺϽ��� ������ֽô� ���Բ� ����帮��,\r\n") 
		.append("���� ���� ���ϺϽ��� �Ǳ� ���� �ּ��� ���ϰڽ��ϴ�.\r\n") 
		.append("\r\n") 
		.append("���ϺϽ� �帲 ^^"); // ����

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // �����»�� �����ϸ� �����۵��� ����
			messageHelper.setTo(tomail); // �޴»�� �̸���
			messageHelper.setSubject(title); // ���������� ������ �����ϴ�
			messageHelper.setText(content.toString()); // ���� ����

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			//System.out.println("���� ������ ����");
		}//end catch
		
		//System.out.println("���� ������ ��");
	}//sendPWMail
	
	public MemberInfoDomain searchMemberInfo(String id) {
		MemberInfoDomain mid = null;
		
		try {
			mid = mDAO.selectMemberInfo(id);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
				
		return mid;		
	}//searchMemberInfo
	
	public void modifyLoginDate(String id){
		//boolean flag = false;
		
		try {
		    //DAO�� ����Ͽ� 
			//�������� �����ϰ� ����� ��´�.
			int cnt = mDAO.updateLoginDate(id);
			if(cnt == 1) {
				//flag = true;
			}//end if
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}//end catch
		
		//return flag;
	}//modifyLoginDate

}
