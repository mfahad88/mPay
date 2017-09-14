package com.example.bipl.util;

public interface EditDialogListener {
        void imageString(byte[] inputText);
        void imageString(String inputText);
        void capturedImage(Boolean status);
    }