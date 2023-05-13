package kr.co.talk.global.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.talk.domain.chatroom.dto.ChatEndChatroomDto;

import java.io.IOException;

public class ChatEndChatroomDtoDeserializer extends JsonDeserializer<ChatEndChatroomDto> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatEndChatroomDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long roomId = node.get("roomId").asLong();
        String lastChatSenderName = node.get("lastChatSenderName").asText();
        String lastChatMessage = node.get("lastChatMessage").asText();

        return ChatEndChatroomDto.builder()
                .roomId(roomId)
                .lastChatSenderName(lastChatSenderName)
                .lastChatMessage(lastChatMessage)
                .build();
    }
}
