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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chat.ChatClient;
import model.MemberDAO;
import model.MemberDTO;
import model.MemberKwanli;
import sales.AddTime;
import sales.SalesInfoMgr;

public class adminUsing extends JFrame implements ActionListener {

	// 필드
	private JLabel name0L, name1L, name2L, name3L, useHour0L, useHour1L, useHour2L, useHour3L, remainHour0L,
			remainHour1L, remainHour2L, remainHour3L, usePay0L, usePay1L, usePay2L, usePay3L, buyPay0L, buyPay1L,
			buyPay2L, buyPay3L;
	private JTextField seatNum0T, seatNum1T, seatNum2T, seatNum3T, name0T, name1T, name2T, name3T, useHour0T, useHour1T,
			useHour2T, useHour3T, remainHour0T, remainHour1T, remainHour2T, remainHour3T, usePay0T, usePay1T, usePay2T,
			usePay3T, buyPay0T, buyPay1T, buyPay2T, buyPay3T, nowtime;
	private JButton pc0Btn, pc1Btn, pc2Btn, pc3Btn, navi0Btn, navi1Btn, navi2Btn, navi3Btn;
	// 화면 회원 제고 매상
	JTextField remainHourT, remaiminuteT, remainsecondT;

	private int fixTime;
	private int seat;
	private Thread t;
	MemberDTO admin;

	public adminUsing(MemberDTO dto) {
		admin = new MemberDTO();
		admin = dto;

		// JFrame 기본 설정
		setTitle("관리자 사용중"); // JFram title 명명
		setBounds(50, 50, 1250, 650); // 화면에서 JFrame 시작위치 & size 지정(시작x, 시작y, 늘어날길이 x, 늘어날 길이y)
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 닫기버튼 누리면 열린거 모두 닫음(프로세서 포함)
		setResizable(false);

		// JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.BLUE); // 확인용, 배경 : 빨강
		layeredPane.setOpaque(true); // 확인용, 투명도, JLayeredPane Background 범위 확인용

		// 이미지 추가를 위한 toolkit 호출용 클래스
		class LoginBackgroundImage extends JPanel {

			// 이미지 불러오기
			// 배경화면
			Toolkit bgt = Toolkit.getDefaultToolkit();
			Image imgbg = bgt.getImage("img\\mainHud_back.png");

			// 좌측상단 삼각별
			Toolkit cl1t = Toolkit.getDefaultToolkit();
			Toolkit cl2t = Toolkit.getDefaultToolkit();
			Toolkit cl3t = Toolkit.getDefaultToolkit();
			Image imgcl1 = cl1t.getImage("img\\cl1.png");
			Image imgcl2 = cl2t.getImage("img\\cl2.png");
			Image imgcl3 = cl3t.getImage("img\\cl3.png");

			// 좌석 선택시 체크표시
			Toolkit checkt = Toolkit.getDefaultToolkit();
			Image imgcheck = checkt.getImage("img\\check.png");

			@Override
			public void paint(Graphics g) {
				// 배경화면
				g.drawImage(imgbg, -50, 0, 1250, 650, // JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
						0, 0, 1600, 900, // 원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
						this);

			}// end of paint() method
		}// end of MainImage inner class

		// 유저 로그인창 메인이미지를 화면에 띄움
		LoginBackgroundImage loginBackgroundImage = new LoginBackgroundImage();
		loginBackgroundImage.setBounds(0, 0, 1600, 900); // JFrame 안에서 mainImage 시작위치 & size 설정
		layeredPane.add(loginBackgroundImage, new Integer(0)); // 로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가

		// JButton 생성 - 메뉴 버튼 4개
		navi0Btn = new JButton(new ImageIcon("img\\bt_navi_0.png"));
		navi1Btn = new JButton(new ImageIcon("img\\bt_navi_1.png"));
		navi2Btn = new JButton(new ImageIcon("img\\bt_navi_2.png"));
		navi3Btn = new JButton(new ImageIcon("img\\bt_navi_3.png"));
		navi0Btn.setBounds(80, 80, 182, 120);
		navi1Btn.setBounds(80, 260, 182, 120);
		navi3Btn.setBounds(80, 440, 182, 120);
		navi2Btn.setBounds(302, 440, 182, 120);
		layeredPane.add(navi0Btn, new Integer(1));
		layeredPane.add(navi1Btn, new Integer(1));
		layeredPane.add(navi3Btn, new Integer(1));
		layeredPane.add(navi2Btn, new Integer(1));

		// JButton 생성 - 좌석 버튼 4개
		pc0Btn = new JButton(new ImageIcon("img\\gameOff.png"));
		pc1Btn = new JButton(new ImageIcon("img\\gameOff.png"));
		pc2Btn = new JButton(new ImageIcon("img\\gameOff.png"));
		pc3Btn = new JButton(new ImageIcon("img\\gameOff.png"));
		pc0Btn.setBounds(600, 80, 220, 220);
		pc1Btn.setBounds(600, 350, 220, 220);
		pc2Btn.setBounds(900, 80, 220, 220);
		pc3Btn.setBounds(900, 350, 220, 220);
		layeredPane.add(pc0Btn, new Integer(1));
		layeredPane.add(pc1Btn, new Integer(1));
		layeredPane.add(pc2Btn, new Integer(1));
		layeredPane.add(pc3Btn, new Integer(1));

		// 좌석 버튼 내부 JTextField(1/6) - 좌석번호
		seatNum0T = new JTextField("좌석번호 : 1번", 10);
		seatNum1T = new JTextField("좌석번호 : 2번", 10);
		seatNum2T = new JTextField("좌석번호 : 3번", 10);
		seatNum3T = new JTextField("좌석번호 : 4번", 10);
		seatNum0T.setFont(new Font("고딕체", Font.BOLD, 15));
		seatNum1T.setFont(new Font("고딕체", Font.BOLD, 15));
		seatNum2T.setFont(new Font("고딕체", Font.BOLD, 15));
		seatNum3T.setFont(new Font("고딕체", Font.BOLD, 15));
		seatNum0T.setForeground(Color.green);
		seatNum1T.setForeground(Color.green);
		seatNum2T.setForeground(Color.green);
		seatNum3T.setForeground(Color.green);
		seatNum0T.setOpaque(false);
		seatNum1T.setOpaque(false);
		seatNum2T.setOpaque(false);
		seatNum3T.setOpaque(false);
		seatNum0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		seatNum1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		seatNum2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		seatNum3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		seatNum0T.setBounds(630, 105, 100, 20);
		seatNum1T.setBounds(630, 375, 100, 20);
		seatNum2T.setBounds(930, 105, 100, 20);
		seatNum3T.setBounds(930, 375, 100, 20);
		layeredPane.add(seatNum0T, new Integer(2));
		layeredPane.add(seatNum1T, new Integer(2));
		layeredPane.add(seatNum2T, new Integer(2));
		layeredPane.add(seatNum3T, new Integer(2));

		// 좌석 버튼 내부 JLabel(2/6) - 회원이름 :
		name0L = new JLabel("회원이름 :");
		name1L = new JLabel("회원이름 :");
		name2L = new JLabel("회원이름 :");
		name3L = new JLabel("회원이름 :");
		name0L.setFont(new Font("고딕체", Font.BOLD, 15));
		name1L.setFont(new Font("고딕체", Font.BOLD, 15));
		name2L.setFont(new Font("고딕체", Font.BOLD, 15));
		name3L.setFont(new Font("고딕체", Font.BOLD, 15));
		name0L.setForeground(Color.green);
		name1L.setForeground(Color.green);
		name2L.setForeground(Color.green);
		name3L.setForeground(Color.green);
		name0L.setOpaque(false);
		name1L.setOpaque(false);
		name2L.setOpaque(false);
		name3L.setOpaque(false);
		name0L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name1L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name2L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name3L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name0L.setBounds(630, 135, 100, 20); // x+75, y+30
		name1L.setBounds(630, 405, 100, 20);
		name2L.setBounds(930, 135, 100, 20);
		name3L.setBounds(930, 405, 100, 20);
		layeredPane.add(name0L, new Integer(2));
		layeredPane.add(name1L, new Integer(2));
		layeredPane.add(name2L, new Integer(2));
		layeredPane.add(name3L, new Integer(2));
		// 좌석 버튼 내부 JTextField
		name0T = new JTextField(10);
		name1T = new JTextField(10);
		name2T = new JTextField(10);
		name3T = new JTextField(10);
		name0T.setFont(new Font("고딕체", Font.BOLD, 15));
		name1T.setFont(new Font("고딕체", Font.BOLD, 15));
		name2T.setFont(new Font("고딕체", Font.BOLD, 15));
		name3T.setFont(new Font("고딕체", Font.BOLD, 15));
		name0T.setForeground(Color.green);
		name1T.setForeground(Color.green);
		name2T.setForeground(Color.green);
		name3T.setForeground(Color.green);
		name0T.setOpaque(false);
		name1T.setOpaque(false);
		name2T.setOpaque(false);
		name3T.setOpaque(false);
//		name0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		name1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		name2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		name3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name0T.setBounds(705, 135, 90, 20);
		name1T.setBounds(705, 405, 90, 20);
		name2T.setBounds(1005, 135, 90, 20);
		name3T.setBounds(1005, 405, 90, 20);
		layeredPane.add(name0T, new Integer(2));
		layeredPane.add(name1T, new Integer(2));
		layeredPane.add(name2T, new Integer(2));
		layeredPane.add(name3T, new Integer(2));

		// 좌석 버튼 내부 JLabel(3/6) - 사용시간 :
		useHour0L = new JLabel("사용시간 :");
		useHour1L = new JLabel("사용시간 :");
		useHour2L = new JLabel("사용시간 :");
		useHour3L = new JLabel("사용시간 :");
		useHour0L.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour1L.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour2L.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour3L.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour0L.setForeground(Color.green);
		useHour1L.setForeground(Color.green);
		useHour2L.setForeground(Color.green);
		useHour3L.setForeground(Color.green);
		useHour0L.setOpaque(false);
		useHour1L.setOpaque(false);
		useHour2L.setOpaque(false);
		useHour3L.setOpaque(false);
		useHour0L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		useHour1L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		useHour2L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		useHour3L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		useHour0L.setBounds(630, 165, 100, 20);
		useHour1L.setBounds(630, 435, 100, 20);
		useHour2L.setBounds(930, 165, 100, 20);
		useHour3L.setBounds(930, 435, 100, 20);
		layeredPane.add(useHour0L, new Integer(2));
		layeredPane.add(useHour1L, new Integer(2));
		layeredPane.add(useHour2L, new Integer(2));
		layeredPane.add(useHour3L, new Integer(2));
		// 좌석 버튼 내부 JTextField
		useHour0T = new JTextField(20);
		useHour1T = new JTextField(20);
		useHour2T = new JTextField(20);
		useHour3T = new JTextField(20);
		useHour0T.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour1T.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour2T.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour3T.setFont(new Font("고딕체", Font.BOLD, 15));
		useHour0T.setForeground(Color.green);
		useHour1T.setForeground(Color.green);
		useHour2T.setForeground(Color.green);
		useHour3T.setForeground(Color.green);
		useHour0T.setOpaque(false);
		useHour1T.setOpaque(false);
		useHour2T.setOpaque(false);
		useHour3T.setOpaque(false);
//		useHour0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		useHour1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		useHour2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		useHour3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		useHour0T.setBounds(705, 165, 90, 20);
		useHour1T.setBounds(705, 435, 90, 20);
		useHour2T.setBounds(1005, 165, 90, 20);
		useHour3T.setBounds(1005, 435, 90, 20);
		layeredPane.add(useHour0T, new Integer(2));
		layeredPane.add(useHour1T, new Integer(2));
		layeredPane.add(useHour2T, new Integer(2));
		layeredPane.add(useHour3T, new Integer(2));

		// 좌석 버튼 내부 JLabel(4/6) - 남은시간 :
		remainHour0L = new JLabel("남은시간 :");
		remainHour1L = new JLabel("남은시간 :");
		remainHour2L = new JLabel("남은시간 :");
		remainHour3L = new JLabel("남은시간 :");
		remainHour0L.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour1L.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour2L.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour3L.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour0L.setForeground(Color.green);
		remainHour1L.setForeground(Color.green);
		remainHour2L.setForeground(Color.green);
		remainHour3L.setForeground(Color.green);
		remainHour0L.setOpaque(false);
		remainHour1L.setOpaque(false);
		remainHour2L.setOpaque(false);
		remainHour3L.setOpaque(false);
		remainHour0L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHour1L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHour2L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHour3L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHour0L.setBounds(630, 195, 100, 20); // x+75, y+30
		remainHour1L.setBounds(630, 465, 100, 20);
		remainHour2L.setBounds(930, 195, 100, 20);
		remainHour3L.setBounds(930, 464, 100, 20);
		layeredPane.add(remainHour0L, new Integer(2));
		layeredPane.add(remainHour1L, new Integer(2));
		layeredPane.add(remainHour2L, new Integer(2));
		layeredPane.add(remainHour3L, new Integer(2));
		// 좌석 버튼 내부 JTextField
		remainHour0T = new JTextField(20);
		remainHour1T = new JTextField(20);
		remainHour2T = new JTextField(20);
		remainHour3T = new JTextField(20);
		remainHour0T.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour1T.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour2T.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour3T.setFont(new Font("고딕체", Font.BOLD, 15));
		remainHour0T.setForeground(Color.green);
		remainHour1T.setForeground(Color.green);
		remainHour2T.setForeground(Color.green);
		remainHour3T.setForeground(Color.green);
		remainHour0T.setOpaque(false);
		remainHour1T.setOpaque(false);
		remainHour2T.setOpaque(false);
		remainHour3T.setOpaque(false);
//		remainHour0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		remainHour1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		remainHour2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		remainHour3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		remainHour0T.setBounds(705, 195, 90, 20);
		remainHour1T.setBounds(705, 465, 90, 20);
		remainHour2T.setBounds(1005, 195, 90, 20);
		remainHour3T.setBounds(1005, 465, 90, 20);
		layeredPane.add(remainHour0T, new Integer(2));
		layeredPane.add(remainHour1T, new Integer(2));
		layeredPane.add(remainHour2T, new Integer(2));
		layeredPane.add(remainHour3T, new Integer(2));

		// 좌석 버튼 내부 JLabel(5/6) - 이용요금 :
		usePay0L = new JLabel("이용요금 :");
		usePay1L = new JLabel("이용요금 :");
		usePay2L = new JLabel("이용요금 :");
		usePay3L = new JLabel("이용요금 :");
		usePay0L.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay1L.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay2L.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay3L.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay0L.setForeground(Color.green);
		usePay1L.setForeground(Color.green);
		usePay2L.setForeground(Color.green);
		usePay3L.setForeground(Color.green);
		usePay0L.setOpaque(false);
		usePay1L.setOpaque(false);
		usePay2L.setOpaque(false);
		usePay3L.setOpaque(false);
		usePay0L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usePay1L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usePay2L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usePay3L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usePay0L.setBounds(630, 225, 100, 20); // x+75, y+30
		usePay1L.setBounds(630, 495, 100, 20);
		usePay2L.setBounds(930, 225, 100, 20);
		usePay3L.setBounds(930, 495, 100, 20);
		layeredPane.add(usePay0L, new Integer(2));
		layeredPane.add(usePay1L, new Integer(2));
		layeredPane.add(usePay2L, new Integer(2));
		layeredPane.add(usePay3L, new Integer(2));
		// 좌석 버튼 내부 JTextField
		usePay0T = new JTextField(20);
		usePay1T = new JTextField(20);
		usePay2T = new JTextField(20);
		usePay3T = new JTextField(20);
		usePay0T.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay1T.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay2T.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay3T.setFont(new Font("고딕체", Font.BOLD, 15));
		usePay0T.setForeground(Color.green);
		usePay1T.setForeground(Color.green);
		usePay2T.setForeground(Color.green);
		usePay3T.setForeground(Color.green);
		usePay0T.setOpaque(false);
		usePay1T.setOpaque(false);
		usePay2T.setOpaque(false);
		usePay3T.setOpaque(false);
//		usePay0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		usePay1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		usePay2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		usePay3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usePay0T.setBounds(705, 225, 90, 20);
		usePay1T.setBounds(705, 495, 90, 20);
		usePay2T.setBounds(1005, 225, 90, 20);
		usePay3T.setBounds(1005, 495, 90, 20);
		layeredPane.add(usePay0T, new Integer(2));
		layeredPane.add(usePay1T, new Integer(2));
		layeredPane.add(usePay2T, new Integer(2));
		layeredPane.add(usePay3T, new Integer(2));

		// 좌석 버튼 내부 JLabel(6/6) - 판매요금 :
//		buyPay0L = new JLabel("판매요금 :");
//		buyPay1L = new JLabel("판매요금 :");
//		buyPay2L = new JLabel("판매요금 :");
//		buyPay3L = new JLabel("판매요금 :");
//		buyPay0L.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay1L.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay2L.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay3L.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay0L.setForeground(Color.green);
//		buyPay1L.setForeground(Color.green);
//		buyPay2L.setForeground(Color.green);
//		buyPay3L.setForeground(Color.green);
//		buyPay0L.setOpaque(false);
//		buyPay1L.setOpaque(false);
//		buyPay2L.setOpaque(false);
//		buyPay3L.setOpaque(false);
//		buyPay0L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay1L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay2L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay3L.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay0L.setBounds(630, 255, 100, 20);	//x+75, y+30
//		buyPay1L.setBounds(630, 525, 100, 20);
//		buyPay2L.setBounds(930, 255, 100, 20);
//		buyPay3L.setBounds(930, 525, 100, 20);
//		layeredPane.add(buyPay0L, new Integer(2));
//		layeredPane.add(buyPay1L, new Integer(2));	
//		layeredPane.add(buyPay2L, new Integer(2));	
//		layeredPane.add(buyPay3L, new Integer(2));	
//		// 좌석 버튼 내부 JTextField
//		buyPay0T = new JTextField(4);
//		buyPay1T = new JTextField(4);
//		buyPay2T = new JTextField(4);
//		buyPay3T = new JTextField(4);
//		buyPay0T.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay1T.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay2T.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay3T.setFont(new Font("고딕체", Font.BOLD, 15));
//		buyPay0T.setForeground(Color.green);
//		buyPay1T.setForeground(Color.green);
//		buyPay2T.setForeground(Color.green);
//		buyPay3T.setForeground(Color.green);
//		buyPay0T.setOpaque(false);
//		buyPay1T.setOpaque(false);
//		buyPay2T.setOpaque(false);
//		buyPay3T.setOpaque(false);
//		buyPay0T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay1T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay2T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay3T.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		buyPay0T.setBounds(705, 255, 90, 20);
//		buyPay1T.setBounds(705, 525, 90, 20);
//		buyPay2T.setBounds(1005, 255, 90, 20);
//		buyPay3T.setBounds(1005, 525, 90, 20);
//		layeredPane.add(buyPay0T, new Integer(2));
//		layeredPane.add(buyPay1T, new Integer(2));
//		layeredPane.add(buyPay2T, new Integer(2));
//		layeredPane.add(buyPay3T, new Integer(2));

		// 사용시간 Thread 시작

		Container c = this.getContentPane(); // 결과는 달라지지않지만 내부적으로 구조가 탄탄해짐
		c.add("Center", layeredPane); // 가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함
//1번
		new adminuser1(name0T).start();
		new remainCount1(remainHour0T).start();
		new ThreadCount1(useHour0T).start();
		new price01(usePay0T).start();
		
//2번
		new adminuser2(name1T).start();
		new remainCount2(remainHour1T).start();
		new ThreadCount2(useHour1T).start();
		new price02(usePay1T).start();
		
//3번
				new adminuser3(name2T).start();
				new remainCount3(remainHour2T).start();
				new ThreadCount3(useHour2T).start();
				new price03(usePay2T).start();
				
				//4번
				new adminuser4(name3T).start();
				new remainCount4(remainHour3T).start();
				new ThreadCount4(useHour3T).start();
				new price04(usePay3T).start();
				

		// 이벤트
		pc0Btn.addActionListener(this);
		pc1Btn.addActionListener(this);
		pc2Btn.addActionListener(this);
		pc3Btn.addActionListener(this);
		navi0Btn.addActionListener(this);
		navi1Btn.addActionListener(this);
		navi2Btn.addActionListener(this);
		navi3Btn.addActionListener(this);

	}// end of adminUsing() co

	@Override
	public void actionPerformed(ActionEvent e) {
		MemberDAO dao = MemberDAO.getInstance();
		if (e.getSource() == pc0Btn) {
			new ChatClient(admin).service();
		} else if (e.getSource() == pc1Btn) {
			new ChatClient(admin).service();
		} else if (e.getSource() == pc2Btn) {
			new ChatClient(admin).service();
		} else if (e.getSource() == pc3Btn) {
			new ChatClient(admin).service();
		} else if (e.getSource() == navi0Btn) {
			new AddTime();
		} else if (e.getSource() == navi1Btn) {
			new MemberKwanli().event();
		} else if (e.getSource() == navi2Btn) {
			admin.setOnoff(0);
			dao.onoff(admin.getOnoff(), admin.getId());
			System.exit(0);
		} else if (e.getSource() == navi3Btn) {
			new SalesInfoMgr();
		}
	}
}// end of adminUsing class


//1번 좌석
class adminuser1 extends Thread {

	MemberDTO usedto1 = null;
	JTextField name;

	MemberDAO dao = MemberDAO.getInstance();

	public adminuser1(JTextField name0T) {
		name = name0T;

	}

	public void run() {
		while (true) {
			usedto1 = dao.adminuserinfo(1, 1);
			if (usedto1 != null) {
				name.setText(usedto1.getId());
			}

			else if (usedto1 == null) {
				name.setText("");
			}
		}
	}
}

//사용시간 클래스 - 스레드
class ThreadCount1 extends Thread {

	// 필드
	JTextField h0;
	MemberDTO usedto1 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public ThreadCount1(JTextField useHour0T) {
		h0 = useHour0T;

	}

	// run method
	public void run() {
		while (true) {
			usedto1 = dao.adminuserinfo(1, 1);
			
			if (usedto1 != null) {
			int hour = usedto1.getTime() / 60;
				for (int i = 0 ; i  <hour ; i++) {
					for (int j = 0; j <=59; j++) {
						for (int k = 0; k <=59; k++) {
							usedto1 = dao.adminuserinfo(1, 1);
							if (usedto1 == null) {
								h0.setText("");
								break;
							}
							h0.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}
			} else if (usedto1 == null) {
				h0.setText(" ");
			}

		}
	}// end of run() method

}// end of ThreadCount class


//남은시간 클래스 - 스레드
class remainCount1 extends Thread {

	// 필드
	JTextField h0;
	MemberDTO usedto1 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public remainCount1(JTextField remainHour0T) {
		h0 = remainHour0T;
	}

	// run method
	public void run() {
		while (true) {
			usedto1 = dao.adminuserinfo(1, 1);
			if (usedto1 != null) {
				int hour = usedto1.getTime() / 60;
		
				for (int i = hour - 1; i >= 0; i--) {
					for (int j = 59; j >= 0; j--) {
						for (int k = 59; k >= 0; k--) {
							usedto1 = dao.adminuserinfo(1, 1);
						
							if (usedto1 == null) {
								h0.setText("");
								break;
							}
							if (k < 0) {
								k = 59;
							}
							if (j < 0) {
								j = 59;
							}
							h0.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}

			} else if (usedto1 == null) {
				h0.setText("");
			}
		}
	}
}


class price01 extends Thread{
	// 필드
		JTextField price0;
		MemberDTO usedto1 = null;
		MemberDAO dao = MemberDAO.getInstance();
		int time ;
		int price = 1000;
	public price01(JTextField usePay0T) {
		price0 = usePay0T;
	}
	@Override
	public void run() {
		while(true) {
			usedto1 = dao.adminuserinfo(1, 1);
			if (usedto1 != null) {
				int time = usedto1.getTime()/60;				
												
				price0.setText(Integer.toString(time*price));
				
			}
			else if (usedto1 == null) {
				price0.setText("");
			}
		}
	}
}
//1번 좌석

//2번 좌석
class adminuser2 extends Thread {

	MemberDTO usedto2 = null;
	JTextField name;

	MemberDAO dao = MemberDAO.getInstance();

	public adminuser2(JTextField name1T) {
		name = name1T;

	}

	public void run() {
		while (true) {
			usedto2 = dao.adminuserinfo(1, 2);
			if (usedto2 != null) {
				name.setText(usedto2.getId());
			}

			else if (usedto2 == null) {
				name.setText("");
			}
		}
	}
}

//사용시간 클래스 - 스레드
class ThreadCount2 extends Thread {

	// 필드
	JTextField h1;
	MemberDTO usedto2 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public ThreadCount2(JTextField useHour1T) {
		h1 = useHour1T;

	}

	// run method
	public void run() {
		while (true) {
			usedto2 = dao.adminuserinfo(1, 2);
			
			if (usedto2 != null) {
			int hour = usedto2.getTime() / 60;
				for (int i = 0 ; i  <hour ; i++) {
					for (int j = 0; j <=59; j++) {
						for (int k = 0; k <=59; k++) {
							usedto2 = dao.adminuserinfo(1, 2);
							if (usedto2 == null) {
								h1.setText("");
								break;
							}
							h1.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}
			} else if (usedto2 == null) {
				h1.setText(" ");
			}

		}
	}// end of run() method

}// end of ThreadCount class


//남은시간 클래스 - 스레드
class remainCount2 extends Thread {

	// 필드
	JTextField h2;
	MemberDTO usedto2 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public remainCount2(JTextField remainHour1T) {
		h2 = remainHour1T;
	}

	// run method
	public void run() {
		while (true) {
			usedto2 = dao.adminuserinfo(1, 2);
			if (usedto2 != null) {
				int hour = usedto2.getTime() / 60;
		
				for (int i = hour - 1; i >= 0; i--) {
					for (int j = 59; j >= 0; j--) {
						for (int k = 59; k >= 0; k--) {
							usedto2 = dao.adminuserinfo(1, 2);
						
							if (usedto2 == null) {
								h2.setText("");
								break;
							}
							if (k < 0) {
								k = 59;
							}
							if (j < 0) {
								j = 59;
							}
							h2.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}

			} else if (usedto2 == null) {
				h2.setText("");
			}
		}
	}
}


class price02 extends Thread{
	// 필드
		JTextField price1;
		MemberDTO usedto2 = null;
		MemberDAO dao = MemberDAO.getInstance();
		int time ;
		int price = 1000;
	public price02(JTextField usePay1T) {
		price1 = usePay1T;
	}
	@Override
	public void run() {
		while(true) {
			usedto2 = dao.adminuserinfo(1, 2);
			if (usedto2 != null) {
				int time = usedto2.getTime()/60;				
												
				price1.setText(Integer.toString(time*price));
				
			}
			else if (usedto2 == null) {
				price1.setText("");
			}
		}
	}
} //2번 좌석

//3번 좌석
class adminuser3 extends Thread {

	MemberDTO usedto3 = null;
	JTextField name;

	MemberDAO dao = MemberDAO.getInstance();

	public adminuser3(JTextField name2T) {
		name = name2T;

	}

	public void run() {
		while (true) {
			usedto3 = dao.adminuserinfo(1, 3);
			if (usedto3 != null) {
				name.setText(usedto3.getId());
			}

			else if (usedto3 == null) {
				name.setText("");
			}
		}
	}
}

//사용시간 클래스 - 스레드
class ThreadCount3 extends Thread {

	// 필드
	JTextField h3;
	MemberDTO usedto3 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public ThreadCount3(JTextField useHour2T) {
		h3 = useHour2T;

	}

	// run method
	public void run() {
		while (true) {
			usedto3 = dao.adminuserinfo(1, 3);
			
			if (usedto3 != null) {
			int hour = usedto3.getTime() / 60;
				for (int i = 0 ; i  <hour ; i++) {
					for (int j = 0; j <=59; j++) {
						for (int k = 0; k <=59; k++) {
							usedto3 = dao.adminuserinfo(1, 3);
							if (usedto3 == null) {
								h3.setText("");
								break;
							}
							h3.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}
			} else if (usedto3 == null) {
				h3.setText(" ");
			}

		}
	}// end of run() method

}// end of ThreadCount class


//남은시간 클래스 - 스레드
class remainCount3 extends Thread {

	// 필드
	JTextField h3;
	MemberDTO usedto3 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public remainCount3(JTextField remainHour2T) {
		h3 = remainHour2T;
	}

	// run method
	public void run() {
		while (true) {
			usedto3 = dao.adminuserinfo(1, 3);
			if (usedto3 != null) {
				int hour = usedto3.getTime() / 60;
		
				for (int i = hour - 1; i >= 0; i--) {
					for (int j = 59; j >= 0; j--) {
						for (int k = 59; k >= 0; k--) {
							usedto3 = dao.adminuserinfo(1, 3);
						
							if (usedto3 == null) {
								h3.setText("");
								break;
							}
							if (k < 0) {
								k = 59;
							}
							if (j < 0) {
								j = 59;
							}
							h3.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}

			} else if (usedto3 == null) {
				h3.setText("");
			}
		}
	}
}


class price03 extends Thread{
	// 필드
		JTextField price3;
		MemberDTO usedto3 = null;
		MemberDAO dao = MemberDAO.getInstance();
		int time ;
		int price = 1000;
	public price03(JTextField usePay2T) {
		price3 = usePay2T;
	}
	@Override
	public void run() {
		while(true) {
			usedto3 = dao.adminuserinfo(1, 3);
			if (usedto3 != null) {
				int time = usedto3.getTime()/60;				
												
				price3.setText(Integer.toString(time*price));
				
			}
			else if (usedto3 == null) {
				price3.setText("");
			}
		}
	}
} //3번 좌석

//4번 좌석
class adminuser4 extends Thread {

	MemberDTO usedto4 = null;
	JTextField name;

	MemberDAO dao = MemberDAO.getInstance();

	public adminuser4(JTextField name3T) {
		name = name3T;

	}

	public void run() {
		while (true) {
			usedto4 = dao.adminuserinfo(1, 4);
			if (usedto4 != null) {
				name.setText(usedto4.getId());
			}

			else if (usedto4 == null) {
				name.setText("");
			}
		}
	}
}

//사용시간 클래스 - 스레드
class ThreadCount4 extends Thread {

	// 필드
	JTextField h4;
	MemberDTO usedto4 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public ThreadCount4(JTextField useHour3T) {
		h4 = useHour3T;

	}

	// run method
	public void run() {
		while (true) {
			usedto4 = dao.adminuserinfo(1, 4);
			
			if (usedto4 != null) {
			int hour = usedto4.getTime() / 60;
				for (int i = 0 ; i  <hour ; i++) {
					for (int j = 0; j <=59; j++) {
						for (int k = 0; k <=59; k++) {
							usedto4 = dao.adminuserinfo(1, 4);
							if (usedto4 == null) {
								h4.setText("");
								break;
							}
							h4.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}
			} else if (usedto4 == null) {
				h4.setText(" ");
			}

		}
	}// end of run() method

}// end of ThreadCount class


//남은시간 클래스 - 스레드
class remainCount4 extends Thread {

	// 필드
	JTextField h4;
	MemberDTO usedto4 = null;
	MemberDAO dao = MemberDAO.getInstance();

	// 생성자
	public remainCount4(JTextField remainHour3T) {
		h4 = remainHour3T;
	}

	// run method
	public void run() {
		while (true) {
			usedto4 = dao.adminuserinfo(1, 4);
			if (usedto4 != null) {
				int hour = usedto4.getTime() / 60;
		
				for (int i = hour - 1; i >= 0; i--) {
					for (int j = 59; j >= 0; j--) {
						for (int k = 59; k >= 0; k--) {
							usedto4 = dao.adminuserinfo(1, 4);
						
							if (usedto4 == null) {
								h4.setText("");
								break;
							}
							if (k < 0) {
								k = 59;
							}
							if (j < 0) {
								j = 59;
							}
							h4.setText(Integer.toString(i) + " : " + Integer.toString(j) + " : " + Integer.toString(k));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}

			} else if (usedto4 == null) {
				h4.setText("");
			}
		}
	}
}


class price04 extends Thread{
	// 필드
		JTextField price4;
		MemberDTO usedto4 = null;
		MemberDAO dao = MemberDAO.getInstance();
		int time ;
		int price = 1000;
	public price04(JTextField usePay3T) {
		price4 = usePay3T;
	}
	@Override
	public void run() {
		while(true) {
			usedto4 = dao.adminuserinfo(1, 4);
			if (usedto4 != null) {
				int time = usedto4.getTime()/60;				
												
				price4.setText(Integer.toString(time*price));
				
			}
			else if (usedto4 == null) {
				price4.setText("");
			}
		}
	}
} //3번 좌석

