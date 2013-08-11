package cz.larpovadatabaze.components.page.user;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.HomePage;
import cz.larpovadatabaze.components.page.author.AuthorDetail;
import cz.larpovadatabaze.components.panel.user.CreateOrUpdateUserPanel;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.services.CsldUserService;
import cz.larpovadatabaze.utils.HbUtils;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * This page allows user to create new or update exiting user.
 */
public class CreateOrUpdateUserPage extends CsldBasePage {
    @SpringBean
    private CsldUserService csldUserService;

    public CreateOrUpdateUserPage(PageParameters params){
        CsldUser csldUser  = null;
        if(!params.isEmpty()){
            Integer id = params.get("id").to(Integer.class);
            csldUser = csldUserService.getById(id);
            if(HbUtils.isProxy(csldUser)){
                csldUser = HbUtils.deproxy(csldUser);
            }
        }
        final boolean isNew = (csldUser == null);

        add(new CreateOrUpdateUserPanel("createOrUpdateUser", csldUser){
            @Override
            protected void onCsldAction(AjaxRequestTarget target, Form<?> form) {
                super.onCsldAction(target, form);

                if(!isNew) {
                    CsldUser user = (CsldUser) form.getModelObject();
                    PageParameters params = new PageParameters();
                    params.add("id", user.getId());

                    throw new RestartResponseException(UserDetail.class, params);
                } else {
                    throw new RestartResponseException(HomePage.class);
                }
            }
        });
    }
}
