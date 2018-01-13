package com.example.discover.utils;

import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.view.CustomView.LabelView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeyWiiu on 2018/1/13.
 */

public class LitePalUtil {

    public static List<String> getSelectLabel() {
        List<LabelType> list = DataSupport.findAll(LabelType.class);
        List<String> labelList = new ArrayList();
        for (LabelType labelType : list) {
            labelList.add(labelType.getType());
        }

        return labelList;
    }
}
