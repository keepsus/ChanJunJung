package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class SearchWindow extends JFrame implements ActionListener{
	private JButton id, pw, cancle;

	public SearchWindow() {
		// JFrame 생성
		setTitle("아이디/비밀번호찾기"); // JFram title 명명
		setSize(new Dimension(460, 430)); // 현재 윈도우의 크기 지정
		// setLayout(null); //이거하면 배경안나옴! layout manager 제거
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재창만 닫아라
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize(); /// 화면 가로, 세로 크기 산출
		Dimension frameSize = this.getSize(); // 현재 윈도우(프레임)의 크기 산출
		int locationX = (monitorSize.width - frameSize.width) / 2;
		int locationY = (monitorSize.height - frameSize.height) / 2;
		this.setLocation(locationX, locationY); // 윈도우(프레임) 위치 지정
		setVisible(true);
		//setResizable(false);

		// JLayeredPane 생성
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.BLUE); // 확인용, 배경 : 빨강
		layeredPane.setOpaque(true); // 확인용, 투명도, JLayeredPane Background 범위 확인용

		// 이미지 추가를 위한 toolkit 호출용 클래스
		class JoinBackgroundImage extends JPanel {
			Toolkit t = Toolkit.getDefaultToolkit();
			Image img = t.getImage("img\\small.png");

			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 450, 390, // JFrame 창 내부에서 시작위치(setBounds와 동일하면 full size)
						0, 0, 450, 390, // 원본 이미지 크기(시작x, 시작y, 끝x, 끝y)
						this);
			}
		}// end of MainImage inner class

		// 버튼 생성
		id = new JButton("아이디찾기");
		id.setBounds(100,270,120,30 );
		pw = new JButton("비밀번호 찾기");
		pw.setBounds(250,270,120,30);
		cancle = new JButton("취소");
		cancle.setBounds(200, 330, 70, 30);

		
		// 이미지 추가를 위한 toolkit 호출용 클래스
		JoinBackgroundImage JoinBackgroundImage = new JoinBackgroundImage();
		JoinBackgroundImage.setBounds(0, 0, 1600, 900); // JFrame 안에서 mainImage 시작위치 & size 설정
		layeredPane.add(JoinBackgroundImage, new Integer(0)); // 로그인 메인 이미지를 JLayerdPane (0, 제일 아래에) 에 추가

		layeredPane.add(id, new Integer(1));
		layeredPane.add(pw, new Integer(1));
		layeredPane.add(cancle, new Integer(1));

		Container c = this.getContentPane(); // 결과는 달라지지않지만 내부적으로 구조가 탄탄해짐
		c.add("Center", layeredPane); // 가끔식 실행은 되지만 layeredPane, ContentPane 둘다 이미지가 불러와지지 않는 error 발생함

		//이벤트
		id.addActionListener(this);
		pw.addActionListener(this);
		cancle.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == id) {
			new IdSearch();
		}else if(e.getSource() == pw) {
			new PwSearch();
		}else if(e.getSource() == cancle) {
			setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		new SearchWindow();
	}

}
