package me.wonwoo.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by wonwoo on 2017. 3. 15..
 */
@Slf4j
public class DefaultWebSocketHandler extends TextWebSocketHandler {

  private List<WebSocketSession> sessions = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.debug("Opened new session in instance " + this);
    sessions.add(session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    try {
      session.sendMessage(new TextMessage("message"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception)
    throws Exception {
    session.close(CloseStatus.SERVER_ERROR);
    sessions.remove(session);
  }
}
