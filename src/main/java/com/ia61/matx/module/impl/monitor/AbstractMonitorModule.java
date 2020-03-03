package com.ia61.matx.module.impl.monitor;

import com.ia61.matx.model.input.impl.MultiInput;
import com.ia61.matx.module.Module;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractMonitorModule<TYPE> extends MultiInput<TYPE> implements Monitor {

    //frequency of data points in milliseconds
    private Long pullRate = 50L;
    //total graph length in milliseconds
    private Long pullTime = 10000L;

    private List<Map<Float, Float>> result = new ArrayList<>();

    public abstract void gatherAllInputs();

}
