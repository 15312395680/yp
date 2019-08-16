package cn.com.yp.entity;

import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;

public class MaterialVo {
    private String mediaId;
    private Integer num;
    private WxMpMaterialNews.WxMpMaterialNewsArticle article;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public WxMpMaterialNews.WxMpMaterialNewsArticle getArticle() {
        return article;
    }

    public void setArticle(WxMpMaterialNews.WxMpMaterialNewsArticle article) {
        this.article = article;
    }
}
