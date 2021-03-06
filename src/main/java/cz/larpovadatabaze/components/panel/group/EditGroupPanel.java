package cz.larpovadatabaze.components.panel.group;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.group.CreateOrUpdateGroupPage;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import cz.larpovadatabaze.security.CsldRoles;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * It consists of link to the page for editing actual group.
 * It is shown only for the logged users, who have rights to manage the group.
 */
public class EditGroupPanel extends Panel {
    private CsldGroup toEdit;

    public EditGroupPanel(String id, CsldGroup toEdit) {
        super(id);
        this.toEdit = toEdit;

        PageParameters params = new PageParameters();
        params.add("id", toEdit.getId());
        BookmarkablePageLink<CsldBasePage> pageLink =
                new BookmarkablePageLink<CsldBasePage>("editGroup", CreateOrUpdateGroupPage.class, params);
        add(pageLink);
    }

    @Override
    protected void onConfigure() {
        boolean isVisible = CsldAuthenticatedWebSession.get().isSignedIn();
        if(isVisible){
            CsldUser logged = ((CsldAuthenticatedWebSession) CsldAuthenticatedWebSession.get()).getLoggedUser();
            if(logged == null) {
                isVisible = false;
            }
            if(logged != null && logged.getRole() <= CsldRoles.USER.getRole()){
                if(!toEdit.getAdministrators().contains(logged)){
                    isVisible = false;
                }
            }
        }

        setVisibilityAllowed(isVisible);
    }
}
