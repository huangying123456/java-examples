package com.youhujia.solar.wxQrcode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.zxing.WriterException;
import com.youhujia.halo.util.LogInfoGenerator;
import com.youhujia.halo.yolar.Yolar;
import com.youhujia.halo.yolar.YolarClientWrap;
import com.youhujia.solar.common.SolarExceptionCodeEnum;
import com.youhujia.solar.util.HttpUtil;
import com.youhujia.solar.util.QRCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam0n on 2017/4/21.
 */

@Service
public class WechatQRCodeBO {

    public final static String WXSUBQRCODEKEY = "wxSubQRCode";
    public final static String QRCODEKEY = "qrCode";
    private final String WxSceneCodeForDptIdPrefix = "departmentId_";
    private final String SHADOW_WX_SCENE_CODE_FOR_DPTID_PREFIX = "shadow_";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    YolarClientWrap yolarClientWrap;

    @Value("${wx.appid}")
    String wxAppid;

    @Value("${wx.secret}")
    String wxSecret;

    public Map<String,String> generateWxSubQRCodeBase64Image(Long departmentId) throws IOException, WriterException {
        boolean isShadowWxAccount = false;
        ObjectMapper objectMapper = new ObjectMapper();

        Yolar.ShadowWxAccountDTO sdWx = yolarClientWrap.findShadowWxAccountByDepartmentId(departmentId);
        if (StringUtils.isNotBlank(sdWx.getAppid())) {
            isShadowWxAccount = true;
        }
        String accessToken = getAccessToken(isShadowWxAccount, sdWx);
        String wxUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;

        ObjectNode sceneObjectNode = objectMapper.createObjectNode();
        if (isShadowWxAccount) {
            sceneObjectNode.put("scene_str", SHADOW_WX_SCENE_CODE_FOR_DPTID_PREFIX + departmentId);
        } else {
            sceneObjectNode.put("scene_str", WxSceneCodeForDptIdPrefix + departmentId);
        }
        ObjectNode actionInfoNode = objectMapper.createObjectNode();
        actionInfoNode.set("scene", sceneObjectNode);

        ObjectNode actionNode = objectMapper.createObjectNode();
        actionNode.put("action_name", "QR_LIMIT_STR_SCENE");
        actionNode.set("action_info", actionInfoNode);

        String resp = HttpUtil.post(wxUrl, actionNode.toString());

        String url = getUrl(resp);
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put(QRCODEKEY,url);
        String wxSubQRCode = QRCodeUtils.getImageBase64Src(url, 250, 250, QRCodeUtils.PNG_FORMAT);
        valueMap.put(WXSUBQRCODEKEY,wxSubQRCode);
        return valueMap;
    }

    private String getAccessToken(boolean isShadowWxAccount, Yolar.ShadowWxAccountDTO sdWx) {

        String where = "WechatQRCodeBO->getAccessToken";

        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

        String url;
        if (isShadowWxAccount) {
            url = String.format(urlTemplate, sdWx.getAppid(), sdWx.getSecret());
        } else {
            url = String.format(urlTemplate, wxAppid, wxSecret);
        }
        String respJson = HttpUtil.getUrlAsStr(url);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(respJson);
            String token = jsonNode.get("access_token").asText();

//            logger.debug("get token:" + token);
            logger.info(LogInfoGenerator.generateCallInfo(where, "token", token));
            return token;
        } catch (IOException e) {
            logger.info(LogInfoGenerator.generateErrorInfo(where, SolarExceptionCodeEnum.UNKNOWN_ERROR, "exception", "json", respJson, "message", e.getMessage()));
        }
        return null;
    }

    private String getUrl(String respJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(respJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = jsonNode.get("url").asText();
//        System.out.println("resp json:" + jsonNode.toString());

        return url;
    }
}
