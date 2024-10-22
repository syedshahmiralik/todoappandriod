package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private ArrayList<String> taskList;
    private int editingPosition = -1; // Track the position of the task being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        // Initialize task list
        taskList = new ArrayList<>();

        // Set up RecyclerView
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerViewTasks.setAdapter(taskAdapter);

        // Add task button click listener
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    if (editingPosition == -1) {
                        // Adding new task
                        taskList.add(task);
                    } else {
                        // Updating existing task
                        taskAdapter.updateTask(editingPosition, task);
                        editingPosition = -1; // Reset editing position
                    }
                    taskAdapter.notifyDataSetChanged();  // Refresh the RecyclerView
                    editTextTask.setText("");  // Clear input field after adding/updating
                }
            }
        });
    }

    @Override
    public void onTaskClick(int position) {
        // When a task is clicked, populate the EditText for editing
        String task = taskList.get(position);
        editTextTask.setText(task);
        editingPosition = position; // Set the current position to edit
    }

    @Override
    public void onTaskDelete(int position) {
        taskList.remove(position);  // Remove the task
        taskAdapter.notifyItemRemoved(position); // Notify adapter of item removal
    }
}