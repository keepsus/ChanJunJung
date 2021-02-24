package model;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MemberKwanli extends JFrame implements ActionListener {
	private List<MemberDTO> list;
	private DefaultTableModel model;
	private JTable table;
	private JButton searchBtn, modiBtn, deleBtn, showAllBtn;

	public MemberKwanli() {
		// 리스트
		list = new ArrayList<MemberDTO>();
		
		// 테이블 제목 생성
		Vector<String> vector = new Vector<String>();
		vector.add("NAME");
		vector.add("AGE");
		vector.add("ID");
		vector.add("PW");
		vector.add("Tel1");
		vector.add("Tel2");
		vector.add("Tel3");
		vector.add("MAIL1");
		vector.add("MAIL2");
		vector.add("TIME");

		model = new DefaultTableModel(vector, 0);

		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);

		MemberDAO dao = MemberDAO.getInstance();
		list = dao.getMemberList();

		// 테이블의 데이터 뿌리기.
		for (MemberDTO dto : list) {
			Vector<String> v = new Vector<String>();
			v.add(dto.getName());
			v.add(dto.getAge() + ""); // 정수형이므로
			v.add(dto.getId());
			v.add(dto.getPw());
			v.add(dto.getTel1());
			v.add(dto.getTel2());
			v.add(dto.getTel3());
			v.add(dto.getMail1());
			v.add(dto.getMail2());
			v.add(dto.getTime() + "");

			model.addRow(v);
		}

		// JButton 생성
		searchBtn = new JButton("찾기");
		modiBtn = new JButton("수정");
		deleBtn = new JButton("삭제");
		showAllBtn = new JButton("전체목록");

		// 버튼 패널 생성
		JPanel buttonP = new JPanel(new GridLayout(1, 4, 5, 1));
		buttonP.add(searchBtn);
		buttonP.add(modiBtn);
		buttonP.add(deleBtn);
		buttonP.add(showAllBtn);

		// 컨테이너
		Container con = this.getContentPane();
		con.add("Center", scroll);
		con.add("South", buttonP);

		// 프레임창
		setBounds(650, 200, 800, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		System.out.println("회원 수 : " + list.size());
	}// MemberKwanli()

	// 이벤트
	public void event() {
		searchBtn.addActionListener(this);
		modiBtn.addActionListener(this);
		deleBtn.addActionListener(this);
		showAllBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {
			System.out.println(list.size());
			searchArticle();
		} else if (e.getSource() == modiBtn) {
			System.out.println(123);
			updateArticle();
		} else if (e.getSource() == deleBtn) {
			deleteArticle();
		} else if (e.getSource() == showAllBtn) {
			showAll();
		}
	}// end of actionPerformed method

	
	public void showAll() { // MemberKwanli() 내부 내용과 동일
		
		//list에 있는 항목 삭제
		System.out.println();
		System.out.println();
		System.out.println("삭제 전 회원 수 : " + list.size());
		
		model.setRowCount(0);
		
		System.out.println("list 삭제");
		System.out.println("삭제 후 회원 수 : " + list.size());
		
		// 리스트////////
		System.out.println("new");
			
		MemberDAO dao = MemberDAO.getInstance();
		list = dao.getMemberList();

		// 테이블의 데이터 뿌리기.
		for (MemberDTO dto : list) {
			Vector<String> v = new Vector<String>();
			v.add(dto.getName());
			v.add(dto.getAge() + ""); // 정수형이므로
			v.add(dto.getId());
			v.add(dto.getPw());
			v.add(dto.getTel1());
			v.add(dto.getTel2());
			v.add(dto.getTel3());
			v.add(dto.getMail1());
			v.add(dto.getMail2());
			v.add(dto.getTime() + "");

			model.addRow(v);/////////////
		}
		System.out.println("회원 수 : " + list.size());
		
		
	}// end of showAll() method


	private void searchArticle() {

		String name = JOptionPane.showInputDialog(null, "이름을 입력해주세요");
		if (name == null || name.length() == 0) {
			return;
		}

		int sw = 0;
		for (MemberDTO dto : list) {
			if (dto.getName().equals(name)) {
				System.out.println("찾는 회원 이름 = "+dto.getName());	//회원이름
				if (sw == 0)
					model.setRowCount(0);

				Vector<String> v = new Vector<String>();
				v.add(dto.getName());
				v.add(dto.getAge() + " ");
				v.add(dto.getId());
				v.add(dto.getPw());
				v.add(dto.getTel1());
				v.add(dto.getTel2());
				v.add(dto.getTel3());
				v.add(dto.getMail1());
				v.add(dto.getMail2());

				model.addRow(v);
				sw = 1;
			}
		}
		if (sw == 0)
			JOptionPane.showMessageDialog(null, "이름을 찾을수 없습니다.");
		return;
	}

	private void updateArticle() {
		// 데이터
		int index = table.getSelectedRow();

		TableModel data = table.getModel();

		String name = (String) data.getValueAt(index, 0);
		String age = (String) data.getValueAt(index, 1);
		String id = (String) data.getValueAt(index, 2);
		String pw = (String) data.getValueAt(index, 3);
		String tel1 = (String) data.getValueAt(index, 4);
		String tel2 = (String) data.getValueAt(index, 5);
		String tel3 = (String) data.getValueAt(index, 6);
		String mail1 = (String) data.getValueAt(index, 7);
		String mail2 = (String) data.getValueAt(index, 8);

		// MemberDTO
		MemberDTO dto = new MemberDTO();
		dto.setName(name);
		dto.setAge(Integer.parseInt(age));
		dto.setId(id);
		dto.setPw(pw);
		dto.setTel1(tel1);
		dto.setTel2(tel2);
		dto.setTel3(tel3);
		dto.setMail1(mail1);
		dto.setMail2(mail2);

		// DB
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateArticle(dto);

		JOptionPane.showMessageDialog(this, "수정 하였습니다");
	}

	private void deleteArticle() {

		int index = table.getSelectedRow();
		TableModel data = table.getModel();
		String id = (String) data.getValueAt(index, 2);
		model.removeRow(index);

		// DB
		MemberDAO dao = MemberDAO.getInstance();
		dao.deleteArticle(id);
	}

}
