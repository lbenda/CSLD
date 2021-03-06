package cz.larpovadatabaze.models;

import org.apache.wicket.model.Model;

/**
 * Model used for selecting and unselecting based on the class attribute.
 */
public class ClassContentModel extends Model<String> {
    private boolean isSelected = false;

    @Override
    public String getObject() {
        if(isSelected) {
            return "selected";
        }
        return "";
    }

    public void select(){
        isSelected = !isSelected;
    }
}
