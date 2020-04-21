package com.pratham.temp;


import androidx.appcompat.app.AppCompatActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    protected void onCreate() {
        // super.onCreate(savedInstanceState);


    }
}
