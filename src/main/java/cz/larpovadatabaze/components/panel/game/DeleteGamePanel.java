package cz.larpovadatabaze.components.panel.game;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.game.DeleteGamePage;
import cz.larpovadatabaze.components.page.group.CreateOrUpdateGroupPage;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import cz.larpovadatabaze.security.CsldRoles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * This panel contains link for deleting the game.
 */
public class DeleteGamePanel extends Panel {
    public DeleteGamePanel(String id, Game game) {
        super(id);

        PageParameters params = new PageParameters();
        params.add("id", game.getId());
        BookmarkablePageLink<CsldBasePage> pageLink =
                new BookmarkablePageLink<CsldBasePage>("deleteGame", DeleteGamePage.class, params);
        add(pageLink);
    }

    @Override
    protected void onConfigure() {
        setVisibilityAllowed(((CsldAuthenticatedWebSession) CsldAuthenticatedWebSession.get()).getLoggedUser().getRole() > CsldRoles.USER.getRole());
    }
}