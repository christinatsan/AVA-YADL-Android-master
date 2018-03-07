package com.example.christina.avayadl.RSTBExtensions;

import android.support.annotation.Nullable;

import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBStepDescriptor;
import edu.cornell.tech.foundry.sdl_rsx_rstbsupport.YADLItemDescriptor;

/**
 * Created by Christina on 2/6/2018.
 */

public class MEDLSpotStepDescriptor extends RSTBStepDescriptor {

    @Nullable
    public String filterKey;
    public List<MEDLItemDescriptor> items;
    public String imagePath = "images/medl";
    public String imageExtension = "jpeg";
    public String itemCellSelectedOverlayImageTitle;
    public String itemCellSelectedOverlayImagePath = "images";
    public String itemCellSelectedOverlayImageExtension = "png";

    public MEDLSpotStepDescriptor() {

    }
}
