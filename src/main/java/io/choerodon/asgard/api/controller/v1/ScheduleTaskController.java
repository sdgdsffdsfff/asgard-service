package io.choerodon.asgard.api.controller.v1;

import io.choerodon.asgard.api.dto.ScheduleTaskDTO;
import io.choerodon.asgard.api.pojo.TriggerType;
import io.choerodon.asgard.api.service.ScheduleTaskService;
import io.choerodon.asgard.domain.QuartzTask;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/schedules/tasks")
@Api("定时任务定义接口")
public class ScheduleTaskController {

    private ScheduleTaskService scheduleTaskService;

    public ScheduleTaskController(ScheduleTaskService scheduleTaskService) {
        this.scheduleTaskService = scheduleTaskService;
    }

    @Permission(level = ResourceLevel.SITE, roles = {InitRoleCode.SITE_DEVELOPER})
    @ApiOperation(value = "创建定时任务")
    @PostMapping
    public ResponseEntity<QuartzTask> create(@RequestBody @Valid ScheduleTaskDTO dto) {
        if (TriggerType.CRON.getValue().equals(dto.getTriggerType())) {
            if (StringUtils.isEmpty(dto.getCronExpression())) {
                throw new CommonException("error.scheduleTask.cronExpressionEmpty");
            }
        } else if (TriggerType.SIMPLE.getValue().equals(dto.getTriggerType())) {
            if (dto.getSimpleRepeatCount() == null || dto.getSimpleRepeatInterval() == null) {
                throw new CommonException("error.scheduleTask.repeatCountOrRepeatIntervalNull");
            }
        } else {
            throw new CommonException("error.scheduleTask.invalidTriggerType");
        }
        return new ResponseEntity<>(scheduleTaskService.create(dto), HttpStatus.OK);
    }

}
