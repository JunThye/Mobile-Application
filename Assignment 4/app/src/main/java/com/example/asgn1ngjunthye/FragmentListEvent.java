package com.example.asgn1ngjunthye;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asgn1ngjunthye.provider.EMAViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.spec.ECField;
import java.util.ArrayList;

public class FragmentListEvent extends Fragment {
    private EMAViewModel emaViewModel;
    ArrayList<Event> data = new ArrayList<>();


    public FragmentListEvent() {
        // Required empty public constructor
    }

    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_event, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.eventRC);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        eventRecyclerAdapter adapter = new eventRecyclerAdapter();
        adapter.setData(data);
        recyclerView.setAdapter(adapter);

        emaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        emaViewModel.getAllEvent().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(new ArrayList<>(newData));
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}