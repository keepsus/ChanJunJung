package view;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
	
	public MailSend() {
		
	}
	
	public String NaverMailSend(String userEmail) {
		Properties props;
		@SuppressWarnings("unused")
		String host = "smtp@gmail.com";
		String user = "asdadasdad";	//이메일
		String password = "12313123";	//비번 수정할 것
		String checkNum = null;
		
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", true);
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
				
			}
		});
		
		
		//수신자 메일 주소
		try {
			MimeMessage message = new MimeMessage(session);

			//수신시 발신자 표시
			message.setFrom(new InternetAddress(user,"찬준정PC방"));
			
			//수신자 메일 주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(""+userEmail+""));
			
			//메일 제목
			message.setSubject("인증 번호 확인");
			
			checkNum = checkNum(); //인증번호 생성 메서드
			
			//메일 내용
			message.setText("찬준정PC방 인증번호 : " + checkNum);
			
			//send the message
			Transport.send(message);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		return checkNum;
		
	}//mailSend()

	
	//인증번호 생성 메서드
	public String checkNum() {
		String checkNum = "";
		for(int i=0; i<5; i++) {
			checkNum += Integer.toString((int)(Math.random()*10));
			
		}
		
		return checkNum;
	}


}
