package cn.com.yp.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

/**
 * @author Binary Wang
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
public class WxPayConfiguration {
  @Value("${wx.appid}")
  private String appId;
  @Value("${wx.pay.mchid}")
  private String mchId;
  @Value("${wx.pay.mchkey}")
  private String mchkey;

  public WxPayConfiguration() {

  }

  @Bean
  @ConditionalOnMissingBean
  public WxPayService wxService() {
    WxPayConfig payConfig = new WxPayConfig();
    payConfig.setAppId(StringUtils.trimToNull("wx54ca38176e7a87d2"));
    payConfig.setMchId(StringUtils.trimToNull(mchId));
    payConfig.setMchKey(StringUtils.trimToNull(mchkey));
//    payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
//    payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
//    payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));

    // 可以指定是否使用沙箱环境
    payConfig.setUseSandboxEnv(true);

    WxPayService wxPayService = new WxPayServiceImpl();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }

}
