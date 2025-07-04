package amnis.moderationservice.services;

import amnis.moderationservice.dtos.BlackListMessagesDTO;
import amnis.moderationservice.models.BlacklistMessages;
import amnis.moderationservice.models.events.ChatMessageSentEvent;
import amnis.moderationservice.repository.BlacklistMessagesRepository;
import amnis.moderationservice.repository.BlockedMessagesRepository;
import amnis.moderationservice.services.websocket.ModerationBroadcaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ModerationService {
    @Value("${api.chatMessage.filtered.topic}")
    private String filteredTopic;
    private final BlacklistMessagesRepository blacklistMessagesRepository;
    private final BlockedMessagesService blockedMessagesService;

    private final ModerationBroadcaster broadcaster;
    private final KafkaTemplate<String, ChatMessageSentEvent> kafkaTemplate;

    public ModerationService(BlacklistMessagesRepository repository, BlockedMessagesService blockedMessagesService, ModerationBroadcaster broadcaster, KafkaTemplate<String, ChatMessageSentEvent> kafkaTemplate) {
        this.blacklistMessagesRepository = repository;
        this.blockedMessagesService = blockedMessagesService;
        this.broadcaster = broadcaster;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void addBlacklistedMessages(Long streamId, BlackListMessagesDTO dto) {
        if (dto.blacklist().isEmpty()){
            return;
        }
        Optional<BlacklistMessages> optionalBlacklistMessages = blacklistMessagesRepository.findByStreamId(streamId);
        if (optionalBlacklistMessages.isEmpty()) {
            BlacklistMessages blacklistMessages = new BlacklistMessages(streamId, dto);
            blacklistMessagesRepository.save(blacklistMessages);
            return;
        }
        BlacklistMessages blacklistMessages = optionalBlacklistMessages.get();
        blacklistMessages.addBlackListKeywords(dto.blacklist());
        blacklistMessagesRepository.save(blacklistMessages);
    }
    public BlackListMessagesDTO getBlacklistedMessages(Long streamId) {
        Optional<BlacklistMessages> optionalBlacklistMessages = blacklistMessagesRepository.findByStreamId(streamId);
        if (optionalBlacklistMessages.isEmpty()) {
            return new BlackListMessagesDTO(new ArrayList<>());
        }
        BlacklistMessages blacklistMessages = optionalBlacklistMessages.get();

        return new BlackListMessagesDTO(blacklistMessages.getBlackListKeywords());
    }

    public void filterMessage(ChatMessageSentEvent event) {
        Long streamId = event.streamId();

        Optional<BlacklistMessages> optionalBlackListedMessage = blacklistMessagesRepository.findByStreamId(streamId);
        if (optionalBlackListedMessage.isEmpty()) {
            kafkaTemplate.send(filteredTopic, event);
            return;
        }
        BlacklistMessages blacklistMessages = optionalBlackListedMessage.get();
        int oldSize = blacklistMessages.getBlackListKeywords().size();

        SpamService spamService = new SpamService(blacklistMessages);

        if (spamService.isSpam(event.message())){
            if (oldSize != blacklistMessages.getBlackListKeywords().size()) {
                blacklistMessagesRepository.save(blacklistMessages);
            }
            blockedMessagesService.addBlockedMessage(streamId, event);
            broadcaster.broadcast(streamId, event);
            return;
        }
        kafkaTemplate.send(filteredTopic, event);
    }

}
