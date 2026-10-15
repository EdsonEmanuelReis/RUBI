package site.rubi.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.rubi.DTO.ConversationDto;

import java.util.List;

@Repository
public class ConversationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConversationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long create(String title) {
        String database = jdbcTemplate.queryForObject(
                "PRAGMA database_list",
                (rs, rowNum) -> rs.getString("file")
        );

        System.out.println("BANCO USADO PELO RUBI: " + database);

        jdbcTemplate.update(
                "INSERT INTO chat_conversation (title) VALUES (?)",
                title
        );

        return jdbcTemplate.queryForObject(
                "SELECT last_insert_rowid()",
                Long.class
        );
    }

    public List<ConversationDto> findAll() {
        String sql = """
                SELECT id, title, created_at
                FROM chat_conversation
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ConversationDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("created_at")
                )
        );
    }


    public boolean existsById(long conversationId) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM chat_conversation
                WHERE id = ?
                """,
                Integer.class,
                conversationId
        );

        return count != null && count > 0;
    }


    public boolean rename(long conversationId, String title) {
        int rowsUpdated = jdbcTemplate.update(
                """
                UPDATE chat_conversation
                SET title = ?
                WHERE id = ?
                """,
                title,
                conversationId
        );

        return rowsUpdated > 0;
    }


    public boolean deleteById(long conversationId) {
        int rowsDeleted = jdbcTemplate.update(
                """
                DELETE FROM chat_conversation
                WHERE id = ?
                """,
                conversationId
        );

        return rowsDeleted > 0;
    }

    public boolean updateAutomaticTitle(long conversationId, String title) {
        int rowsUpdated = jdbcTemplate.update(
                """
                UPDATE chat_conversation
                SET title = ?
                WHERE id = ?
                  AND title = 'Nova conversa'
                """,
                title,
                conversationId
        );

        return rowsUpdated > 0;
    }

}
