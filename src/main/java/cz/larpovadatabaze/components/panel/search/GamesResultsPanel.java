package cz.larpovadatabaze.components.panel.search;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.game.GameDetail;
import cz.larpovadatabaze.components.page.user.UserDetail;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.Rating;
import cz.larpovadatabaze.services.GameService;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GamesResultsPanel extends Panel {
    @SpringBean
    GameService gameService;

    public GamesResultsPanel(String id, String query) {
        super(id);

        if(query == null) {
            query = "";
        }

        List<Game> allResults = gameService.getAll();
        List<Game> searchResults = new ArrayList<Game>();
        for(Game result: allResults){
            if(result.getAutoCompleteData().toLowerCase().contains(query.toLowerCase())){
                searchResults.add(result);
            }
        }

        List<Game> fullResults;
        List<Game> shortResults;

        if(searchResults.size() > 2){
            fullResults = searchResults.subList(0,3);
            shortResults = searchResults.subList(3, searchResults.size());
        } else {
            fullResults = searchResults.subList(0,searchResults.size());
            shortResults = new ArrayList<Game>();
        }

        ListView<Game> fullList = new ListView<Game>("fullGames", fullResults) {
            @Override
            protected void populateItem(ListItem<Game> item) {
                Game game = item.getModelObject();

                PageParameters params = new PageParameters();
                params.add("id", game.getId());

                if(game.getImage() == null){
                    game.setImage(cz.larpovadatabaze.entities.Image.getDefaultGame());
                }

                final BookmarkablePageLink<CsldBasePage> gameLink =
                        new BookmarkablePageLink<CsldBasePage>("gameLink", GameDetail.class, params);
                final Image gameLinkImage = new Image("gameLinkImage",
                        new ContextRelativeResource(game.getImage().getPath()));
                gameLink.add(gameLinkImage);
                item.add(gameLink);

                String gameRatingColor = Rating.getColorOf(gameService.getRatingOfGame(game));
                Label gameRating = new Label("gameRating","");
                gameRating.add(new AttributeAppender("class", Model.of(gameRatingColor), " "));
                item.add(gameRating);

                final BookmarkablePageLink<CsldBasePage> gameLinkContent =
                        new BookmarkablePageLink<CsldBasePage>("gameLinkContent", GameDetail.class, params);
                final Label gameName = new Label("gameName", game.getName());
                gameLinkContent.add(gameName);
                item.add(gameLinkContent);
            }
        };
        add(fullList);

        ListView<Game> othersList = new ListView<Game>("shortGames", shortResults) {
            @Override
            protected void populateItem(ListItem<Game> item) {
                Game game = item.getModelObject();

                PageParameters params = new PageParameters();
                params.add("id", game.getId());

                final BookmarkablePageLink<CsldBasePage> authorShortLink =
                        new BookmarkablePageLink<CsldBasePage>("authorShortLink", GameDetail.class, params);
                final Label shortName = new Label("shortGameName", game.getName());
                final Label shortYear = new Label("shortGameYear", game.getYear());
                authorShortLink.add(shortName);
                authorShortLink.add(shortYear);
                item.add(authorShortLink);
            }
        };
        add(othersList);
    }
}