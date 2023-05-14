package kr.co.talk.domain.statistics.messagequeue;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<StatisticsDocument, Long> {
}
