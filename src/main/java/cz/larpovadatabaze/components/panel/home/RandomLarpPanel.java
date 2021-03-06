package cz.larpovadatabaze.components.panel.home;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.game.GameDetail;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.Rating;
import cz.larpovadatabaze.services.GameService;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * It shows info about randomly choosen Larp from database.
 */
public class RandomLarpPanel extends Panel {
    @SpringBean
    GameService gameService;
    private boolean show = true;

    public RandomLarpPanel(String id) {
        super(id);

        Game game = gameService.getRandomGame();
        if(game == null){
            game = Game.getEmptyGame();
            game.setId(-1);
            show = false;
        }

        PageParameters params = new PageParameters();
        params.add("id", game.getId());

        if(game.getImage() == null){
            game.setImage(cz.larpovadatabaze.entities.Image.getDefaultGame());
        }

        final BookmarkablePageLink<CsldBasePage> gameLink =
                new BookmarkablePageLink<CsldBasePage>("gameIconLink", GameDetail.class, params);
        final Image gameLinkImage = new Image("gameIcon",
                new ContextRelativeResource(game.getImage().getPath()));
        gameLink.add(gameLinkImage);
        add(gameLink);

        Double rating =game.getTotalRating();
        if(rating == null){
            rating = 0d;
        }
        String gameRatingColor = Rating.getColorOf(Math.round(rating));
        Label gameRating = new Label("gameRating","");
        gameRating.add(new AttributeAppender("class", Model.of(gameRatingColor), " "));
        add(gameRating);

        final BookmarkablePageLink<CsldBasePage> gameLinkContent =
                new BookmarkablePageLink<CsldBasePage>("gameLink", GameDetail.class, params);
        final Label gameName = new Label("gameName", game.getName());
        gameLinkContent.add(gameName);
        add(gameLinkContent);

        add(new Label("players", Model.of(game.getPlayers())));
        add(new Label("year", Model.of(game.getYear())));
        add(new Label("rating", Model.of(Math.round((game.getTotalRating() != null) ? game.getTotalRating() : 0))));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisibilityAllowed(show);
    }
}
