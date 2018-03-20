package cn.xiaoyaoji.controller;

import java.util.List;
import java.util.Map;

import cn.xiaoyaoji.core.common.Pagination;
import cn.xiaoyaoji.service.ServiceFactory;

import cn.xiaoyaoji.data.bean.ProjectLog;
import cn.xiaoyaoji.core.common._HashMap;
import cn.xiaoyaoji.core.common._HashMap;
import cn.xiaoyaoji.data.bean.ProjectLog;
import cn.xiaoyaoji.service.ServiceFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujingjie
 * @date 2016-11-01
 */
@RestController
@RequestMapping("/projectlog")
public class ProjectLogController {

    @GetMapping
    public Object get(Map<String,String> params) {
        Pagination pagination = Pagination.build(params);
        List<ProjectLog> logs = ServiceFactory.instance().getProjectLogs(pagination);
        return new _HashMap<>()
                .add("logs", logs);
    }
}
