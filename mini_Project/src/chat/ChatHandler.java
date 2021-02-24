package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import model.MemberDTO;

public class ChatHandler extends Thread {
	private Socket socket;
	private List<ChatHandler> list;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private int i = 0;
	public ChatHandler(Socket socket, List<ChatHandler> list) throws IOException {
		this.socket = socket;
		this.list = list;
		//
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());

	}

	@Override
	public void run() {
		// 클라이언트로 부터 받는 쪽
		MemberDTO dto = null;
		String nickName = null;
		while (true) {
			try {
				dto = (MemberDTO) ois.readObject();

				if (i == 0) {
					// 모든 클라이언트에게 입장 메세지를 보내기
					MemberDTO sendDTO = new MemberDTO();

					nickName = dto.getId();
					sendDTO.setMsg(nickName + "님이 입장하였습니다.");
					broadcast(sendDTO);
					i++;
				} else if (i != 0) {
					MemberDTO sendDTO = new MemberDTO();

					sendDTO.setMsg("[" + nickName + "] " + dto.getMsg());
					broadcast(sendDTO);
				} else {
					MemberDTO sendDTO = new MemberDTO();
					// quit를 보낸 클라이언트에게 quit를 보내기
					oos.writeObject(sendDTO);
					oos.flush();

					ois.close();
					oos.close();
					socket.close();
					// 남은 클라이언트에게 퇴장메세지 보내기
					list.remove(this);
					nickName = dto.getId();
					sendDTO.setMsg(nickName + "님이 퇴장하였습니다.");
					broadcast(sendDTO);

					break;

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}// run

	public void broadcast(MemberDTO sendDTO) {
		for (ChatHandler handler : list) {
			try {
				handler.oos.writeObject(sendDTO);
				handler.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}