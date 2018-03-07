package com.example.christina.avayadl.RSTBExtensions;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBStateHelper;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;
import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.step.YADLSpotAssessmentStep;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLItemDescriptor;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLSpotStepDescriptor;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLSpotStepGenerator extends RSTBBaseStepGenerator {

    public MEDLSpotStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "MEDLSpotAssessment"
        );
    }

    protected String[] excludedIdentifiers(List<MEDLItemDescriptor> items, MEDLSpotStepDescriptor medlSpotStepDescriptor, RSTBTaskBuilderHelper helper) {
        String[] emptyArray = new String[0];

        String filterKey = medlSpotStepDescriptor.filterKey;
        if (StringUtils.isEmpty(filterKey)) {
            return emptyArray;
        }

        RSTBStateHelper stateHelper = helper.getStateHelper();
        if (stateHelper == null){
            return emptyArray;
        }

        byte[] filteredItemsBytes = stateHelper.valueInState(helper.getContext(), filterKey);
        if (filteredItemsBytes == null) {
            return emptyArray;
        }

        String joinedItems = new String(filteredItemsBytes);
        List<String> includedIdentifiers = Arrays.asList((android.text.TextUtils.split(joinedItems, ",")));
        if (includedIdentifiers.size() > 0) {

            ArrayList<String> excludedIdentifiers = new ArrayList<>();
            for (MEDLItemDescriptor item : items) {
                if (!includedIdentifiers.contains(item.identifier)) {
                    excludedIdentifiers.add(item.identifier);
                }
            }

            String[] excludedIdentifierArray = new String[excludedIdentifiers.size()];
            excludedIdentifierArray = excludedIdentifiers.toArray(excludedIdentifierArray);
            return excludedIdentifierArray;
        }
        else {
            return emptyArray;
        }
    }

    protected String generateImagePath(MEDLSpotStepDescriptor medlSpotStepDescriptor, MEDLItemDescriptor itemDescriptor) {
        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(medlSpotStepDescriptor.imagePath)) {
            imageTitleStringBuilder.append(medlSpotStepDescriptor.imagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(itemDescriptor.imageTitle);

        if (!TextUtils.isEmpty(medlSpotStepDescriptor.imageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(medlSpotStepDescriptor.imageExtension);
        }

        return imageTitleStringBuilder.toString();
    }


    protected RSXImageChoice[] generateChoices(MEDLSpotStepDescriptor medlSpotStepDescriptor, List<MEDLItemDescriptor> items) {
        RSXImageChoice[] choices = new RSXImageChoice[items.size()];

        for(int i=0; i<items.size(); i++) {
            MEDLItemDescriptor item = items.get(i);
            String imagePath = this.generateImagePath(medlSpotStepDescriptor, item);
            RSXActivityItem activity = new RSXActivityItem(
                    item.identifier,
                    item.generalDescription,
                    imagePath
            );
            choices[i] = activity.getImageChoice();
        }


        return choices;
    }

    @Nullable
    protected String generateOverlayImageTitle(MEDLSpotStepDescriptor medlSpotStepDescriptor) {

        if (medlSpotStepDescriptor.itemCellSelectedOverlayImageTitle == null ) {
            return null;
        }

        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(medlSpotStepDescriptor.itemCellSelectedOverlayImagePath)) {
            imageTitleStringBuilder.append(medlSpotStepDescriptor.itemCellSelectedOverlayImagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(medlSpotStepDescriptor.itemCellSelectedOverlayImageTitle);

        if (!TextUtils.isEmpty(medlSpotStepDescriptor.itemCellSelectedOverlayImageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(medlSpotStepDescriptor.itemCellSelectedOverlayImageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        MEDLSpotStepDescriptor medlSpotStepDescriptor = helper.getGson().fromJson(jsonObject, MEDLSpotStepDescriptor.class);

        if (medlSpotStepDescriptor == null) {
            return null;
        }

        RSXImageChoice[] choices = this.generateChoices(medlSpotStepDescriptor, medlSpotStepDescriptor.items);
        AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);
        String[] excludedIdentifiers = this.excludedIdentifiers(medlSpotStepDescriptor.items, medlSpotStepDescriptor, helper);
        RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions(jsonObject, helper.getContext());

        options.setItemCellSelectedOverlayImageTitle(this.generateOverlayImageTitle(medlSpotStepDescriptor));

        Step step = new YADLSpotAssessmentStep(
                medlSpotStepDescriptor.identifier,
                medlSpotStepDescriptor.title,
                answerFormat,
                options,
                excludedIdentifiers
        );

        step.setOptional(medlSpotStepDescriptor.optional);
        return Arrays.asList(step);
    }
}
