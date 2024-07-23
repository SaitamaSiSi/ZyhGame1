package com.zyh.ZyhG1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.Msg;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView _leftMsg;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            _leftMsg = itemView.findViewById(R.id.left_msg);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        TextView _rightMsg;

        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            _rightMsg = itemView.findViewById(R.id.right_msg);
        }
    }

    List<Msg> _msgList;

    public MsgAdapter(List<Msg> msgList) {
        _msgList = msgList;
    }

    @Override
    public int getItemViewType(int position) {
        Msg msg = _msgList.get(position);
        return msg._type;
        //return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Msg.TYPE_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_left_item,
                    parent, false);
            return new LeftViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_right_item,
                    parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Msg msg = _msgList.get(position);
        if (holder instanceof LeftViewHolder) {
            ((LeftViewHolder) holder)._leftMsg.setText(msg._content);
        } else if (holder instanceof RightViewHolder) {
            ((RightViewHolder) holder)._rightMsg.setText(msg._content);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getItemCount() {
        return _msgList.size();
    }
}
