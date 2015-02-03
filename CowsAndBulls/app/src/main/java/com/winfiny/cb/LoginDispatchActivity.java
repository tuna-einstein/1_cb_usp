package com.winfiny.cb;

import com.parse.ui.ParseLoginDispatchActivity;

public class LoginDispatchActivity extends ParseLoginDispatchActivity {

    @Override
    protected Class<?> getTargetClass() {
        return MainActivity.class;
    }
}