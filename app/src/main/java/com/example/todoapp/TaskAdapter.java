package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<String> taskList;  // List of tasks
    private OnTaskListener onTaskListener; // Interface for task click handling

    // Constructor
    public TaskAdapter(ArrayList<String> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;  // Initialize the task list
        this.onTaskListener = onTaskListener; // Set the listener
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view, onTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind data to the view
        String task = taskList.get(position);
        holder.textViewTask.setText(task);
    }

    @Override
    public int getItemCount() {
        // Return the size of the task list
        return taskList.size();
    }

    // Method to update a task
    public void updateTask(int position, String newTask) {
        taskList.set(position, newTask);
        notifyItemChanged(position);
    }

    // ViewHolder class to hold references to the views for each item
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        Button buttonDelete; // Declare delete button

        public TaskViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);

            // Set click listener for the task item
            itemView.setOnClickListener(v -> {
                if (onTaskListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onTaskListener.onTaskClick(position);
                    }
                }
            });

            // Set up delete button
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(v -> {
                if (onTaskListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onTaskListener.onTaskDelete(position);
                    }
                }
            });
        }
    }

    // Interface for task click handling
    public interface OnTaskListener {
        void onTaskClick(int position);
        void onTaskDelete(int position);
    }
}
