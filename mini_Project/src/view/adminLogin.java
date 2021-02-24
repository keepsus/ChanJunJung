package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.MemberDAO;
import model.MemberDTO;
public class adminLogin extends JFrame implements ActionListener {
	
	//필드
	private JTextField loginTextField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	
	static MemberDTO admin ;
	//생성자
	public adminLogin() {
		
		//JFrame 기본 설정
		setTitle("관리자 로그인");	//JFram title 명명
		setBounds(50, 50, 1250, 650);	//화면에서 JFrame 시작위치 & size 지정(시작x, 시작y, 늘어날길이 x, 늘어날 길이y)		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//닫기버튼 누리면 열린거 모두 닫음(프로세서 포함)
		setResizable(false);		
		
		//JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();		
		layeredPane.setBackground(Color.RED);	//확인용, 배경 : 빨강
		layeredPane.setOpaque(true);			//확인용, 투명도, JLayeredPane Background 범위 확인용
		
		
		//이미지 추가를 위한 toolkit 호출용 클래스
		class LoginBackgroundImage extends JPanel {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image img = t.getImage("img\\login.png");
			@Override
			public void paint(Graphics g) {
				g.drawImage(img,							
							0, 0, 1250, 650,	//JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
							0, 0, 1600, 900,	//원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
							this);
			}
		}//end of MainImage inner class
		
				
		//관리자 로그인창 메인이미지를 화면에 띄움
		LoginBackgroundImage loginBackgroundImage = new LoginBackgroundImage();
		loginBackgroundImage.setBounds(0, 0, 1600, 900);	//JFrame 안에서 mainImage 시작위치 & size 설정		
		layeredPane.add(loginBackgroundImage, new Integer(0));	//로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가		
						
		Container c = this.getContentPane();	//결과는 달라지지않지만 내부적으로 구조가 탄탄해짐		
		c.add("Center", layeredPane);			//가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함
		
		
		// 로그인 필드
		loginTextField = new JTextField(20);
		loginTextField.setBounds(580, 287, 150, 30);
		loginTextField.requestFocus();
		loginTextField.setFont(new Font("고딕체", Font.BOLD, 15));
		loginTextField.setForeground(Color.green);
		layeredPane.add(loginTextField, new Integer(1));	
		
		
		// 패스워드
		passwordField = new JPasswordField();	
		passwordField.setBounds(580, 380, 150, 30);
		passwordField.setFont(new Font("고딕체", Font.BOLD, 15));
		passwordField.setForeground(Color.green);	
		layeredPane.add(passwordField, new Integer(1));	

		//로그인Btn 추가
		loginBtn = new JButton("로그인");
		loginBtn.setBounds(500, 450, 110, 45);
		loginBtn.setFont(new Font("고딕체", Font.BOLD, 15));
	
		//버튼 생성 레이어 지정
		layeredPane.add(loginBtn, new Integer(1));
		
		// 버튼 이벤트
		loginBtn.addActionListener(this);
		
	}//end of adminClient() constructor
	
	
	//버튼 클릭할 경우, 발생하는 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			//로그인 버튼 클릭할 경우

			MemberDAO dao = new MemberDAO();
			if (e.getSource() == loginBtn) {
				admin = new MemberDTO();
				String pwtT = new String(passwordField.getPassword());
				// 로그인 버튼 클릭할 경우
				admin = dao.LoginSevice(loginTextField.getText(), pwtT);

				if (loginTextField.getText().equals("") || pwtT.equals("")|| 
						admin==null) {
					JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 틀렸습니다.");

				} else {
				new adminUsing(admin);
					setVisible(false);
				}
			
		}
		
		

			
			
		} 
	}//end of actionPerformed() method
	
	

	//메인 메소드
	public static void main(String[] args) {
		new adminLogin();
	}//end of main method;
	
	
}//end of adminClient() class 