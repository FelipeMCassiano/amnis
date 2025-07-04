package amnis.moderationservice.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document("tb_blocked_messages")
public class BlockedMessages{
    @MongoId
    private String id;
    @Indexed
    private Long streamId;
    List<Message> messages = new ArrayList<>();

    public void addMessage(Message message){
        messages.add(message);
    }

    public List<Message> getMessages(){
        return messages;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }
}
