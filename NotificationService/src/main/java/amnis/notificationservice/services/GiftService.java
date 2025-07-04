package amnis.notificationservice.services;

import amnis.notificationservice.dtos.GiftDTO;
import amnis.notificationservice.models.events.GiftSentEvent;
import amnis.notificationservice.services.websocket.GiftWebsocketBroadcaster;
import org.springframework.stereotype.Service;

@Service
public class GiftService {
    private final GiftWebsocketBroadcaster broadcaster;

    public GiftService(GiftWebsocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void sendGiftNotification(GiftSentEvent event){
        GiftDTO giftDTO = new GiftDTO(formatMessage(event), event.giftType());
        broadcaster.broadcast(event.streamId(), giftDTO);
    }

    private String formatMessage(GiftSentEvent event){
        return  String.format("%s send %ds %s", event.username(), event.value(), event.giftType());
    }
}
