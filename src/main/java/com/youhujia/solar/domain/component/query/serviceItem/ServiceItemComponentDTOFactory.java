package com.youhujia.solar.domain.component.query.serviceItem;

import com.youhujia.halo.hdfragments.HDFragments;
import com.youhujia.halo.hdfragments.HDFragmentsServiceWrap;
import com.youhujia.halo.solar.Solar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ljm on 2017/4/18.
 */
@Component
public class ServiceItemComponentDTOFactory {

    @Autowired
    HDFragmentsServiceWrap hdFragmentsServiceWrap;

    public Solar.ServiceItemComponentDTO buildServiceItemDTO(ServiceItemComponentQueryContext context) {

//        HDFragments.TagDTO tagDTO = context.getTagDTO();
//        HDFragments.TagPropertiesDTO tagPropertiesDTO = hdFragmentsServiceWrap.getTagPropertiesByTagId(tagDTO.getData().getTag().getId());
//        return transform2SerivceItemComponentDTO(tagDTO.getData().getTag(), tagPropertiesDTO);
        return null;
    }

    private Solar.ServiceItemComponentDTO transform2SerivceItemComponentDTO(HDFragments.Tag tag, HDFragments.TagPropertiesDTO tagPropertiesDTO) {
//        Solar.ServiceItem.Builder serviceItem = Solar.ServiceItem.newBuilder();
//        Solar.ServiceItemComponentDTO.Builder serviceItemComponentDTO = Solar.ServiceItemComponentDTO.newBuilder();
//
//        if (tag.hasName()) {
//            serviceItem.setTitle(tag.getName());
//        }
//
//        if (tag.hasId()) {
//            serviceItem.setComponentId(tag.getId());
//        }
//
//        if (tag.hasCreatorId()) {
//            serviceItem.setCreatorId(tag.getCreatorId());
//        }
//
//        if (tag.hasDptId()) {
//            serviceItem.setDepartmentId(tag.getDptId());
//        }
//
//        if (tagPropertiesDTO.getData().getPropertiesList().size() > 0) {
//            tagPropertiesDTO.getData().getPropertiesList().stream().forEach(tagProperty -> {
//                if (tagProperty.hasValue()) {
//                    JSONObject jsonObject = JSON.parseObject(tagProperty.getValue());
//                    serviceItem.setRank(Long.parseLong(jsonObject.get("rank").toString()));
//                    String[] str = jsonObject.get("serviceItemId").toString().split(",");
//                    for (String serviceItemId : str) {
//                        serviceItem.addServiceItemId(Long.parseLong(serviceItemId));
//                    }
//                }
//            });
//        }
//        return serviceItemComponentDTO
//                .setServiceItem(serviceItem.build())
//                .setResult(COMMON.Result.newBuilder().setCode(200).setDisplaymsg("success")).build();
        return null;
    }
}
