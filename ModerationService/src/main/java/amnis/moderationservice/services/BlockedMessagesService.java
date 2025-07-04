package amnis.moderationservice.services;

import amnis.moderationservice.dtos.BlockedMessageDTO;
import amnis.moderationservice.dtos.BlockedMessagesDTO;
import amnis.moderationservice.models.BlockedMessages;
import amnis.moderationservice.models.Message;
import amnis.moderationservice.models.events.ChatMessageSentEvent;
import amnis.moderationservice.repository.BlacklistMessagesRepository;
import amnis.moderationservice.repository.BlockedMessagesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlockedMessagesService {
    private final BlockedMessagesRepository blockedMessagesRepository;


    public BlockedMessagesService(BlockedMessagesRepository blockedMessagesRepository) {
        this.blockedMessagesRepository = blockedMessagesRepository;
    }
    public BlockedMessagesDTO getBlockedMessages(Long streamId) {
        Optional<BlockedMessages> blockedMessages = blockedMessagesRepository.findByStreamId(streamId);
        if (blockedMessages.isEmpty()) {
            return new BlockedMessagesDTO(new ArrayList<>());
        }

        BlockedMessages blockedMessagesEntity = blockedMessages.get();

        return new BlockedMessagesDTO(
                blockedMessagesEntity
                        .getMessages()
                        .stream()
                        .map(
                m -> new BlockedMessageDTO(m.getUsername(),m.getMessage(), m.getTimestamp(), m.getBlockedAt())
        ).toList());
    }
    public void addBlockedMessage(Long streamId, ChatMessageSentEvent event) {
        Message message = new Message(event.username(), event.message(), event.timestamp());
        Optional<BlockedMessages> blockedMessages = blockedMessagesRepository.findByStreamId(streamId);
        if (blockedMessages.isEmpty()) {
            BlockedMessages blockedMessagesEntity = new BlockedMessages();
            blockedMessagesEntity.setStreamId(streamId);
            blockedMessagesEntity.addMessage(message);
            blockedMessagesRepository.save(blockedMessagesEntity);
            return;
        }

        BlockedMessages blockedMessagesEntity = blockedMessages.get();
        blockedMessagesEntity.addMessage(message);
        blockedMessagesRepository.save(blockedMessagesEntity);
    }
}
