package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chat.ChatClient;
import model.MemberDAO;
import model.MemberDTO;

public class userUsing extends JFrame implements ActionListener {

	// 필드
	private JLabel seatNumL, timeL, useHourL, remainHourL, prepaidL, chargeL, col1L, col2L, col3L, col4L;
	private JTextField seatNumT, timeT, hourT, minuteT, secondT, remainHourT, remaiminuteT, remainsecondT, chargeT;
	private JButton chatBtn, orderBtn, exitBtn, startBtn, stopBtn, resetBtn;
	private MemberDTO user;
	private String useHourT, useMinuteT;

	// 생성자
	public userUsing(MemberDTO user) {

		this.user = user;

		// JFrame 기본 설정
		setTitle("유저 사용중"); // JFram title 명명
		setBounds(900, 50, 400, 400); // 화면에서 JFrame 시작위치 & size 지정(시작x, 시작y, 늘어날길이 x, 늘어날 길이y)
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기버튼 누리면 열린거 모두 닫음(프로세서 포함)

		// JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.BLUE); // 확인용, 배경 : 파랑
		layeredPane.setOpaque(true); // 확인용, 투명도, JLayeredPane Background 범위 확인용

		// 이미지 추가를 위한 toolkit 호출용 클래스
		class LoginBackgroundImage extends JPanel {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image img = t.getImage("img\\mainHud_back.png");

			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 400, 380, // JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
						0, 0, 1600, 900, // 원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
						this);
			}// end of paint() method
		}// end of MainImage inner class

		// 유저 로그인창 메인이미지를 화면에 띄움
		LoginBackgroundImage loginBackgroundImage = new LoginBackgroundImage();
		loginBackgroundImage.setBounds(-15, 0, 400, 400); // JFrame 안에서 mainImage 시작위치 & size 설정
		layeredPane.add(loginBackgroundImage, new Integer(0)); // 로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가

		Container c = this.getContentPane(); // 결과는 달라지지않지만 내부적으로 구조가 탄탄해짐
		c.add("Center", layeredPane); // 가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함

		// JLabel 생성
		seatNumL = new JLabel("자리번호 : ");
		seatNumL.setForeground(Color.green);

		timeL = new JLabel("현재시간 : ");
		timeL.setForeground(Color.green);

		useHourL = new JLabel("이용시간 : ");
		useHourL.setForeground(Color.green);

		remainHourL = new JLabel("남은시간 : ");
		remainHourL.setForeground(Color.green);

		prepaidL = new JLabel("선불요금 : ");
		prepaidL.setForeground(Color.green);

		chargeL = new JLabel("선불요금 : ");
		chargeL.setForeground(Color.green);

		col1L = new JLabel(":");
		col1L.setForeground(Color.green);

		col2L = new JLabel(":");
		col2L.setForeground(Color.green);

		col3L = new JLabel(":");
		col3L.setForeground(Color.green);

		col4L = new JLabel(":");
		col4L.setForeground(Color.green);

		// JTextField 생성
		seatNumT = new JTextField(1);
		seatNumT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		seatNumT.setOpaque(false);
		seatNumT.setFont(new Font("고딕체", Font.BOLD, 15));
		seatNumT.setForeground(Color.green);

		// 현재시간
		timeT = new JTextField(8);
		timeT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		timeT.setOpaque(false);
		timeT.setFont(new Font("고딕체", Font.BOLD, 15));
		timeT.setForeground(Color.green);

		// 사용 시간 시계, hourT, minuteT, secondT
		hourT = new JTextField("--", 2);
		hourT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		hourT.setOpaque(false);
		hourT.setFont(new Font("고딕체", Font.BOLD, 15));
		hourT.setForeground(Color.green);

		minuteT = new JTextField("--", 2);
		minuteT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		minuteT.setOpaque(false);
		minuteT.setFont(new Font("고딕체", Font.BOLD, 15));
		minuteT.setForeground(Color.green);

		secondT = new JTextField("--", 2);
		secondT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		secondT.setOpaque(false);
		secondT.setFont(new Font("고딕체", Font.BOLD, 15));
		secondT.setForeground(Color.green);

		// 충전시간에서 차감되어 남은 시간
		remainHourT = new JTextField(2);
		remainHourT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHourT.setOpaque(false);
		remainHourT.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHourT.setForeground(Color.green);

		remaiminuteT = new JTextField(2);
		remaiminuteT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remaiminuteT.setOpaque(false);
		remaiminuteT.setFont(new Font("고딕체", Font.BOLD, 15));
		remaiminuteT.setForeground(Color.green);

		remainsecondT = new JTextField(2);
		remainsecondT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainsecondT.setOpaque(false);
		remainsecondT.setFont(new Font("고딕체", Font.BOLD, 15));
		remainsecondT.setForeground(Color.green);

		// 충전금액
		chargeT = new JTextField(10);
		chargeT.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chargeT.setOpaque(false);
		chargeT.setFont(new Font("고딕체", Font.BOLD, 15));
		chargeT.setForeground(Color.green);

		// JPanel 생성
		JPanel seatP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		seatP.setOpaque(false);
		seatP.add(seatNumL);
		seatP.add(seatNumT);
		seatP.setBounds(20, 40, 300, 40);
		layeredPane.add(seatP, new Integer(1));

		JPanel timeP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timeP.setOpaque(false);
		timeP.add(timeL);
		timeP.add(timeT);
		timeP.setBounds(200, 40, 200, 40);
		layeredPane.add(timeP, new Integer(1));

		JPanel useHourP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		useHourP.setOpaque(false);
		useHourP.add(useHourL);
		useHourP.add(hourT);
		useHourP.add(col1L);
		useHourP.add(minuteT);
		useHourP.add(col2L);
		useHourP.add(secondT);
		useHourP.setBounds(20, 80, 300, 40);
		layeredPane.add(useHourP, new Integer(1));

		JPanel remainHourP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		remainHourP.setOpaque(false);
		remainHourP.add(remainHourL);
		remainHourP.add(remainHourT);
		remainHourP.add(col3L);
		remainHourP.add(remaiminuteT);
		remainHourP.add(col4L);
		remainHourP.add(remainsecondT);
		remainHourP.setBounds(20, 120, 300, 40);
		layeredPane.add(remainHourP, new Integer(1));

		JPanel chargeP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		chargeP.setOpaque(false);
		chargeP.add(chargeL);
		chargeP.add(chargeT);
		chargeP.setBounds(20, 160, 300, 40);
		layeredPane.add(chargeP, new Integer(1));

		// 버튼 생성
		chatBtn = new JButton("문의하기");
		chatBtn.setBounds(20, 230, 100, 40);
		chatBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		orderBtn = new JButton("주문하기");
		orderBtn.setBounds(140, 230, 100, 40);
		orderBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		exitBtn = new JButton("종료하기");
		exitBtn.setBounds(260, 230, 100, 40);
		exitBtn.setFont(new Font("고딕체", Font.BOLD, 15));

//		startBtn = new JButton("공란");
//		startBtn.setBounds(20, 290, 100, 40);
//		startBtn.setFont(new Font("고딕체", Font.BOLD, 15));
//
//		stopBtn = new JButton("공란");
//		stopBtn.setBounds(140, 290, 100, 40);
//		stopBtn.setFont(new Font("고딕체", Font.BOLD, 15));
//
//		resetBtn = new JButton("공란");
//		resetBtn.setBounds(260, 290, 100, 40);
//		resetBtn.setFont(new Font("고딕체", Font.BOLD, 15));

		// 버튼 생성 레이어 지정
		layeredPane.add(chatBtn, new Integer(2));
		layeredPane.add(orderBtn, new Integer(2));
		layeredPane.add(exitBtn, new Integer(2));
//		layeredPane.add(startBtn, new Integer(2));
//		layeredPane.add(stopBtn, new Integer(2));
//		layeredPane.add(resetBtn, new Integer(2));

		// 버튼 이벤트
		chatBtn.addActionListener(this);
		orderBtn.addActionListener(this);
		exitBtn.addActionListener(this);
//		startBtn.addActionListener(this);
//		stopBtn.addActionListener(this);
//		resetBtn.addActionListener(this);

		// 사용자 이용창에 보여지는 시간 - 스레드
		new ThreadCount(hourT, minuteT, secondT).start();

		// 남은 시간
		new remainHour(remainHourT, remaiminuteT, remainsecondT, user).start();

		// 현재시간
		new ThreadClock(timeT).start();

		// 화면 닫기버튼 클릭할 경우, 해당 창만 닫힘
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}// end of userClient() constructor

	// 버튼 클릭할 경우, 발생하는 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		MemberDAO dao = new MemberDAO();
		if (e.getSource() == chatBtn) {
			new ChatClient(user).service();

		} else if (e.getSource() == orderBtn) {

		} else if (e.getSource() == exitBtn) {
			// 사용차 창에서 종료버튼 클릭하면 그 시점의 시간과 분을 JTextField 타입으로 가져옴
			useHourT = ThreadCount.h.getText();
			useMinuteT = ThreadCount.m.getText();
			
			user.setTime(Integer.parseInt(useHourT) * 60 + Integer.parseInt(useMinuteT));
//
			// 사용시간 스레드 정지 시킴
			ThreadCount.h = null;
			ThreadCount.m = null;
			ThreadCount.s = null;

			// 창 종료
			user.setOnoff(0);
			dao.setTime(user.getTime(), user.getId());
			dao.onoff(user.getOnoff(), user.getId());
			dao.setSeatOut("0", user.getId());
			System.exit(0);

		} 
	}// end of actionPerformed() method

}// end of userUsing class

//남은시간
class remainHour extends Thread {
	// 필드
	static JTextField h;
	static JTextField m;
	static JTextField s;
	private int fixTime;
	MemberDTO user;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public remainHour(JTextField remainHourT, JTextField remaiminuteT, JTextField remainsecondT, MemberDTO user) {
		
		this.user = user;
		h = remainHourT;
		m = remaiminuteT;
		s = remainsecondT;

		fixTime = user.getTime();
	}

	public void run() {
		int time = fixTime;

		int hour;

		hour = time / 60;

		// 처음 저장시간만큼 돌림
		while (true) {
			if (isInterrupted()) {
				break;
			}

			for (int i = hour-1; i >= 0; i--) {
				for (int j = 59; j >= 0; j--) {
					for (int k = 59; k >= 0; k--) {
						if (k < 0) {
							k = 59;
						}
						if (j < 0) {
							j = 59;
						}
						h.setText(Integer.toString(i));
						m.setText(Integer.toString(j));
						s.setText(Integer.toString(k));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}
			user.setOnoff(0);
			dao.setTime(0, user.getId());
			dao.onoff(0,user.getId());
			dao.setSeatOut("0", user.getId());
			System.exit(0);


		}
	}
}
// 시간이 다 끝난 후 현재 창 종료 후 로그인창 띄움.

//			MemberDAO dao = MemberDAO.getInstance();  
//			dao.setTime(time, userID);//DB에 아이디와 종료시간 0 을 보내서 update시킴
//			dao.setOff(userID);
//			dao.setSeatOut(userID);
//			
//			CntServer cnt = CntServer.getInstance(memberDTO);
//			InfoDTO infoDTO = new InfoDTO();
//			infoDTO.setUserName(userName);
//			infoDTO.setUserID(userID);
//			infoDTO.setUserTime(memberDTO.getTime());
//			infoDTO.setTotCost(memberDTO.getPostPayment());
//			cnt.getExit(infoDTO);
//			
//			dispose();
//			new LoginFrame();
//			

// run

//사용시간 클래스 - 스레드
class ThreadCount extends Thread {

	// 필드
	static JTextField h;
	static JTextField m;
	static JTextField s;

	// 생성자
	public ThreadCount(JTextField hourT, JTextField minuteT, JTextField secondT) {
		h = hourT;
		m = minuteT;
		s = secondT;
	}

	// run method
	public void run() {
		int second = 0;
		int minute = 0;
		int hour = 0;

		while (s != null || m != null || h != null) {

			s.setText(Integer.toString(second));
			m.setText(Integer.toString(minute));
			h.setText(Integer.toString(hour));

			second++;
			if (second == 60) {
				minute++;
				second = 0;
			}

			if (minute == 60) {
				hour++;
				minute = 0;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // while()
	}// end of run() method

}// end of ThreadCount class

//현재시간 클래스 - 스레드
class ThreadClock extends Thread {

	// 필드
	JTextField target;

	// 생성자
	public ThreadClock(JTextField t) {
		target = t;
	}

	// run method
	public void run() {
		Calendar c = null;
		String time = null;
		while (true) {
			c = Calendar.getInstance();// Calendar객체 생성안하고 시간읽을때
			time = c.get(Calendar.HOUR) + "시" + c.get(Calendar.MINUTE) + "분" + c.get(Calendar.SECOND) + "초";
			target.setText(time);
			try {
				Thread.sleep(1000);// 보기좋게 시간출력 위해1초 쉬게함
			} catch (InterruptedException e) {
			}
		} // while()
	}// end of run() method

}// end of ThreadClock class
