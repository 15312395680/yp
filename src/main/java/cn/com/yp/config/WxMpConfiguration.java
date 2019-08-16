package cn.com.yp.config;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
public class WxMpConfiguration {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${yp.url}")
    private String url;

    public WxMpConfiguration() {
    }



    public WxMpService getMpService() {
        return mpService;
    }

    WxMpService mpService = new WxMpServiceHttpClientImpl();

    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();

    @PostConstruct
    public void initServices() {

        //创建菜单
        //创建一级菜单
        WxMenuButton button1=new WxMenuButton();
        //点击事件按钮
        button1.setType("view");
        button1.setName("文章列表");
        //根据标志获取点击菜单
        button1.setKey("key1");
        button1.setUrl(url + "wx/materialList");

        //创建一个复合菜单
        WxMenuButton button2=new WxMenuButton();
        button2.setName("多级菜单");

        WxMenuButton button2_1=new WxMenuButton();
        //点击事件按钮
        button2_1.setType("click");
        button2_1.setName("子菜单一");
        //根据标志获取点击菜单
        button2_1.setKey("key2");

        WxMenuButton button2_2=new WxMenuButton();
        //点击事件按钮
        button2_2.setType("click");
        button2_2.setName("子菜单二");
        //根据标志获取点击菜单
        button2_2.setKey("key3");


        List<WxMenuButton> subButtons=new ArrayList<WxMenuButton>();
        subButtons.add(button2_1);
        subButtons.add(button2_2);
        button2.setSubButtons(subButtons);

        List<WxMenuButton> buttons=new ArrayList<WxMenuButton>();
        buttons.add(button1);
        buttons.add(button2);

        WxMenu menu=new WxMenu();
        menu.setButtons(buttons);


        configStorage.setAppId(appid);
        configStorage.setSecret(secret);
        //configStorage.setToken(properties.getToken());
        //configStorage.setAesKey(properties.getAesKey());
        mpService.setWxMpConfigStorage(configStorage);
        try {
            mpService.getMenuService().menuCreate(menu);

//            WxMpMaterial material1 = new WxMpMaterial();
//            material1.setName("图片1");
//            material1.setFile(new File("C:/Users/Administrator/Desktop/pictures/timg.jpg"));
//            WxMpMaterialUploadResult image1 = mpService.getMaterialService().materialFileUpload("image", material1);
//
//            WxMpMaterial material2 = new WxMpMaterial();
//            material2.setName("图片2");
//            material2.setFile(new File("C:/Users/Administrator/Desktop/pictures/timg2.jpg"));
//            WxMpMaterialUploadResult image2 = mpService.getMaterialService().materialFileUpload("image", material2);
//
//            WxMpMaterial material3 = new WxMpMaterial();
//            material3.setName("图片11");
//            material3.setFile(new File("C:/Users/Administrator/Desktop/pictures/timg3.jpg"));
//            WxMpMaterialUploadResult image3 = mpService.getMaterialService().materialFileUpload("image", material3);
//
//            WxMpMaterial material4 = new WxMpMaterial();
//            material4.setName("图片12");
//            material4.setFile(new File("C:/Users/Administrator/Desktop/pictures/timg4.jpg"));
//            WxMpMaterialUploadResult image4 = mpService.getMaterialService().materialFileUpload("image", material4);
//
//            WxMpMaterial material5 = new WxMpMaterial();
//            material5.setName("图片13");
//            material5.setFile(new File("C:/Users/Administrator/Desktop/pictures/timg5.jpg"));
//            WxMpMaterialUploadResult image5 = mpService.getMaterialService().materialFileUpload("image", material5);
//
//            WxMpMaterialNews news = new WxMpMaterialNews();
//            List<WxMpMaterialNews.WxMpMaterialNewsArticle> wxMpMaterialNewsArticles = new ArrayList<>();
//            WxMpMaterialNews.WxMpMaterialNewsArticle article1 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//            article1.setTitle("第二篇文章");
//            article1.setThumbMediaId(image1.getMediaId());
//            article1.setAuthor("");
//            article1.setDigest("");
//            article1.setShowCoverPic(false);
//            article1.setContent("<p><br  /></p><p><img class=\"\" data-copyright=\"0\" data-ratio=\"0.4243663123466885\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_png/mSz8zK8Ejbu5dcJbtSyqmwv0qC3PuyL02UicGed9LnvSECVuJq0XictvtBrA5qypicQk5UibzBL9UMgShkpM35O69g/640?wx_fmt=png\" data-type=\"png\" data-w=\"1223\" style=\"\"  /></p><p><img class=\"\" data-copyright=\"0\" data-ratio=\"1.139874739039666\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_png/mSz8zK8Ejbu5dcJbtSyqmwv0qC3PuyL0Et2WCkFDAaKj4U8q7FNM0jPpM2TpfqOd1VzKMXThTK6NlJ1Z9eAx5g/640?wx_fmt=png\" data-type=\"png\" data-w=\"479\" style=\"\"  /></p>");
//            article1.setContentSourceUrl("");
//            article1.setNeedOpenComment(false);
//            article1.setOnlyFansCanComment(false);
//            wxMpMaterialNewsArticles.add(article1);
//            news.setArticles(wxMpMaterialNewsArticles);
//            mpService.getMaterialService().materialNewsUpload(news);
//
//            WxMpMaterialNews news2 = new WxMpMaterialNews();
//            List<WxMpMaterialNews.WxMpMaterialNewsArticle> wxMpMaterialNewsArticles2 = new ArrayList<>();
//            WxMpMaterialNews.WxMpMaterialNewsArticle article2 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//            article2.setTitle("第一篇文章");
//            article2.setThumbMediaId(image2.getMediaId());
//            article2.setAuthor("");
//            article2.setDigest("这是一个测试文章这是一个测试文章这是一个测试文章这是一个测试文章这是一个测试文章这是一个测试文章这是一个测试");
//            article2.setShowCoverPic(false);
//            article2.setContent("<p><br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章</p><p style=\"text-align: center;\"><img class=\"rich_pages\" data-copyright=\"0\" data-ratio=\"0.4243663123466885\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_png/mSz8zK8Ejbu5dcJbtSyqmwv0qC3PuyL02UicGed9LnvSECVuJq0XictvtBrA5qypicQk5UibzBL9UMgShkpM35O69g/640?wx_fmt=png\" data-type=\"png\" data-w=\"1223\" style=\"\"  /></p><p><br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br style=\"white-space: normal;\"  />这是一个测试文章<br  /></p><p>这是一个测试文章</p><p><br  />这是一个测试文章</p><p><br  />这是一个测试文章</p><p><br  />这是一个测试文章</p><p><br  /></p><p>这是一个测试文章嘎嘎嘎嘎嘎过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过过灌灌灌灌</p><p><br  /></p><p>这是一个测试文章</p>");
//            article2.setContentSourceUrl("");
//            article2.setNeedOpenComment(false);
//            article2.setOnlyFansCanComment(false);
//            wxMpMaterialNewsArticles2.add(article2);
//            news2.setArticles(wxMpMaterialNewsArticles2);
//            mpService.getMaterialService().materialNewsUpload(news2);
//
//            WxMpMaterialNews news3 = new WxMpMaterialNews();
//            List<WxMpMaterialNews.WxMpMaterialNewsArticle> wxMpMaterialNewsArticles3 = new ArrayList<>();
//            WxMpMaterialNews.WxMpMaterialNewsArticle article3 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//            article3.setTitle("第三篇文章111");
//            article3.setThumbMediaId(image3.getMediaId());
//            article3.setAuthor("");
//            article3.setDigest("");
//            article3.setShowCoverPic(false);
//            article3.setContent("<p>11111111111111111111111111111111111111111111111111111111</p>");
//            article3.setContentSourceUrl("");
//            article3.setNeedOpenComment(false);
//            article3.setOnlyFansCanComment(false);
//            wxMpMaterialNewsArticles3.add(article3);
//
//            WxMpMaterialNews.WxMpMaterialNewsArticle article4 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//            article4.setTitle("第三篇文章22222");
//            article4.setThumbMediaId(image4.getMediaId());
//            article4.setAuthor("");
//            article4.setDigest("");
//            article4.setShowCoverPic(false);
//            article4.setContent("<p>22222222222222222222222222222222222222222222222222222222222</p>");
//            article4.setContentSourceUrl("");
//            article4.setNeedOpenComment(false);
//            article4.setOnlyFansCanComment(false);
//            wxMpMaterialNewsArticles3.add(article4);
//
//            WxMpMaterialNews.WxMpMaterialNewsArticle article5 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
//            article5.setTitle("第三篇文章333");
//            article5.setThumbMediaId(image5.getMediaId());
//            article5.setAuthor("");
//            article5.setDigest("");
//            article5.setShowCoverPic(false);
//            article5.setContent("<p>333333333333333333333333333333333333333333333</p>");
//            article5.setContentSourceUrl("");
//            article5.setNeedOpenComment(false);
//            article5.setOnlyFansCanComment(false);
//            wxMpMaterialNewsArticles3.add(article5);
//            news3.setArticles(wxMpMaterialNewsArticles3);
//            mpService.getMaterialService().materialNewsUpload(news3);

        } catch (WxErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
