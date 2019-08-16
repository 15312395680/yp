package cn.com.yp.controller;

import cn.com.yp.config.WxMpConfiguration;
import cn.com.yp.entity.MaterialVo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class WxController {
    @Autowired
    private WxMpConfiguration wxMpConfiguration;

    @RequestMapping("/materialList")
    public String gotoMaterialList(Map<String, Object> param) throws Exception{
        WxMpService service = wxMpConfiguration.getMpService();
        WxMpMaterialNewsBatchGetResult materilas = service.getMaterialService().materialNewsBatchGet(0, 20);
        param.put("materials", materilas);
        return "materialList";
    }

    @RequestMapping(value = "/materialDetail",method = RequestMethod.GET)
    public String materialDetail(Map<String, Object> param, String mediaId, String num) throws Exception{
        WxMpService service = wxMpConfiguration.getMpService();
        WxMpMaterialNews wxMpMaterialNews = service.getMaterialService().materialNewsInfo(mediaId);
        WxMpMaterialNews.WxMpMaterialNewsArticle article = wxMpMaterialNews.getArticles().get(Integer.valueOf(num));
        param.put("article", wxMpMaterialNews.getArticles().get(Integer.valueOf(num)));
        Boolean isBuy = true;
//        WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//        article.setContent("<div>2222asdsjdhsdjhhsdsdjshd22222</div><span>aaaa</span><div><a href='http://www.baidu.com'>点击</div>我的实打2222222223232师德师风大幅度发大幅度付多付多付多付多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多实的就是觉得还是得山东省觉得还是觉得还是大街上的韩国数据和电话公司市地税局到货时间的告诉大家公司等十多个还是得更好撒大声地 山东省觉得换个时间大概是多少大概是广东省说的就是的故事的故事的故事大概是个十大歌手的故事的故事帝国时代");
//        article.setTitle("wenzhang11");
//        article.setAuthor("陈龙");
        if (!isBuy){
            article.setContent(article.getContent().substring(0, 100));
        }
        param.put("article", article);
        param.put("createDate", wxMpMaterialNews.getUpdateTime());
        param.put("isBuy", isBuy);
        return "materialDetail";
    }

    @GetMapping("/getMaterialList")
    @ResponseBody
    public WxMpMaterialNewsBatchGetResult getMaterialList() throws Exception{
        WxMpService service = wxMpConfiguration.getMpService();
        WxMpMaterialNewsBatchGetResult materilas = service.getMaterialService().materialNewsBatchGet(0, 20);
        return materilas;
    }

    @GetMapping("/getMaterialImagesList")
    @ResponseBody
    public WxMpMaterialFileBatchGetResult getMaterialImagesList() throws Exception{
        WxMpService service = wxMpConfiguration.getMpService();
        WxMpMaterialFileBatchGetResult images = service.getMaterialService().materialFileBatchGet("image", 0, 20);
        return images;
    }
}
