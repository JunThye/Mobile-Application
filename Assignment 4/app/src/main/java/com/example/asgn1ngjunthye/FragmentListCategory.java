package com.example.asgn1ngjunthye;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
import java.util.ArrayList;
import java.util.List;


public class FragmentListCategory extends Fragment {
    private EMAViewModel emaViewModel;
    ArrayList<Category> data = new ArrayList<>();


    public FragmentListCategory() {
        // Required empty public constructor
    }

    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
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
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.categoryRC);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        categoryRecyclerAdapter adapter = new categoryRecyclerAdapter();
        adapter.setData(data);
        recyclerView.setAdapter(adapter);

        emaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        emaViewModel.getAllCategory().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(new ArrayList<>(newData));
            adapter.notifyDataSetChanged();
        });

        return view;
    }
}