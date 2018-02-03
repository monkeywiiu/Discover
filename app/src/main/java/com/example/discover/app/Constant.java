package com.example.discover.app;

import com.example.discover.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class Constant {


    public static final String EYE_VIDEO = "eye_video";
    public static final String EYE_FIND = "eye_find";
    public static final String GANK_WELFARE = "gank_welfare";
    public static final HashMap LabelMap = new HashMap() {{
        put("萌宠", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelCre));
        put("搞笑", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelMusic));
        put("游戏", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelTravel));
        put("科普", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelScience));
        put("集锦", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelFunny));
        put("生活", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelFashion));
        put("综艺", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelSport));
        put("开胃", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelAnim));
        put("预告", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelAd));
        put("广告", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelAppetizing));
        put("记录", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelLife));
        put("时尚", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelDrama));
        put("创意", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelNotice));
        put("运动", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelVariety));
        put("旅行", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelRecord));
        put("剧情", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelGame));
        put("动画", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelPet));
        put("音乐", DiscoverApplication.getContext().getResources().getColor(R.color.colorLabelVariety));
    }};

    public static final List<String> videoTypeList = new ArrayList<String>() {{
        add("萌宠");
        add("搞笑");
        add("游戏");
        add("科普");
        add("集锦");
        add("生活");
        add("综艺");
        add("开胃");
        add("预告");
        add("广告");
        add("记录");
        add("时尚");
        add("创意");
        add("运动");
        add("旅行");
        add("剧情");
        add("动画");
        add("音乐");

    }};

}
