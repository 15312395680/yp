package cn.com.yp.controller;

import cn.com.yp.config.WxMpConfiguration;
import cn.com.yp.util.SubCodeUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/wx")
public class WxController {

    private static final Logger logger = Logger.getLogger(WxController.class);
    @Value("${yp.url}")
    private String url;

    @Autowired
    private WxMpConfiguration wxMpConfiguration;
    private WxPayService wxPayService;

    @Autowired
    public WxController(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

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
//        WxMpMaterialNews wxMpMaterialNews = service.getMaterialService().materialNewsInfo(mediaId);
//        WxMpMaterialNews.WxMpMaterialNewsArticle article = wxMpMaterialNews.getArticles().get(Integer.valueOf(num));
//        param.put("article", wxMpMaterialNews.getArticles().get(Integer.valueOf(num)));
        Boolean isBuy = false;
        WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        article.setContent("<div>2222asdsjdhsdjhhsdsdjshd22222</div><span>aaaa</span><div><a href='http://www.baidu.com'>点击</div>我的实打2222222223232师德师风大幅度发大幅度付多付多付多付多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多实的就是觉得还是得山东省觉得还是觉得还是大街上的韩国数据和电话公司市地税局到货时间的告诉大家公司等十多个还是得更好撒大声地 山东省觉得换个时间大概是多少大概是广东省说的就是的故事的故事的故事大概是个十大歌手的故事的故事帝国时代");
        article.setTitle("wenzhang11");
        article.setAuthor("陈龙");
        if (!isBuy){
            article.setContent(article.getContent().substring(0, 100));
        }
        param.put("article", article);
        param.put("createDate",new Date());
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

    @GetMapping(value = "/generatePay")
    @ResponseBody
    public WxPayAppOrderResult generatePay(String payType, String id) throws WxPayException {
        String tradeNo = SubCodeUtil.getNum();
        String busId = "123131313";
        Integer fee = 1;
        WxPayUnifiedOrderRequest request  = new WxPayUnifiedOrderRequest();
        request.setBody(payType);
        request.setTotalFee(fee);
        request.setOutTradeNo(tradeNo);
        request.setProductId("1000000123");
        request.setSpbillCreateIp("192.168.1.5");
        request.setNotifyUrl(url + "wx/parseOrderNotifyResult");
        request.setTradeType(WxPayConstants.TradeType.JSAPI);
        request.setSignType(WxPayConstants.SignType.MD5);
        // 沙箱mchKey
        wxPayService.getConfig().setMchKey(wxPayService.getSandboxSignKey());
        WxPayAppOrderResult o = wxPayService.createOrder(request);
        return o;
    }

    /**
     * 接收支付返回的消息
     * @param
     */
    @RequestMapping(value="/parseOrderNotifyResult",method = RequestMethod.POST)
    @ResponseBody
    public String parseOrderNotifyResult(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("============支付回调开始");
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            System.out.println(result.toString());
            // 结果正确
            String orderId = result.getOutTradeNo();
            String tradeNo = result.getTransactionId();
            //String totalFee = WxPayBaseResult.feeToYuan(result.getTotalFee());
            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            System.out.println("============支付回调结束");
            return WxPayNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}" + e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }

    }
}
