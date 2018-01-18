package com.myself.deployrequester.bo;

import com.myself.deployrequester.model.DeployDbserversDO;

/**
 * Created by QueRenJie on ${date}
 */
public class DeployDbservers extends DeployDbserversDO {
    private String belongName = "";

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
