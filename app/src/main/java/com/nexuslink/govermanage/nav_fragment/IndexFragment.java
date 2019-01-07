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
import com.nexuslink.govermanage.TaskProcessingActivity;
import com.nexuslink.govermanage.TaskPubActivity;
import com.nexuslink.govermanage.WorkShowActivity;
import com.nexuslink.govermanage.WorkUpActivity;
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
 * Created by wentai on 18-12-5.
 */

public class IndexFragment extends Fragment {

    private BannerView banner;  // 最顶上轮播
    private int[] imgs = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner1,R.drawable.banner2};
    private List<View> viewList;
    RecyclerView popActivitys;
    RecyclerView popCompetition;

    // 四个按钮
    private ImageView upload_msg;
    private ImageView openLoc;

    private Unbinder unbinder;
    // 商家平台8个按钮
    @BindView(R.id.bigsmall_thing) public ImageView bigsmallThing;
    @BindView(R.id.secondhand_goods) public ImageView secondhandGoods;
    @BindView(R.id.house_tolet) public ImageView houseTolet;
    @BindView(R.id.recruitment) public ImageView recruitment;
    @BindView(R.id.resident_forhelp) public ImageView residentForhelp;
    @BindView(R.id.business_info) public ImageView businessInfo;
    @BindView(R.id.city_dating) public ImageView cityDating;
    @BindView(R.id.take_out) public ImageView takeOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        unbinder = ButterKnife.bind(this, view);

        banner = view.findViewById(R.id.banner);
        popActivitys = view.findViewById(R.id.pop_activity);
        popCompetition = view.findViewById(R.id.pop_competition);

        // 菜单定位按钮
        openLoc = view.findViewById(R.id.location_index_fragment);
        openLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });

        // 首页按钮8个*********************************************************************************
        upload_msg = view.findViewById(R.id.index_upload);
        upload_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PushMsgActivity.class));
            }
        });

        view.findViewById(R.id.index_gover_affairs_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
//                intent.putExtra("bus_name", "政务服务");
//                intent.putExtra("bus_url", "http://m.cq.gov.cn/");
                Intent intent = new Intent(getActivity(), TaskPubActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.index_people_congress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
//                intent.putExtra("bus_name", "人大政协");
//                intent.putExtra("bus_url", "http://www.cppcc.gov.cn/");
                Intent intent = new Intent(getActivity(), WorkUpActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.index_life_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "生活服务");
                intent.putExtra("bus_url", "http://i.meituan.com/?cevent=imt%2Fhd%2Findex");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.event_processing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EventprocessActivity.class));
            }
        });

        view.findViewById(R.id.index_consult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TaskProcessingActivity.class);
                //Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
//                intent.putExtra("bus_name", "互动咨询");
//                intent.putExtra("bus_url", "https://www.cqsq.com/");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.show_teams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
//                intent.putExtra("bus_name", "社区文化");
//                intent.putExtra("bus_url", "https://www.cqwhw.gov.cn/");
                Intent intent = new Intent(getActivity(), WorkShowActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.index_city_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "城市服务");
                intent.putExtra("bus_url", "http://zwfw.cq.gov.cn/icity/public/index");
                startActivity(intent);
            }
        });

        //商家平台按钮 8个******************************************************************************
        bigsmallThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "大事小事");
                intent.putExtra("bus_url", "http://www.cqnews.net/");
                startActivity(intent);
            }
        });
        secondhandGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "二手物品");
                intent.putExtra("bus_url", "https://2.taobao.com/");
                startActivity(intent);
            }
        });
        houseTolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "房屋出售");
                intent.putExtra("bus_url", "https://cq.lianjia.com");
                startActivity(intent);
            }
        });
        recruitment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "求职招聘");
                intent.putExtra("bus_url", "https://m.zhaopin.com/registerone.html?utm_source=baiduppzz&utm_medium=cpt&utm_term=title&utm_content=textlink&utm_campaign=Apr");
                startActivity(intent);
            }
        });
        residentForhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "居民求助");
                intent.putExtra("bus_url", "http://www.cq.gov.cn/publicmail/citizen/Index.aspx");
                startActivity(intent);
            }
        });
        businessInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "商家信息");
                intent.putExtra("bus_url", "http://shangjia.inlishui.com/");
                startActivity(intent);
            }
        });
        cityDating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "同城交友");
                intent.putExtra("bus_url", "http://www.zhenai.com/");
                startActivity(intent);
            }
        });
        takeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewbusinessActivity.class);
                intent.putExtra("bus_name", "外卖服务");
                intent.putExtra("bus_url", "https://waimai.meituan.com/");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        banner.setLoopInterval(2500);
        banner.startLoop(true);

        banner.setViewList(viewList);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TestActivity.class);
//                startActivity(intent);
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popActivitys.setLayoutManager(linearLayoutManager);
        int[] imgs = {R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3, R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3, R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3, R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3, R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3};
        popActivitys.setAdapter(new PopActivityRecyclerAdapter(imgs));
        popActivitys.setNestedScrollingEnabled(false);

        popCompetition.setLayoutManager(new LinearLayoutManager(getActivity()));
        int[] imgs2 = {R.drawable.pop_activity6, R.drawable.pop_activity4, R.drawable.pop_activity1, R.drawable.pop_activity2, R.drawable.pop_activity3, R.drawable.pop_competition};
        popCompetition.setAdapter(new PopCompetitionRecyclerAdapter(imgs2));
        popCompetition.setNestedScrollingEnabled(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
