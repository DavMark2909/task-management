package application.service;

import application.entity.chat.ChatRoom;
import application.entity.chat.Message;
import application.repository.ChatRoomRepository;
import application.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository msgRepository;
    private ChatRoomRepository chatRepository;

    Message saveMessage(Message msg){
        return msgRepository.save(msg);
    }

    ChatRoom saveRoom(ChatRoom room){
        return chatRepository.save(room);
    }
}
