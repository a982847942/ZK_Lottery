package edu.nuaa.lottery.domain.activity.service.stateflow;

import edu.nuaa.lottery.common.ActivityConstants;
import edu.nuaa.lottery.domain.activity.service.stateflow.event.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/29 11:13
 */
public class StateConfig {
    @Resource
    ArraignmentState arraignmentState;
    @Resource
    CheckPassState passState;
    @Resource
    CheckRefuseState refuseState;
    @Resource
    CloseState closeState;
    @Resource
    DoingState doingState;
    @Resource
    EditingState editingState;
    @Resource
    OpenState openState;

    protected Map<Enum<ActivityConstants.ActivityState>,AbstractState> stateGroup = new ConcurrentHashMap<>();
    @PostConstruct
    public void init(){
        stateGroup.put(ActivityConstants.ActivityState.ARRAIGNMENT, arraignmentState);
        stateGroup.put(ActivityConstants.ActivityState.CLOSE, closeState);
        stateGroup.put(ActivityConstants.ActivityState.DOING, doingState);
        stateGroup.put(ActivityConstants.ActivityState.EDIT, editingState);
        stateGroup.put(ActivityConstants.ActivityState.OPEN, openState);
        stateGroup.put(ActivityConstants.ActivityState.PASS, passState);
        stateGroup.put(ActivityConstants.ActivityState.REFUSE, refuseState);
    }
}
