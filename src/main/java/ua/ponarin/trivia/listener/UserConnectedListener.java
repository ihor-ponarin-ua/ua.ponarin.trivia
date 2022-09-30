package ua.ponarin.trivia.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import ua.ponarin.trivia.service.PlayersService;

@Component
@RequiredArgsConstructor
public class UserConnectedListener implements ApplicationListener<SessionSubscribeEvent> {
    private final PlayersService playersService;

    @Value("${ws.topic.players}")
    private String playersTopic;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        var subscribedTopic = event.getMessage().getHeaders().get("simpDestination");
        if (playersTopic.equals(subscribedTopic)) {
            playersService.playerConnected(event.getUser());
        }
    }
}
