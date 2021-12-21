package com.webdoc.ibcc.Testing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.webdoc.ibcc.databinding.ActivityTestBinding;

public class test extends AppCompatActivity {
    private ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}