package com.example.christina.avayadl.RSTBExtensions;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;
import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.step.MEDLFullAssessmentStep;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.step.YADLSpotAssessmentStep;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLDifficultyChoiceDescriptor;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLFullStepDescriptor;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLItemDescriptor;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLSpotStepDescriptor;

/**
 * Created by Christina on 2/5/2018.
 */

public class MEDLFullStepGenerator extends RSTBBaseStepGenerator {

    public MEDLFullStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "MEDLFullAssessment"
        );
    }

    protected String generateImagePath(MEDLFullStepDescriptor medlFullStepDescriptor, MEDLItemDescriptor itemDescriptor) {
        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(medlFullStepDescriptor.imagePath)) {
            imageTitleStringBuilder.append(medlFullStepDescriptor.imagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(itemDescriptor.imageTitle);

        if (!TextUtils.isEmpty(medlFullStepDescriptor.imageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(medlFullStepDescriptor.imageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    @Nullable
    protected String generateOverlayImageTitle(MEDLFullStepDescriptor medlFullStepDescriptor) {

        if (medlFullStepDescriptor.itemCellSelectedOverlayImageTitle == null ) {
            return null;
        }

        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(medlFullStepDescriptor.itemCellSelectedOverlayImagePath)) {
            imageTitleStringBuilder.append(medlFullStepDescriptor.itemCellSelectedOverlayImagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(medlFullStepDescriptor.itemCellSelectedOverlayImageTitle);

        if (!TextUtils.isEmpty(medlFullStepDescriptor.itemCellSelectedOverlayImageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(medlFullStepDescriptor.itemCellSelectedOverlayImageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    protected String[] getCategories (List<MEDLItemDescriptor> medlItems){
        ArrayList<String> categories = new ArrayList<String>();
        for (int i =0; i<medlItems.size(); i++) {
            categories.add(medlItems.get(i).category);

        }

        ArrayList<String> noDupCategories = Lists.newArrayList(Sets.newHashSet(categories));
        String[] categoriesList = noDupCategories.toArray(new String[0]);

        return categoriesList;
    }

    protected RSXImageChoice[] generateChoices(MEDLFullStepDescriptor medlFullStepDescriptor, List<MEDLItemDescriptor> items) {
        RSXImageChoice[] choices = new RSXImageChoice[items.size()];

        for(int i=0; i<items.size(); i++) {
            MEDLItemDescriptor item = items.get(i);
            String imagePath = this.generateImagePath(medlFullStepDescriptor, item);
            RSXActivityItem activity = new RSXActivityItem(
                    item.identifier,
                    item.specificDescription,
                    imagePath
            );
            choices[i] = activity.getImageChoice();
        }


        return choices;
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        MEDLFullStepDescriptor medlFullStepDescriptor = helper.getGson().fromJson(jsonObject, MEDLFullStepDescriptor.class);

        if (medlFullStepDescriptor == null) {
            return null;
        }

        ArrayList<Step> medlSteps = new ArrayList<>();

        String[] categories = this.getCategories(medlFullStepDescriptor.items);

        for (int i=0; i<categories.length; i++){

            ArrayList<MEDLItemDescriptor> currentItems = new ArrayList<>();

            for (int j=0; j<medlFullStepDescriptor.items.size(); j++){
                if (Objects.equals(medlFullStepDescriptor.items.get(j).category, categories[i])){
                    currentItems.add(medlFullStepDescriptor.items.get(j));
                }
            }

            RSXImageChoice[] choices = this.generateChoices(medlFullStepDescriptor, currentItems);
            AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);
            RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions(jsonObject, helper.getContext());

            options.setItemCellSelectedOverlayImageTitle(this.generateOverlayImageTitle(medlFullStepDescriptor));

            String identifier = "medl_full." + categories[i];

            Step step = new MEDLFullAssessmentStep(
                    identifier,
                    "title",
                    categories[i],
                    answerFormat,
                    options
            );

            step.setOptional(medlFullStepDescriptor.optional);
            medlSteps.add(step);

        }


        return medlSteps;
    }

}
