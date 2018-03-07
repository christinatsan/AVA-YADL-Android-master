package com.example.christina.avayadl.RSRPBackendSupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.Core.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.Core.RSRPIntermediateResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLFullRawCSVTransformer implements RSRPFrontEnd {
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

        MEDLFullRawCSVEncodable medlFull = new MEDLFullRawCSVEncodable(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                (Map<String, Object>)schemaID,
                resultMap);

        medlFull.setStartDate( firstResult != null ? firstResult.getStartDate() : new Date() );
        medlFull.setEndDate(firstResult != null ? firstResult.getEndDate() : new Date());



        return medlFull;

    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(com.example.christina.avayadl.RSRPBackendSupport.MEDLFullRawCSVEncodable.TYPE)) return true;
        else return false;
    }
}
