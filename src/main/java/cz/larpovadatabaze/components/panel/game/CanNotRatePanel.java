package cz.larpovadatabaze.components.panel.game;

import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * This panel is shown when user does not have right to rate the game.
 * Therefore when he is not logged.
 */
public class CanNotRatePanel extends Panel {
    public CanNotRatePanel(String id) {
        super(id);
    }

    protected void onConfigure() {
        setVisibilityAllowed(!CsldAuthenticatedWebSession.get().isSignedIn());
    }
}
