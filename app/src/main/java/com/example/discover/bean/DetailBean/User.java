package com.example.discover.bean.DetailBean;

import java.io.Serializable;

/**
 * @author zsj
 */

public class User implements Serializable {

    public int uid;
    public String nickname;
    public String avatar;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
