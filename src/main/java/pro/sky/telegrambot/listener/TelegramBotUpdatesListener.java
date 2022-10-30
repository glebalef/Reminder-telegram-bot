package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final NotificationTaskRepository notificationTaskRepository;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
                    logger.info("Processing update: {}", update);
                    // Process your updates here
                    if (Objects.equals(update.message().text(), "/start")) {
                        SendMessage message = new SendMessage(update.message().chat().id(), "привет тебе от бота!");
                        SendResponse response = telegramBot.execute(message);
                    } else {
                        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)"); // format like "01.01.1991 here_is_text"
                        Matcher matcher = pattern.matcher(update.message().text());

                        if (!matcher.matches()) {

                            // processing pattern-invalid requests

                            logger.warn("invalid format of request");
                            SendMessage message = new SendMessage(update.message().chat().id(), "запрос не соответвует формату");
                            SendResponse response = telegramBot.execute(message);

                        } else {

                            // save to database if request is patter-valid

                            String date = matcher.group(1); // date + time from request
                            String item = matcher.group(3); // text part of request

                            logger.info("request saved: chatID: {}, DateTime: {}, Text: {}",update.message().chat().id(),date, item );

                            notificationTaskRepository.save(new NotificationTask(
                                    (long) update.updateId(),
                                    update.message().chat().id(),
                                    item,
                                    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

                            ));
                        }
                    }
                }
        );
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    // send reminders according to schedule
    @Scheduled(cron = "0 0/1 * * * *")
    public void getEventsCollection() {
        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> eventsList = notificationTaskRepository.findByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        eventsList.forEach(notificationTask -> {
            if (notificationTask.getDateTime().equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
                SendMessage message = new SendMessage(notificationTask.getChatId(), notificationTask.getText());
                logger.info ("reminder '{}' sent to chatID '{}'", notificationTask.getText(), notificationTask.getChatId());
                SendResponse response = telegramBot.execute(message);
            }
        });
    }
}

