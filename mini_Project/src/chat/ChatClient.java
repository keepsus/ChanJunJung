package chat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.MemberDTO;

public class ChatClient extends JFrame implements ActionListener, Runnable {
	private JTextField input;
	private JButton send;
	private JTextArea output;

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private MemberDTO dto;

	public ChatClient(MemberDTO user) {
		dto = new MemberDTO();
		dto = user;
		input = new JTextField();
		output = new JTextArea();
		JScrollPane scroll = new JScrollPane(output);
		output.setEditable(false);
		send = new JButton("보내기");

		// JPanel p = new JPanel(new GridLayout(1,2));
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout()); // 퍄널만 동서남북으로 재 정의 한다 => BorderLayout

		p.add("Center", input);
		p.add("East", send);

		Container c = getContentPane();
		c.add("Center", scroll);
		c.add("South", p);

		setBounds(700, 200, 300, 300);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (ois == null || oos == null)
					System.exit(0);
				try {
					oos.writeObject(dto);
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public void service() {
		// 서버IP
//		String serverIP = JOptionPane.showInputDialog(this, "서버IP를 입력하세요", "서버IP", JOptionPane.INFORMATION_MESSAGE);
		String serverIP = "192.168.0.25";
		MemberDTO user = new MemberDTO();

		// 닉네임
		user = dto;

		// 소켓 생성
		try {
			socket = new Socket(serverIP, 9500);
			System.out.println("클라이언트 챗팅");
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

		} catch (UnknownHostException e) {
			System.out.println("서버를 찾을 수 없습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("서버를 찾을 수 없습니다.");
			e.printStackTrace();
			System.exit(0);
		}

		// 서버로 닉네임 보내기
		try {
			MemberDTO dto = new MemberDTO();// 한번 쓰고 버리기
			dto.setId(user.getId());
			oos.writeObject(dto);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 스래드 생성
		Thread t = new Thread(this);
		// 스래드 시작 - 스래드 실행
		t.start();

		// 이벤트
		send.addActionListener(this);
		input.addActionListener(this);// JTextField에서
	}

	@Override
	public void run() {
		// 서버로부터 받는 쪽
		MemberDTO dto = null; // new 할필요가 없다. dto통으로 주기 떄문에 new 할필요없고 받으면 된다
		while (true) {
			try {
				dto = (MemberDTO) ois.readObject();				
					// 메세지르 ㄹ주고 받겠다
					output.append(dto.getMsg() + "\n");

					int pos = output.getText().length();
					output.setCaretPosition(pos);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 서버로 보내는 쪽
		String msg;
		msg = input.getText();

		MemberDTO dto1 = new MemberDTO();
			dto1.setId(dto.getId());
			dto1.setMsg(msg);
		try {
			oos.writeObject(dto1);
			oos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		input.setText(" ");
	}
 
}
