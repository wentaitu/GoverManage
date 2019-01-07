package com.nexuslink.govermanage.nav_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexuslink.govermanage.BusinessPlatform.WebviewbusinessActivity;
import com.nexuslink.govermanage.EventprocessActivity;
import com.nexuslink.govermanage.PushMsgActivity;
import com.nexuslink.govermanage.R;
import com.nexuslink.govermanage.adapter.PopActivityRecyclerAdapter;
import com.nexuslink.govermanage.adapter.PopCompetitionRecyclerAdapter;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.cqupt.loc.MapActivity;


/**
 * Created by wentai on 17-10-30.
 */

public class ServicesFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service , container, false);
        view.findViewById(R.id.mid_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "学习");
                intent.putExtra("bus_url", "https://m.dushu.com/");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.mid_life).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "生活");
                intent.putExtra("bus_url", "https://waimai.meituan.com/");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.mid_sports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "运动");
                intent.putExtra("bus_url", "https://www.51yund.com/");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.mid_travel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "旅行");
                intent.putExtra("bus_url", "https://m.tuniu.com/");
                startActivity(intent);
            }
        });


        return view;
    }
}
