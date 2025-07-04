package com.amnis.StreamGateway.controllers;

import com.amnis.StreamGateway.dtos.GiftDTO;
import com.amnis.StreamGateway.dtos.UserJoinedDTO;
import com.amnis.StreamGateway.services.GiftService;
import com.amnis.StreamGateway.services.UserJoinService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("streams")
public class StreamGatewayController {

    private final UserJoinService userJoinService;
    private final GiftService giftService;

    public StreamGatewayController(UserJoinService userJoinService, GiftService giftService) {
        this.userJoinService = userJoinService;
        this.giftService = giftService;
    }

    @PostMapping("{streamId}/join")
    public ResponseEntity<Void> join(@PathVariable Long streamId, @RequestBody @Valid UserJoinedDTO userJoinedDTO) {
        userJoinService.send(streamId, userJoinedDTO);
        return ResponseEntity.ok().build();
    }
    @PostMapping("{streamId}/gift")
    private ResponseEntity<Void> gift(@PathVariable Long streamId, @RequestBody @Valid GiftDTO giftDTO) {
        giftService.send(streamId, giftDTO);
        return ResponseEntity.ok().build();
    }
}
