package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    public static List<Integer> items = new ArrayList<>();

    static {
        for(int i = 0; i < 150; i++){
            items.add(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerAdapter(items));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
    }

    static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        private List<Integer> items = new ArrayList();

        public RecyclerAdapter(List items){
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(items.get(position));
        }


        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.number);
            }

            public void bind(int number){
                textView.setText(String.valueOf(number));
            }

        }
    }

}