package cz.larpovadatabaze.components.panel.game;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.author.AuthorDetail;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;

import java.util.List;

/**
 *
 */
public class GameDetailPanel extends Panel {
    public GameDetailPanel(String id, Game game) {
        super(id);


        final Image adminImage = new Image("gameImage",
                new ContextRelativeResource((game.getImage() != null) ? game.getImage().getPath(): ""));
        add(adminImage);
        Label gameName =
                new Label("gameName", game.getName());
        add(gameName);

        List<cz.larpovadatabaze.entities.Label> labels = game.getLabels();
        ListView<cz.larpovadatabaze.entities.Label> view = new ListView<cz.larpovadatabaze.entities.Label>("labels", labels) {
            @Override
            protected void populateItem(ListItem<cz.larpovadatabaze.entities.Label> item) {
                cz.larpovadatabaze.entities.Label label = item.getModelObject();
                item.add(new Label("label", label.getName()));
            }
        };
        add(view);

        Label players = new Label("players", game.getPlayers());
        add(players);
        Label men = new Label("men", game.getMenRole());
        add(men);
        Label women = new Label("women", game.getWomenRole());
        add(women);
        Label both = new Label("both", game.getBothRole());
        add(both);

        Label hours = new Label("hours", game.getHours());
        add(hours);
        Label days = new Label("days", game.getDays());
        add(days);
        Label years = new Label("year", game.getYear());
        add(years);

        add(new ExternalLink("webGameLink", game.getWeb(), game.getWeb()));

        List<CsldUser> authors = game.getAuthors();
        ListView<CsldUser> authorsList = new ListView<CsldUser>("authors",authors) {
            @Override
            protected void populateItem(ListItem<CsldUser> item) {
                CsldUser author = item.getModelObject();
                PageParameters params = new PageParameters();
                params.add("id", author.getId());

                Link<CsldBasePage> authorDetailLink = new BookmarkablePageLink<CsldBasePage>("authorDetailLink", AuthorDetail.class, params);
                authorDetailLink.add(
                        new Label("authorName", author.getPerson().getName()));
                item.add(authorDetailLink);
            }
        };
        add(authorsList);

        Label description = new Label("description",game.getDescription());
        add(description);
    }
}
