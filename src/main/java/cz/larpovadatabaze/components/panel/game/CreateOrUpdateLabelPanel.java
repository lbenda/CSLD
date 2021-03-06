package cz.larpovadatabaze.components.panel.game;

import cz.larpovadatabaze.api.EntityModel;
import cz.larpovadatabaze.api.ValidatableForm;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Label;
import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import cz.larpovadatabaze.services.LabelService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * It is used for creating and updating Labels for games.
 */
public abstract class CreateOrUpdateLabelPanel extends Panel {
    @SpringBean
    LabelService labelService;

    public CreateOrUpdateLabelPanel(String id){
        this(id,Label.getEmptyLabel());
    }

    public CreateOrUpdateLabelPanel(String id, Label label) {
        super(id);
        IModel<Label> labelModel = new EntityModel<Label>(Label.class, label.getId());

        final ValidatableForm<Label> createOrUpdateLabel =
                new ValidatableForm<Label>("createOrUpdateLabel", new CompoundPropertyModel<Label>(labelModel)){};

        createOrUpdateLabel.add(new TextField<String>("name"));
        createOrUpdateLabel.add(new TextArea<String>("description"));
        createOrUpdateLabel.add(new AjaxButton("submit"){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);

                if(createOrUpdateLabel.isValid()){
                    Label label = createOrUpdateLabel.getModelObject();
                    if(saveOrUpdateLabel(label)){
                        onCsldAction(target, form);
                    }
                }
            }
        });

        add(createOrUpdateLabel);
    }

    private boolean saveOrUpdateLabel(Label label) {
        CsldUser loggedUser = ((CsldAuthenticatedWebSession) CsldAuthenticatedWebSession.get()).getLoggedUser();
        label.setAddedBy(loggedUser);
        return labelService.saveOrUpdate(label);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisibilityAllowed(CsldAuthenticatedWebSession.get().isSignedIn());
    }

    protected void onCsldAction(AjaxRequestTarget target, Form<?> form){}
}
