package com.example.christina.avayadl.RSTBExtensions;

import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.descriptors.RSTBElementDescriptor;


/**
 * Created by Christina on 2/5/2018.
 */

public class MEDLFullStepDescriptor extends RSTBElementDescriptor {

    public List<MEDLItemDescriptor> items;
    public String text;
    public boolean optional = true;
    public String imagePath = "images/medl";
    public String imageExtension = "jpeg";
    public String itemCellSelectedOverlayImageTitle;
    public String itemCellSelectedOverlayImagePath = "images";
    public String itemCellSelectedOverlayImageExtension = "png";


}
