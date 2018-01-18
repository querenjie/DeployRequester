package com.myself.deployrequester.controller;

import com.myself.deployrequester.dto.ModuleDTO;
import com.myself.deployrequester.model.DeployPerformanceDO;
import com.myself.deployrequester.service.DeployPerformanceService;
import com.myself.deployrequester.util.json.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by QueRenJie on ${date}
 */
@Controller
@RequestMapping("performance")
public class DeployPerformanceController {
    @Autowired
    private DeployPerformanceService deployPerformanceService;

    @ResponseBody
    @RequestMapping(value="/getPerformance", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JsonResult getPerformance(@RequestBody ModuleDTO moduleDTO) {
        JsonResult result = null;
        DeployPerformanceDO deployPerformanceDO = new DeployPerformanceDO();
        deployPerformanceDO.setProjectcode(moduleDTO.getProjectId());
        deployPerformanceDO.setModulecode(moduleDTO.getModuleId());
        deployPerformanceDO.setModuletypecode(moduleDTO.getModuleTypeId());
        try {
            List<DeployPerformanceDO> deployPerformanceDOList = deployPerformanceService.selectByModulecodeAndModuletypecodeAndProjectcode(deployPerformanceDO);
            if (deployPerformanceDOList != null && deployPerformanceDOList.size() == 1) {
                result = JsonResult.createSuccess("获取性能数据成功");
                long duration = deployPerformanceDOList.get(0).getDeployduration();
                result.addData(duration);   //发布的毫秒数
            } else {
                result = JsonResult.createFailed("取出的性能数据有问题");
            }
        } catch (Exception e) {
            result = JsonResult.createFailed("提交发布申请异常:" + e.getStackTrace().toString());
        }
        return result;
    }
}
