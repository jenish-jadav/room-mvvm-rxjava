package jj.app.astics_task.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ChatMessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> addMessage(ChatMessages chatMessages);

    @Query("SELECT * from chats")
    Flowable<List<ChatMessages>> getChats();
}
