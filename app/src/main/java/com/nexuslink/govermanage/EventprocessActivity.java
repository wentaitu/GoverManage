package com.nexuslink.govermanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.govermanage.event_fragment.BeingprocessedFragment;
import com.nexuslink.govermanage.event_fragment.ProcessedFragment;
import com.nexuslink.govermanage.event_fragment.UndisposedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventprocessActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn1) public ImageView btn1;
    @BindView(R.id.btn2) public ImageView btn2;
    @BindView(R.id.btn3) public ImageView btn3;
    @BindView(R.id.view1) public TextView view1;
    @BindView(R.id.view2) public TextView view2;
    @BindView(R.id.view3) public TextView view3;
    @BindView(R.id.fab_add_event) public FloatingActionButton fabAddEvent;
    @BindView(R.id.back_event) public ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventprocess);

        ButterKnife.bind(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventprocessActivity.this, PushMsgActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        replaceFragment(new UndisposedFragment());

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout_event, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        switch (view.getId()) {
            case R.id.btn1:
                view1.setVisibility(View.VISIBLE);
                replaceFragment(new UndisposedFragment());
                break;
            case R.id.btn2:
                view2.setVisibility(View.VISIBLE);
                replaceFragment(new BeingprocessedFragment());
                break;
            case R.id.btn3:
                view3.setVisibility(View.VISIBLE);
                replaceFragment(new ProcessedFragment());
                break;
        }
    }
}
