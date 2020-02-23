package jj.app.astics_task;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import jj.app.astics_task.database.ChatMessages;
import jj.app.astics_task.database.ChatMessagesDao;

public class DatabaseRepository {

    private ChatMessagesDao messagesDao;

    public DatabaseRepository(ChatMessagesDao messagesDao) {
        this.messagesDao = messagesDao;
    }

    public Flowable<List<ChatMessages>> getAllChats() {
        return messagesDao.getChats();
    }

    public Single<Long> addMessage(ChatMessages chatMessages) {
        return messagesDao.addMessage(chatMessages);
    }
}
