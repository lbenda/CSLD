package cz.larpovadatabaze.components.page.search;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.panel.game.FilterGamesPanel;
import cz.larpovadatabaze.components.panel.search.GamesResultsPanel;
import cz.larpovadatabaze.components.panel.search.UserResultsPanel;
import cz.larpovadatabaze.entities.Label;
import cz.larpovadatabaze.models.FilterGame;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;

import java.util.List;

/**
 *
 */
public class SearchResults extends CsldBasePage {
    public SearchResults(PageParameters params) {
        String query = params.get("queryString").toString();

        Image searchIcon = new Image("searchIcon", new ContextRelativeResource(cz.larpovadatabaze.entities.Image.getSearchResultsIconPath()));
        add(searchIcon);

        final GamesResultsPanel gamesResultsPanel = new GamesResultsPanel("games", query);
        gamesResultsPanel.setOutputMarkupId(true);
        add(gamesResultsPanel);
        final UserResultsPanel userResultsPanel = new UserResultsPanel("users", query);
        userResultsPanel.setOutputMarkupId(true);
        add(userResultsPanel);

        add(new FilterGamesPanel("gamePanel"){
            @Override
            public void onCsldEvent(AjaxRequestTarget target, Form<?> form, List<Label> allLabels) {
                FilterGame filters = (FilterGame) form.getModelObject();
                gamesResultsPanel.reload(target, filters, allLabels);
            }
        });

    }
}
