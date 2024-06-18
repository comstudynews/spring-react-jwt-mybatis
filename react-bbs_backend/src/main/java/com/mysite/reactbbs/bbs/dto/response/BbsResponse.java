package com.mysite.reactbbs.bbs.dto.response;

import com.mysite.reactbbs.bbs.domain.Bbs;

public class BbsResponse {

    private Bbs bbs;

    public BbsResponse(Bbs bbs) {
        this.bbs = bbs;
    }

    public Bbs getBbs() {
        return bbs;
    }

    public void setBbs(Bbs bbs) {
        this.bbs = bbs;
    }
}
