package com.example.christina.avayadl.RSRPBackendSupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.Core.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.Core.RSRPIntermediateResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyResult;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLSpotRawCSVTransformer implements RSRPFrontEnd {
    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {
        Object schemaID = parameters.get("schemaID");
        if (schemaID == null || !(schemaID instanceof Map)) {
            return null;
        }

        Object resultObject = parameters.get("result");
        if (resultObject == null ||
                !(resultObject instanceof StepResult) ||
                !(((StepResult)resultObject).getResult() instanceof RSXMultipleImageSelectionSurveyResult)) {
            return null;
        }

        RSXMultipleImageSelectionSurveyResult medlSpotResult = (RSXMultipleImageSelectionSurveyResult)((StepResult)resultObject).getResult();

        Map<String, String> resultMap = new HashMap<>();

        for (int i=0; i<medlSpotResult.getSelectedIdentifiers().length; i++) {
            resultMap.put(medlSpotResult.getSelectedIdentifiers()[i], "selected");
        }

        for (int i=0; i<medlSpotResult.getNotSelectedIdentifiers().length; i++) {
            resultMap.put(medlSpotResult.getNotSelectedIdentifiers()[i], "notSelected");
        }

        for (int i=0; i<medlSpotResult.getExcludedIdentifiers().length; i++) {
            resultMap.put(medlSpotResult.getExcludedIdentifiers()[i], "excluded");
        }

        MEDLSpotRawCSVEncodable medlSpot = new MEDLSpotRawCSVEncodable(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                (Map<String, Object>)schemaID,
                medlSpotResult.getSelectedIdentifiers(),
                medlSpotResult.getNotSelectedIdentifiers(),
                medlSpotResult.getExcludedIdentifiers(),
                resultMap
        );

        medlSpot.setStartDate(((StepResult)resultObject).getStartDate());
        medlSpot.setEndDate(((StepResult)resultObject).getEndDate());

        return medlSpot;
    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(com.example.christina.avayadl.RSRPBackendSupport.MEDLSpotRawCSVEncodable.TYPE)) return true;
        else return false;
    }
}
