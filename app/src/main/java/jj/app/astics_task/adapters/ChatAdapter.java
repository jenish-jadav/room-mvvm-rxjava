package jj.app.astics_task.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jj.app.astics_task.R;
import jj.app.astics_task.database.ChatMessages;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatMessages> chatMessagesList;

    public ChatAdapter() {
        this.chatMessagesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_chat, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_chat, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessages chat = chatMessagesList.get(position);
        holder.txt_message.setText(chat.getMessage());
        holder.txt_time.setText(chat.getDatetime());

        if (holder.txt_to_username != null) {
            // TODO: 23-02-2020 usermaster table require to get user name from database
            holder.txt_to_username.setText("Upendra Shah (Manager)");
        }
    }

    public void updateChats(List<ChatMessages> messagesList) {
        this.chatMessagesList = messagesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatMessagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessages chat = chatMessagesList.get(position);
        return chat.getFromUserId() == 1 ? 1 : 0;
        //1 = myView
        //2 = other side
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_message, txt_time, txt_to_username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_message = itemView.findViewById(R.id.txt_message);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_to_username = itemView.findViewById(R.id.txt_to_username);
        }
    }
}
