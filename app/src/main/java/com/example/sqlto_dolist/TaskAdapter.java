package com.example.sqlto_dolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Task> taskList;


    public TaskAdapter(MainActivity context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvName;
        ImageView ivEdit, ivDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            holder.tvName = view.findViewById(R.id.textViewName);
            holder.ivDelete = view.findViewById(R.id.imageViewDelete);
            holder.ivEdit = view.findViewById(R.id.imageViewEdit);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Task task = taskList.get(i);

        holder.tvName.setText(task.getTaskName());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showEditDialog(task.getTaskName(), task.getId());
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showDialogDelete(task.getTaskName(), task.getId());
            }
        });
        return view;
    }
}
