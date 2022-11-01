package pro.sky.telegrambot.Model;

import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private Long id;

    @Column (name = "chat_id")
    private long chatId;

    @Column (name = "message")
    private String text;

    private LocalDateTime dateTime;

    public NotificationTask(long chatId, String text, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public NotificationTask() {

    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }
}
