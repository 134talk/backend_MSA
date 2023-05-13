//package kr.co.talk.global.service;
//
//import kr.co.talk.domain.chatroom.dto.ChatDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Optional;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class ChatRedisService {
//
//    //Redis CacheKeys
//    private static final String CHAT_ROOMS = "CHAT_ROOM"; //채팅룸 저장
//    public static final String USER_COUNT = "USER_COUNT"; //채팅룸에 입장한 클라이언트수 저장
//    public static final String ENTER_INFO = "ENTER_INFO"; //채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
//
//    @Autowired
//    private RedisTemplate<String, ChatDto> redisChatRoomTemplate;
//
//
//    @PostConstruct
//    private void init() {
//        redisChatRoomTemplate.opsForList();
//    }
//
//    private HashOperations<String, ChatDto> hashOpsChatRoom;
//
//    private HashOperations<String, String, String> hashOpsEnterInfo;
//
//    private ValueOperations<String, String> valueOps;
//
//    private final ChannelTopic channelTopic;
//
//    //채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
//    public ChatRoom createChatRoom(String title, Member member, Playlist playlist) {
//        ChatRoom chatRoom = ChatRoom.builder()
//                .roomId(String.valueOf(UUID.randomUUID()))
//                .member(member)
//                .playlistId(playlist.getPlaylistId())
//                .title(title)
//                .build();
//
//        hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }
//
//    //입장한 roomId와 입장한 사람의 sessionId 맵핑 정보 저장
//    public void setUserEnterInfo(String sessionId, String roomId) {
//        hashOpsEnterInfo.put(ENTER_INFO,sessionId,roomId);
//    }
//
//    //사용자 세션으로 입장해있는 roomId 조회
//    public String getUserEnterRoomId(String sessionId) {
//        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
//    }
//
//    //사용자 세션정보와 맵핑된 roomId 삭제
//    public void removeUserEnterInfo(String sessionId) {
//        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
//    }
//
//    //채팅방 userCount 조회
//    public Long getUserCount(String roomId) {
//        return Long.valueOf(Optional.ofNullable(valueOps.get(USER_COUNT + " " + roomId)).orElse("0"));
//    }
//
//    //userCount + 1
//    public Long plusUserCount(String roomId) {
//        return Long.valueOf(Optional.ofNullable(valueOps.increment(USER_COUNT + " " + roomId )).orElse(0L));
//    }
//
//    //userCount - 1
//    public Long minusUserCount(String roomId) {
//        return Long.valueOf(Optional.ofNullable(valueOps.decrement(USER_COUNT + " " + roomId)).filter(count -> count > 0).orElse(0L));
//    }
//
//    //모든 채팅방 조회
//    public List<ChatRoom> findAllRoom() {
//        return hashOpsChatRoom.values(CHAT_ROOMS);
//    }
//
//    //특정 채팅방 조회
//    public ChatRoom findRoomById(String roomId) {
//        return hashOpsChatRoom.get(CHAT_ROOMS, roomId);
//    }
//
//    public String getRoomId(String destination) {
//        int lastIndex = destination.lastIndexOf('/');
//        if (lastIndex != -1) {
//            return destination.substring(lastIndex + 1);
//        } else return "";
//    }
//
//    public void sendMessage(ChatMessage chatMessage) {
//        chatMessage.setUserCount(getUserCount(chatMessage.getRoomId()));
//        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
//            chatMessage.setMessage(chatMessage.getMemberName() + "님이 입장하셨습니다.");
//            chatMessage.setMemberName("[알림]");
//        } else if(chatMessage.getType().equals(ChatMessage.MessageType.LEAVE)) {
//            chatMessage.setMessage(chatMessage.getMemberName() + "님이 나가셨습니다.");
//            chatMessage.setMemberName("[알림]");
//        }
//        redisStringTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
//    }
//}
