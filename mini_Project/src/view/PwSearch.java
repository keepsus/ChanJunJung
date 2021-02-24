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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.MemberDAO;

public class PwSearch  extends JFrame implements ActionListener {
	private JButton sureBtn, cancleBtn;
	private JLabel idL, mailL, mail;
	private JTextField idT, mailT1, mailT2;

	public PwSearch() {
		// JFrame 생성
		setTitle("비밀번호찾기"); // JFram title 명명
		setSize(new Dimension(565, 365)); // 현재 윈도우의 크기 지정
		// setLayout(null); //이거하면 배경안나옴! layout manager 제거
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재창만 닫아라
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); /// 화면 가로, 세로 크기 산출
		Dimension frameSize = this.getSize(); // 현재 윈도우(프레임)의 크기 산출
		int locationX = (monitorSize.width - frameSize.width) / 2;
		int locationY = (monitorSize.height - frameSize.height) / 2;
		this.setLocation(locationX, locationY); // 윈도우(프레임) 위치 지정
		setVisible(true);
		// setResizable(false);

		
		// JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.RED); // 확인용, 배경 : 빨강
		layeredPane.setOpaque(true); // 확인용, 투명도, JLayeredPane Background 범위 확인용

		// 이미지 추가를 위한 toolkit 호출용 클래스
		class idSearchBackgroundImage extends JPanel {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image img = t.getImage("img\\small22.png");

			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 550, 330, // JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
						0, 0, 550, 330, // 원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
						this);
			}
		}// end of MainImage inner class
		
		// 이미지 추가를 위한 toolkit 호출용 클래스
		idSearchBackgroundImage JoinBackgroundImage = new idSearchBackgroundImage();
		JoinBackgroundImage.setBounds(0, 0, 550, 330); // JFrame 안에서 mainImage 시작위치 & size 설정
		layeredPane.add(JoinBackgroundImage, new Integer(0)); // 로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가
		
		//라벨 & 텍스트
		idL = new JLabel("아이디 : ");
		idL.setForeground(Color.green);
		mailL = new JLabel("이메일 : ");
		mailL.setForeground(Color.green);
		mail = new JLabel(" @ ");
		mail.setForeground(Color.green);
		idT = new JTextField(20);
		mailT1 = new JTextField(10);
		mailT2 = new JTextField(10);

		
		//버튼
		sureBtn = new JButton("확인");
		sureBtn.setBounds(120, 220, 120, 30);
		
		cancleBtn = new JButton("취소");
		cancleBtn.setBounds(320, 220, 120, 30);
			
		// 패널
		JPanel idP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		idP.setOpaque(false);
		idP.add(idL);
		idP.add(idT);
		idP.setBounds(100, 70, 400, 50);

		JPanel mailP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mailP.setOpaque(false);
		mailP.add(mailL);
		mailP.add(mailT1);
		mailP.add(mail);
		mailP.add(mailT2);
		mailP.setBounds(100, 140, 400, 50);

		layeredPane.add(idP, new Integer(1));
		layeredPane.add(mailP, new Integer(1));
		layeredPane.add(sureBtn, new Integer(2));
		layeredPane.add(cancleBtn, new Integer(2));
		
		Container c = this.getContentPane(); // 결과는 달라지지않지만 내부적으로 구조가 탄탄해짐
		c.add("Center", layeredPane); // 가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함
	
		// 이벤트
		sureBtn.addActionListener(this);
		cancleBtn.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MemberDAO dao = new MemberDAO();
		String msg = null;
		if (e.getSource() == sureBtn) {

			msg = dao.pwSearch(idT.getText(), mailT1.getText(), mailT2.getText());
			if (msg == null) {
				JOptionPane.showMessageDialog(this, "등록된 아이디가 없습니다");
			} else {
				JOptionPane.showMessageDialog(this, "관리자한테 문의하세요");
				setVisible(false);
			}
		} else if (e.getSource() == cancleBtn) {
			setVisible(false);
		}

	}



}