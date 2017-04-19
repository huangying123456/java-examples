package com.youhujia.solar.domain.component.query.articleDisease;

import com.youhujia.halo.common.YHJException;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ArticleDiseaseComponentQueryBO {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public ArticleDiseaseComponentQueryContext getDiseaseComponentById(Long componentId) {
        ArticleDiseaseComponentQueryContext context = new ArticleDiseaseComponentQueryContext();
        context.setTagDTO(getTagById(componentId));
        return context;
    }

    private HDFragments.TagDTO getTagById(Long componentId) {
        HDFragments.TagDTO tagDTO = hdFragmentsServiceWrap.getTagById(componentId);
        if (tagDTO.getData().getTag() == null) {
            throw new YHJException(YHJExceptionCodeEnum.SHOW_EXCEPTION_INFO_TO_USER, "该组件不存在");
        }
        return tagDTO;
    }
}
