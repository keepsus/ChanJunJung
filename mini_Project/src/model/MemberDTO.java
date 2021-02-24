package model;

import java.io.Serializable;


enum Info {
	JOIN, EXIT, SEND
}

public class MemberDTO implements Serializable{ //회원가입정보
	//필드
	private static final long seriaLVersionUID = 1L;
	private int seq;
	private int time; 
	private int age;
	private int postPayment;
	private String name;
	private String id;
	private String pw; 
	private String tel1;
	private String tel2;
	private String tel3;
	private String mail1;
	private String mail2;
	private String msg;
	private int onoff;
	private int seat;
	private Info command;
	
	public Info getCommand() {
		return command;
	}
	public void setCommand(Info command) {
		this.command = command;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public int getOnoff() {
		return onoff;
	}
	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getTime () {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getMail1() {
		return mail1;
	}
	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}
	public String getMail2() {
		return mail2;
	}
	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}
	public int getPostPayment() {
		return postPayment;
	}
	public void setPostPayment(int postPayment) {
		this.postPayment = postPayment;
	}
	
	//세터,게터
	
}
