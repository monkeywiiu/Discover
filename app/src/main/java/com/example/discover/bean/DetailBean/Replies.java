package com.example.discover.bean.DetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zsj
 */

public class Replies implements Serializable{

    private List<ReplyList> replyList;

    public List<ReplyList> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyList> replyList) {
        this.replyList = replyList;
    }
}
