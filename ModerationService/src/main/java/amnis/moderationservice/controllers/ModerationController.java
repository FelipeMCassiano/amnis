package amnis.moderationservice.controllers;

import amnis.moderationservice.dtos.BlackListMessagesDTO;
import amnis.moderationservice.dtos.BlockedMessagesDTO;
import amnis.moderationservice.services.BlockedMessagesService;
import amnis.moderationservice.services.ModerationService;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mod")
public class ModerationController {
    private final ModerationService moderationService;
    private final BlockedMessagesService blockedMessagesService;

    public ModerationController(ModerationService moderationService, BlockedMessagesService blockedMessagesService) {
        this.moderationService = moderationService;

        this.blockedMessagesService = blockedMessagesService;
    }

    @PostMapping("{streamId}")
    public ResponseEntity<Void> addBlacklist(@PathVariable Long streamId,@RequestBody BlackListMessagesDTO dto) {
        moderationService.addBlacklistedMessages(streamId, dto);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{streamId}")
    public ResponseEntity<BlackListMessagesDTO> get(@PathVariable Long streamId) {
        BlackListMessagesDTO response = moderationService.getBlacklistedMessages(streamId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("{streamId}/blocked")
    public ResponseEntity<BlockedMessagesDTO> getBlocked(@PathVariable Long streamId) {
        BlockedMessagesDTO response = blockedMessagesService.getBlockedMessages(streamId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
