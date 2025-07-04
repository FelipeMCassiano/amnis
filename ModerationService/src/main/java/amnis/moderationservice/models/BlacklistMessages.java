package amnis.moderationservice.models;

import amnis.moderationservice.dtos.BlackListMessagesDTO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document("tb_blacklist_messages")
public class BlacklistMessages {
    @MongoId
    private String id;
    @Indexed
    private Long streamId;
    private List<String> blackListKeywords = new ArrayList<>();

    public Long getStreamId() {
        return streamId;
    }

    public BlacklistMessages (Long streamId,BlackListMessagesDTO dto) {
        this.streamId = streamId;
        this.blackListKeywords = dto.blacklist();
    }

    public BlacklistMessages () {
    }

    public List<String> getBlackListKeywords() {
        return blackListKeywords;
    }
    public void addBlackListKeyword(String keyword) {
        blackListKeywords.add(keyword);
    }
    public void addBlackListKeywords(List<String> keywords) {
        blackListKeywords.addAll(keywords);
    }


}
