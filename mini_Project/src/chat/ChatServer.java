package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class ChatServer {
	
	private ServerSocket ss;
	private List<ChatHandler> list;
	
	public ChatServer() {
		try {
			ss = new ServerSocket(9500);
			System.out.println("서버 준비 완료");
			
			list = new ArrayList<ChatHandler>();
			
			while(true) {

				Socket socket = ss.accept();
				ChatHandler handler = new ChatHandler(socket, list);//스레드 생성
				handler.start();//스레드시작
				list.add(handler);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new ChatServer();
	}
}
