package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import model.MemberDAO;
import model.MemberDTO;

public class joinArticle extends JFrame implements ActionListener{
	private JLabel certiNumL, pwhL, dashL1, dashL2, atL, nameL, idL, pwL, ageL, maileageL, telL, mailL;
	private JComboBox<String> telT1;
	private JTextField certiNumT, nameT, idT, ageT, maileageT,  telT2, telT3 , mailT1, mailT2;
	private JButton joinBtn, cancelBtn, sameBtn, mailBtn, certiNumBtn; 
	
	private JPasswordField pwT, pwhT;
	private JList<MemberDTO> listT;
	private DefaultListModel<MemberDTO> model;
	private String checkNum;				//수정
	private int i;
	private int j;
	private int emailCheck;					//수정
	
	//생성자
	public joinArticle() {		
				
		//JFrame 생성			
		setTitle("회원가입");	//JFram title 명명		
		setSize(new Dimension(400, 600));	//현재 윈도우의 크기 지정
		//setLayout(null);	//이거하면 배경안나옴! layout manager 제거
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//현재창만 닫아라			
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();	/// 화면 가로, 세로 크기 산출
		Dimension frameSize = this.getSize();	//현재 윈도우(프레임)의 크기 산출		
		int locationX=(monitorSize.width - frameSize.width) / 2;
		int locationY=(monitorSize.height - frameSize.height) / 2;		
		this.setLocation(locationX, locationY);	//윈도우(프레임) 위치 지정
		setVisible(true);
		setResizable(false);		
		
		
		//라벨 생성
		atL = new JLabel("@");
		atL.setForeground(Color.green);
		
		dashL1 = new JLabel(" - ");
		dashL1.setForeground(Color.green);
		
		dashL2 = new JLabel(" - ");
		dashL2.setForeground(Color.green);
		
		nameL = new JLabel("이    름 :");
		nameL.setForeground(Color.green);
		
		nameT = new JTextField(10);
		
		idL = new JLabel("아 이 디 :");
		idL.setForeground(Color.green);
		
		idT = new JTextField(10);
		idT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		pwL = new JLabel("패스워드 :");
		pwL.setForeground(Color.green);
		
		pwT = new JPasswordField(10);
		pwT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		pwhL = new JLabel("패스워드 확인:");
		pwhL.setForeground(Color.green);
		
		pwhT = new JPasswordField(10);
		pwhT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		ageL = new JLabel("나      이 :");
		ageL.setForeground(Color.green);
		
		ageT = new JTextField(2);
		ageT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		telL = new JLabel("휴 대 폰 :");
		telL.setForeground(Color.green);
		
		telT1 = new JComboBox<String>();
		telT2 = new JTextField(4);
		telT2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		telT3 = new JTextField(4);
		telT3.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		mailL = new JLabel("메    일 :");
		mailL.setForeground(Color.green);
		
		mailT1 = new JTextField(15);
		mailT1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		mailT2 = new JTextField(10);
		mailT2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		certiNumL = new JLabel("인증번호");
		certiNumL.setForeground(Color.green);
		
		certiNumT = new JTextField(4);
		certiNumT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		String[] number = {"010","011","017"};
		telT1 = new JComboBox<String>(number);
		
		joinBtn = new JButton("회원가입");
		cancelBtn= new JButton("취      소");
		sameBtn = new JButton("중복체크");
		mailBtn = new JButton("인증번호발송");
		certiNumBtn = new JButton("확    인");
		
		joinBtn.setBounds(110,470,100,50);
		cancelBtn.setBounds(260,470,100,50);
		
		JPanel namP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namP.setOpaque(false);
		namP.add(nameL);
		namP.add(nameT);
		namP.setBounds(10, 20, 400, 50);
		
		JPanel idP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		idP.setOpaque(false);
		idP.add(idL);
		idP.add(idT);
		idP.add(sameBtn);
		idP.setBounds(10, 70, 400, 50);
		
		JPanel pwP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pwP.setOpaque(false);
		pwP.add(pwL);
		pwP.add(pwT);
		pwP.setBounds(10, 130, 400, 50);
		
		JPanel pwhP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pwhP.setOpaque(false);
		pwhP.add(pwhL);
		pwhP.add(pwhT);
		pwhP.setBounds(10, 180, 400, 50);
		
		JPanel ageP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ageP.setOpaque(false);
		ageP.add(ageL);
		ageP.add(ageT);
		ageP.setBounds(10, 230, 400, 50);
		
		JPanel phoneP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		phoneP.setOpaque(false);
		phoneP.add(telL);
		phoneP.add(telT1);
		phoneP.add(dashL1);
		phoneP.add(telT2);
		phoneP.add(dashL2);	
		phoneP.add(telT3);
		phoneP.setBounds(10, 290, 400, 50);
		
		JPanel mailP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mailP.setOpaque(false);
		mailP.add(mailL);
		mailP.add(mailT1);
		mailP.add(atL);
		mailP.add(mailT2);
		mailP.add(mailBtn);
		mailP.setBounds(10, 350, 400, 50);
		
		JPanel certiNumP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		certiNumP.setOpaque(false);
		certiNumP.add(certiNumL);
		certiNumP.add(certiNumT);
		certiNumP.add(certiNumBtn);
		certiNumP.setBounds(10,410, 250, 50);
				
		//JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();		
		layeredPane.setBackground(Color.BLUE);	//확인용, 배경 : 빨강
		layeredPane.setOpaque(true);			//확인용, 투명도, JLayeredPane Background 범위 확인용
				
		//이미지 추가를 위한 toolkit 호출용 클래스
		class JoinBackgroundImage extends JPanel {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("img\\mainHud_back.png");
			@Override
			public void paint(Graphics g) {
			g.drawImage(img,							
					-26, -35, 400, 600,	//JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
					0, 0, 1600, 900,	//원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
					this);
			}
		}//end of MainImage inner class
				
		
		//이미지 추가를 위한 toolkit 호출용 클래스
		JoinBackgroundImage JoinBackgroundImage = new JoinBackgroundImage();
		JoinBackgroundImage.setBounds(0, 0, 1600, 900);	//JFrame 안에서 mainImage 시작위치 & size 설정		
		layeredPane.add(JoinBackgroundImage, new Integer(0));	//로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가
		
		layeredPane.add(namP, new Integer(1));
		layeredPane.add(idP, new Integer(1));
		layeredPane.add(pwP, new Integer(1));
		layeredPane.add(pwhP, new Integer(1));
		layeredPane.add(ageP, new Integer(1));
		layeredPane.add(phoneP, new Integer(1));
		layeredPane.add(mailP, new Integer(1));
		layeredPane.add(certiNumP, new Integer(1));
		layeredPane.add(joinBtn, new Integer(1));
		layeredPane.add(cancelBtn, new Integer(1));
		
		Container c = this.getContentPane();	//결과는 달라지지않지만 내부적으로 구조가 탄탄해짐		
		c.add("Center", layeredPane);			//가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함
	
		
		// 이벤트
		joinBtn.addActionListener(this);
		sameBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		mailBtn.addActionListener(this);				//수정 인증번호받기
		certiNumBtn.addActionListener(this);			//수정 확인
		
		
		
	}//end of joinWindow() constructor


	@Override
	public void actionPerformed(ActionEvent e) {
		String pwtT = new String(pwT.getPassword());								//수정
		String pwhtT = new String(pwhT.getPassword());								//수정
		String userEmail = mailT1.getText()+"@"+mailT2.getText();					//수정
				
		if (e.getSource() == joinBtn) { // CHECK //if - else if로 수정

			if (nameT.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "이름은 필수 항목입니다.");
			} else if (idT.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "아이디는 필수 항목입니다.");
			} else if (!pwtT.equals(pwhtT)) {//
				JOptionPane.showMessageDialog(this, "비밀번호가 같지 않습니다.");// CHECK //같지않기때문에 !붙여서 수정
			} else if (telT2.getText().length() == 0 || telT3.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "전화번호는 필수 항목입니다.");
			} else if (mailT1.getText().length() == 0 || mailT2.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "메일은 필수 항목입니다.");
			} else {//
				if (i == 1) {
					insertArticle();// CHECK //insert함수 삽입
				} else if (i != 1) {
					JOptionPane.showMessageDialog(this, "아이디 중복 확인을 해주세요");
				}
				if (emailCheck != 1) {
					JOptionPane.showMessageDialog(this, "이메일 인증을 확인해주세요");
				}
			}
			
			
			// 회원가입
		} else if (e.getSource() == cancelBtn) {
			setVisible(false);
		} else if (e.getSource() == sameBtn) {
			// 아이디 중복확인
			sameBtn();

			if (i != 1) {
				JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
				idT.setText("");
			} else if (i == 1) {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
			} else if (idT.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
			} 
			
			
		} else if (e.getSource() == mailBtn) {//수정 
			//수정
			
			String email1 = mailT1.getText();
			String email2 = mailT2.getText();
			if(email1.equals("") || email2.equals("")) {
				JOptionPane.showMessageDialog(this, "메일을 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
			}else if(emailCheck != 1) {
				checkNum = new MailSend().NaverMailSend(userEmail);		//수정			
				JOptionPane.showMessageDialog(this, "인증 번호가 발송되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);//수정
				emailCheck = 1; 
			}
			
//			JOptionPane.showMessageDialog(this, "이메일을 확인해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
			
																		
		} else if (e.getSource() == certiNumBtn) {
			if(!checkNum.equals(certiNumT.getText())){
				JOptionPane.showMessageDialog(this, "인증번호가 틀렸습니다, 다시 입력해 주세요", "정보", JOptionPane.INFORMATION_MESSAGE);//수정
			}else if(checkNum.equals(certiNumT.getText())) {
				JOptionPane.showMessageDialog(this, "인증되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		// 인증 메일 받기
		
	}//end of actionPerformed() method
	
	
	private void mailBtn() {			//수정
		String email1 = mailT1.getText();
		String email2 = mailT2.getText();
		
		if(email1.equals("") || email2.equals("")) {
			JOptionPane.showMessageDialog(this, "메일을 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		}else if(emailCheck != 1) {
			JOptionPane.showMessageDialog(this, "이메일을 확인해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		}else if(!checkNum.equals(certiNumT.getText())) {
			JOptionPane.showMessageDialog(this, "인증번호가 틀렸습니다, 다시 입력해 주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}								//수정


	private void sameBtn() {
		i = 1;
		MemberDAO dao = MemberDAO.getInstance();
		i = dao.same(idT.getText());

	}

	private void insertArticle() {
		// 데이터
		String pwtT = new String(pwT.getPassword());
		String name = nameT.getText();
		int age = Integer.parseInt(ageT.getText());
		String id = idT.getText();
		String pw = pwtT;
		String tel1 = (String) this.telT1.getSelectedItem();
		String tel2 = telT2.getText();
		String tel3 = telT3.getText();
		String mail1 = mailT1.getText();
		String mail2 = mailT2.getText();

		MemberDTO dto = new MemberDTO();
		dto.setName(name);
		dto.setAge(age);
		dto.setId(id);
		dto.setPw(pw);
		dto.setTel1(tel1);
		dto.setTel2(tel2);
		dto.setTel3(tel3);
		dto.setMail1(mail1);
		dto.setMail2(mail2);

		// DB
		MemberDAO dao = MemberDAO.getInstance();

		int seq = dao.getSeq();

		dto.setSeq(seq);
		dao.insertArticle(dto);


		// 응답

		JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
		clear();
		dispose();

	}//end of insertArticle() method
	
	private void clear() {
		nameT.setText("");
		ageT.setText("");
		idT.setText("");
		pwT.setText("");
		telT1.setSelectedItem("010");
		telT2.setText("");
		telT3.setText("");
		mailT1.setText("");
		mailT2.setText("");
	}//end of clear() method
	
	
	
}//end of joinWindow class
