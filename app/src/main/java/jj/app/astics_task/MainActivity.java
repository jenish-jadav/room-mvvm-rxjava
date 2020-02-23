package jj.app.astics_task;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import jj.app.astics_task.adapters.ChatAdapter;
import jj.app.astics_task.database.ChatMessages;
import jj.app.astics_task.helper.DateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MainActivityViewModel viewModel;
    RecyclerView recyclerView;
    ChatAdapter adapter;
    TextInputEditText etMyMessage, etOtherMessage;
    MaterialButton btn_me, btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getMessages();
    }

    private void init() {
        etMyMessage = findViewById(R.id.et_my_message);
        etOtherMessage = findViewById(R.id.et_other_message);
        btn_me = findViewById(R.id.btn_me);
        btn_other = findViewById(R.id.btn_other);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.init();

        btn_me.setOnClickListener(this::onClick);
        btn_other.setOnClickListener(this::onClick);
    }

    private void sendMessage(int fromUserId, int toUserId, String message) {
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setMessage(message);
        chatMessages.setFromUserId(fromUserId);
        chatMessages.setToUserId(toUserId);
        chatMessages.setDatetime(DateUtil.getCurrentDate(DateUtil.Format.DATABASE));

        viewModel.addMessage(chatMessages).observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long returnId) {
                etMyMessage.setText("");
                etOtherMessage.setText("");
            }
        });
    }

    private void getMessages() {
        viewModel.getAllChats().observe(this, new Observer<List<ChatMessages>>() {
            @Override
            public void onChanged(List<ChatMessages> chatMessages) {
                adapter.updateChats(chatMessages);
                if (adapter.getItemCount() > 0) {
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == btn_me) {
            if (!TextUtils.isEmpty(etMyMessage.getText())) {
                sendMessage(1, 2, String.valueOf(etMyMessage.getText()));
            }
        } else if (view == btn_other) {
            if (!TextUtils.isEmpty(etOtherMessage.getText())) {
                sendMessage(2, 1, String.valueOf(etOtherMessage.getText()));
            }
        }
    }
}
