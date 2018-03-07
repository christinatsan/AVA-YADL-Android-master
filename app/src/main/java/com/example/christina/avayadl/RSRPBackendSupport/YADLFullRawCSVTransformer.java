package com.example.christina.avayadl.RSRPBackendSupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.Core.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.Core.RSRPIntermediateResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.christina.avayadl.RSRPBackendSupport.YADLFullRaw;

/**
 * Created by Christina on 2/1/2018.
 */

public class YADLFullRawCSVTransformer implements RSRPFrontEnd {
    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {
        Object schemaID = parameters.get("schemaID");
        if (schemaID == null || !(schemaID instanceof Map)) {
            return null;
        }

        StepResult firstResult = null;
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() instanceof StepResult) {
                StepResult result = (StepResult) entry.getValue();
                if (firstResult==null) { firstResult = result; }
                Object answer = result.getResult();
                if (answer instanceof String) {
                    String[] parts = result.getIdentifier().split("\\.");
                    String identifier = parts[parts.length-1];
                    resultMap.put(identifier, (String)answer);
                }
            }
        }

        YADLFullRawCSVEncodable yadlFull = new YADLFullRawCSVEncodable(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                (Map<String, Object>)schemaID,
                resultMap);

        yadlFull.setStartDate( firstResult != null ? firstResult.getStartDate() : new Date() );
        yadlFull.setEndDate(firstResult != null ? firstResult.getEndDate() : new Date());

       // YADLFullRaw yadlFullRaw = (YADLFullRaw) yadlFull;
       // YADLFullRawCSVEncodable yadlFullRawCSVEncodable = (YADLFullRawCSVEncodable) yadlFullRaw;


        return yadlFull;

    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(com.example.christina.avayadl.RSRPBackendSupport.YADLFullRawCSVEncodable.TYPE)) return true;
        else return false;
    }
}
