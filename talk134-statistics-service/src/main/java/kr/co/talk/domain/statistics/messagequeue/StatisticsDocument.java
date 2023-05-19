package kr.co.talk.domain.statistics.messagequeue;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "statistics")
public class StatisticsDocument {
    @Id
    private Long roomId;
    private String name;
    private LocalDateTime endDateTime;
}
