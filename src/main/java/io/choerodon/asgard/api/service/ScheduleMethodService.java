package io.choerodon.asgard.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.choerodon.asgard.api.dto.ScheduleMethodDTO;
import io.choerodon.asgard.api.dto.ScheduleMethodInfoDTO;
import io.choerodon.asgard.api.dto.ScheduleMethodParamsDTO;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface ScheduleMethodService {

    ResponseEntity<Page<ScheduleMethodInfoDTO>> pageQuery(PageRequest pageRequest, String code,
                                                          String service, String method, String description, String params, String level);

    List<ScheduleMethodDTO> getMethodByService(String serviceName, String level);


    ScheduleMethodParamsDTO getParams(Long id, String level);

    Long getMethodIdByCode(String code);

    /**
     * 查询各层级拥有可执行程序的服务
     *
     * @param level 层级
     * @return 服务列表
     */
    List<String> getServices(String level);

    /**
     * 删除指定Id的可执行程序
     *
     * @param id 可执行程序Id
     */
    void delete(Long id);
}
