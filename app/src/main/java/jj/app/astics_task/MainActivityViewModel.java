package jj.app.astics_task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jj.app.astics_task.database.ChatMessages;
import jj.app.astics_task.database.ChatMessagesDao;
import jj.app.astics_task.database.DatabaseHelper;
import jj.app.astics_task.helper.AppUtil;

public class MainActivityViewModel extends AndroidViewModel {
    private DatabaseRepository repository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<ChatMessages>> chatMessages;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        DatabaseHelper db = DatabaseHelper.getInstance(getApplication());
        ChatMessagesDao messagesDao = db.chatMessagesDao();
        this.repository = new DatabaseRepository(messagesDao);
        compositeDisposable = new CompositeDisposable();
        chatMessages = new MutableLiveData<>();
    }


    public void init() {
        Disposable favoriteShowsDisposable = repository.getAllChats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMessageReceived, this::onError);
        compositeDisposable.add(favoriteShowsDisposable);
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        AppUtil.log(throwable.toString());
    }

    private void onMessageReceived(List<ChatMessages> favoriteShows) {
        chatMessages.setValue(favoriteShows);
    }

    public LiveData<List<ChatMessages>> getAllChats() {
        return chatMessages;
    }

    public LiveData<Long> addMessage(ChatMessages chatMessages) {
        MutableLiveData<Long> returnId = new MutableLiveData<>();

        repository.addMessage(chatMessages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        returnId.setValue(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


        return returnId;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
