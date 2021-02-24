package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs; // select 쓸 시

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String userName = "c##java";
	private String password = "bit";
	private static MemberDAO instance;

	public MemberDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}// driver 1.로딩

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// getConnection() 2.접속

	public static MemberDAO getInstance() {
		if (instance == null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}
		}
		return instance;
	}// 싱글톤

	public void onoff(int onoff, String id) {
		String sql = "update member set onoff = ? where id =?";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, onoff);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// onoff 기능

	public void setTime(int time, String id) {
		String sql = "update member set time=? where id=?";

		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, time);
			pstmt.setString(2, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int idCheck(String id) {
		int result = 0;
		String sql = "select * from member where id=?";

		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("id").equals(id))
					result = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}// idCheck()

	public int getUserTime(String id) {
		int dbTime = 0;
		getConnection();

		String sql = "select time from member where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dbTime = rs.getInt("time");
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dbTime;
	}

	public void insertArticle(MemberDTO dto) { // 다른점 : int su 잡지 않았음.
		String sql = "insert into member(SEQ, NAME, ID, PASSWORD, AGE, TEL1, TEL2, TEL3, MAIL1, MAIL2) values (?,?,?,?,?,?,?,?,?,?)";
		// DB 테이블값엔 인덱스 6번에 time들어가 있음. insert값에는 time데이터 없음.
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getSeq());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getPw());
			pstmt.setInt(5, dto.getAge());
			pstmt.setString(6, dto.getTel1());
			pstmt.setString(7, dto.getTel2());
			pstmt.setString(8, dto.getTel3());
			pstmt.setString(9, dto.getMail1());
			pstmt.setString(10, dto.getMail2());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// insertArticle()

	public void updateArticle(MemberDTO dto) {
		String sql = "update member set name=?, age=?, password=?, tel1=?, tel2=?, tel3=?, mail1=?, mail2=? where id=?";
		getConnection();
		System.out.print(dto.getId() + "," + dto.getName() + "," + dto.getAge() + "," + dto.getPw() + ","
				+ dto.getTel1() + "," + dto.getTel2() + "," + dto.getTel3() + "," + dto.getMail1() + ","
				+ dto.getMail2() + "," + dto.getId());
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getPw());
			pstmt.setString(4, dto.getTel1());
			pstmt.setString(5, dto.getTel2());
			pstmt.setString(6, dto.getTel3());
			pstmt.setString(7, dto.getMail1());
			pstmt.setString(8, dto.getMail2());
			pstmt.setString(9, dto.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// updateArticle

	// 로그인 서비스
	public MemberDTO LoginSevice(String id, String pw) {
		MemberDTO dto = null;
		String sql = "select seq, name, age, id, password, tel1, tel2, tel3, mail1, mail2, time from member where id = ? and password = ? ";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, id);
			pstmt.setNString(2, pw);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new MemberDTO();
				if (id.equals(rs.getString("id")) && pw.equals(rs.getString("password"))) {
					dto.setSeq(rs.getInt("seq"));
					dto.setName(rs.getString("name"));
					dto.setId(rs.getString("ID"));
					dto.setPw(rs.getString("password"));
					dto.setAge(rs.getInt("age"));
					dto.setTel1(rs.getString("tel1"));
					dto.setTel2(rs.getString("tel2"));
					dto.setTel3(rs.getString("tel3"));
					dto.setMail1(rs.getString("mail1"));
					dto.setMail2(rs.getString("mail2"));
					dto.setTime(rs.getInt("time"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	// 아이디 찾기
	public String idSearch(String nameT, String mailT1, String mailT2) {
		String sql = "select id, name, mail1, mail2 from member where name = ? and mail1 = ? and mail2 = ?";
		String idg = null;
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, nameT);
			pstmt.setNString(2, mailT1);
			pstmt.setNString(3, mailT2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (nameT.equals(rs.getString("name")) && mailT1.equals(rs.getString("mail1"))
						&& mailT2.equals(rs.getString("mail2"))) {
					idg = rs.getString("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return idg;
	}

	// pw 찾기
	public String pwSearch(String idT, String mailT1, String mailT2) {
		String sql = "select password, id, mail1, mail2 from member where id = ? and mail1 = ? and mail2 = ?";
		String pwg = null;
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, idT);
			pstmt.setNString(2, mailT1);
			pstmt.setNString(3, mailT2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (idT.equals(rs.getString("id")) && mailT1.equals(rs.getString("mail1"))
						&& mailT2.equals(rs.getString("mail2"))) {
					pwg = rs.getString("password");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pwg;
	}//

	// 중복검사
	public int same(String idT) {
		String sql = "select id from member where id = ?";
		int i = 1;
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, idT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (idT.equals(rs.getString("id"))) {
					i = 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(i);
		return i;
	}// same()

	public void deleteArticle(String id) {
		String sql = "delete member where id = ?";
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // CHECK
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// deleteArticle

	public int getSeq() {
		int seq = 0;
		String sql = "select seq_member.nextval from dual";
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 생성
			rs = pstmt.executeQuery(); // 실행 //rs에 받아온다.

			rs.next(); // nextval이 1개밖에 없기때문에 while문을 쓸 필요가 없다. 항목이 1개밖에 없으니까.
			seq = rs.getInt(1); // 첫번째 컬럼값을 가져와서 seq에 던져줘라.

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seq;
	}// getSeq()

	public List<MemberDTO> getMemberList() {
		List<MemberDTO> dtoList = new ArrayList<MemberDTO>();
		String sql = "select * from member order by seq";
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);// 생성
			rs = pstmt.executeQuery();// 실행

			while (rs.next()) {
				MemberDTO dto1 = new MemberDTO();
				dto1.setSeq(rs.getInt("seq"));
				dto1.setName(rs.getString("name"));
				dto1.setId(rs.getString("ID"));
				dto1.setPw(rs.getString("password"));
				dto1.setAge(rs.getInt("age"));
				dto1.setTel1(rs.getString("tel1"));
				dto1.setTel2(rs.getString("tel2"));
				dto1.setTel3(rs.getString("tel3"));
				dto1.setMail1(rs.getString("mail1"));
				dto1.setMail2(rs.getString("mail2"));
				dto1.setTime(rs.getInt("time"));
				dtoList.add(dto1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dtoList;
	}

	public void setSeatOut(String msg, String userID) {
		getConnection();
		pstmt = null;
		String sql = "update member set seat = ? where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg);
			pstmt.setString(2, userID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 로그인 서비스
	public MemberDTO adminuserinfo(int onoff, int seat) {
		MemberDTO dto = null;
		String sql = "select seq, name, age, id, password, tel1, tel2, tel3, mail1, mail2, time from member where onoff = ? and seat = ? ";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, onoff);
			pstmt.setInt(2, seat);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new MemberDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("ID"));
				dto.setPw(rs.getString("password"));
				dto.setAge(rs.getInt("age"));
				dto.setTel1(rs.getString("tel1"));
				dto.setTel2(rs.getString("tel2"));
				dto.setTel3(rs.getString("tel3"));
				dto.setMail1(rs.getString("mail1"));
				dto.setMail2(rs.getString("mail2"));
				dto.setTime(rs.getInt("time"));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}
