package pro.sky.telegrambot.repository;


import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.Model.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask,Long> {
     List<NotificationTask> findByDateTime(LocalDateTime date);
}
