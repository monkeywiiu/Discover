package com.example.discover.utils;

import com.example.discover.bean.DetailBean.Data;
import com.example.discover.bean.LitePalBean.Follow;
import com.example.discover.bean.LitePalBean.LabelType;
import com.example.discover.bean.LitePalBean.LikeVideo;
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

    public static void addVideoToFavor(int id, String title, String desc, String playUrl,
                                  String imageUrl, String authorName, int authorId, String authorIcon, String authorDesc, int labelColor, String labelText, int size) {
        LikeVideo likeVideo = new LikeVideo();
        likeVideo.setId(1);
        likeVideo.setVideoId(id);
        likeVideo.setTitle(title);
        likeVideo.setDescription(desc);
        likeVideo.setPlayUrl(playUrl);
        likeVideo.setImageUrl(imageUrl);
        likeVideo.setAuthorName(authorName);
        likeVideo.setAuthorId(authorId);
        likeVideo.setAuthorIcon(authorIcon);
        likeVideo.setAuthorDesc(authorDesc);
        likeVideo.setLabelColor(labelColor);
        likeVideo.setLabelText(labelText);
        likeVideo.setSize(size);
        likeVideo.save();
    }

    public static void deleteVideoFromFavor(int videoId) {
        DataSupport.deleteAll(LikeVideo.class, "videoId = ?", String.valueOf(videoId));
    }

    public static void addToFollow(int authorId, String authorName, String desc, String iconUrl, int backgroundColor,String backgroundImage) {
        Follow follow = new Follow();
        follow.setAuthorId(authorId);
        follow.setAuthorName(authorName);
        follow.setAuthorDesc(desc);
        follow.setIconUrl(iconUrl);
        follow.setBackgroundColor(backgroundColor);
        follow.setBackgroundImage(backgroundImage);
        follow.save();
    }

    public static void deleteFromFollow(int authorId) {
        DataSupport.deleteAll(Follow.class, "authorId = ?", String.valueOf(authorId));
    }
}
