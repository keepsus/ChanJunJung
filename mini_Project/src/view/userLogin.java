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

public class userLogin extends JFrame implements ActionListener {

	// 필드
	private JTextField loginTextField;
	private JPasswordField passwordField;
	private JButton loginBtn, joinBtn, searchIdPwBtn;
	static MemberDTO user;

	// 생성자
	public userLogin() {

		// JFrame 기본 설정
		setTitle("유저 로그인"); // JFram title 명명
		setBounds(50, 50, 1250, 650); // 화면에서 JFrame 시작위치 & size 지정(시작x, 시작y, 늘어날길이 x, 늘어날 길이y)
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 닫기버튼 누리면 열린거 모두 닫음(프로세서 포함)
		setResizable(false);

		// JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.RED); // 확인용, 배경 : 빨강
		layeredPane.setOpaque(true); // 확인용, 투명도, JLayeredPane Background 범위 확인용

		// 이미지 추가를 위한 toolkit 호출용 클래스
		class LoginBackgroundImage extends JPanel {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image img = t.getImage("img\\login.png");

			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 1250, 650, // JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
						0, 0, 1600, 900, // 원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
						this);
			}
		}// end of MainImage inner class

		// 유저 로그인창 메인이미지를 화면에 띄움
		LoginBackgroundImage loginBackgroundImage = new LoginBackgroundImage();
		loginBackgroundImage.setBounds(0, 0, 1600, 900); // JFrame 안에서 mainImage 시작위치 & size 설정
		layeredPane.add(loginBackgroundImage, new Integer(0)); // 로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가

		Container c = this.getContentPane(); // 결과는 달라지지않지만 내부적으로 구조가 탄탄해짐
		c.add("Center", layeredPane); // 가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함

		///////////////////////// 내부 콘텐츠들/////////////////////////////////////
		// 로그인 필드
		loginTextField = new JTextField(20); // 생성, 초기화 및 텍스트가 나올상자의 사이즈 지정
		loginTextField.setBounds(580, 287, 150, 30);
		loginTextField.requestFocus();
		loginTextField.setFont(new Font("고딕체", Font.BOLD, 15));
		// loginTextField.setForeground(Color.green);
		layeredPane.add(loginTextField, new Integer(1));

		// 패스워드
		passwordField = new JPasswordField(); // 생성, 초기화 및 텍스트가 나올상자의 사이즈 지정
		passwordField.setBounds(580, 380, 150, 30);
		passwordField.setFont(new Font("고딕체", Font.BOLD, 15));
		// passwordField.setForeground(Color.green);
		layeredPane.add(passwordField, new Integer(1));

		// 로그인Btn 추가
		loginBtn = new JButton("로그인");
		loginBtn.setBounds(500, 450, 110, 45);
		loginBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		// 회원가입Btn 추가
		joinBtn = new JButton("회원가입");
		joinBtn.setBounds(630, 450, 110, 45);
		joinBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		// 아이디 & 비밀번호 찾기 Btn
		searchIdPwBtn = new JButton("아이디/비밀번호 찾기");
		searchIdPwBtn.setBounds(500, 510, 240, 45);
		searchIdPwBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		// 버튼 생성 레이어 지정
		layeredPane.add(loginBtn, new Integer(1));
		layeredPane.add(joinBtn, new Integer(1));
		layeredPane.add(searchIdPwBtn, new Integer(1));

		// 버튼 이벤트
		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		searchIdPwBtn.addActionListener(this);

	}// end of userClient() constructor

	// 버튼 클릭할 경우, 발생하는 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = null;
		MemberDAO dao = MemberDAO.getInstance();
		if (e.getSource() == loginBtn) {
			user = new MemberDTO();
			user.setOnoff(0);
			String pwtT = new String(passwordField.getPassword());
			// 로그인 버튼 클릭할 경우
			user = dao.LoginSevice(loginTextField.getText(), pwtT);

			if (loginTextField.getText().equals("") || pwtT.equals("") || user == null) {
				JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 틀렸습니다.");

			} else {
				if (user.getTime() == 0) {// 잔여시간 없으면 로그인 불가
					JOptionPane.showMessageDialog(this, "잔여 시간이 없습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				} else {
					msg =JOptionPane.showInputDialog(null,"좌석 선택해 주세요.");
					user.setOnoff(1);
					dao.setSeatOut(msg, user.getId() );
					dao.onoff(user.getOnoff(), user.getId());
					new userUsing(user);
					setVisible(false);
				}
			}
		} else if (e.getSource() == joinBtn) {
			// 회원가입 버튼 클릭 할 경우
			joinArticle();

		} else if (e.getSource() == searchIdPwBtn) {
			// 아이디/비밀번호 찾기 버튼 클릭할 경우
			SearchWindow();
		}
	}// end of actionPerformed() method

	private void SearchWindow() {
		new SearchWindow();

	}

	// loginBtn 클릭하여 로그인 했을 때 사용자가 보는 우측상단 상태창 클래스 호출
	public void userUsing() {
		new userUsing(user);
	}

	// joinBtn 클릭할 경우, 회원가입 화면으로 가는 클래스 호출
	public void joinArticle() {
		new joinArticle();
	}

	// 메인 메소드fds
	public static void main(String[] args) {
		new userLogin();
	}// end of main method

}// end of userClient class