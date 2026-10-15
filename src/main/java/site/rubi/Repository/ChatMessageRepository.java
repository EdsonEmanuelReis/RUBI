package site.rubi.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.rubi.DTO.ChatMessageDto;

import java.util.List;

@Repository
public class ChatMessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChatMessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(
            long conversationId,
            String userMessage,
            String assistantResponse
    ) {
        jdbcTemplate.update(
                """
                INSERT INTO chat_message
                    (conversation_id, user_message, assistant_response)
                VALUES (?, ?, ?)
                """,
                conversationId,
                userMessage,
                assistantResponse
        );
    }

    public List<ChatMessageDto> findByConversationId(long conversationId) {
        String sql = """
            SELECT id, user_message, assistant_response, created_at
            FROM chat_message
            WHERE conversation_id = ?
            ORDER BY id ASC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ChatMessageDto(
                                rs.getLong("id"),
                                rs.getString("user_message"),
                                rs.getString("assistant_response"),
                                rs.getString("created_at")
                        ),
                conversationId
        );
    }


    public int deleteByConversationId(long conversationId) {
        return jdbcTemplate.update(
                """
                DELETE FROM chat_message
                WHERE conversation_id = ?
                """,
                conversationId
        );
    }

    public boolean hasMessages(long conversationId) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM chat_message
                WHERE conversation_id = ?
                """,
                Integer.class,
                conversationId
        );

        return count != null && count > 0;
    }

}
