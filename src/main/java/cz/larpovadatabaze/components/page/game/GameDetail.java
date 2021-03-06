package cz.larpovadatabaze.components.page.game;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.panel.game.*;
import cz.larpovadatabaze.components.panel.user.SimpleListUsersPanel;
import cz.larpovadatabaze.entities.Comment;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.UserPlayedGame;
import cz.larpovadatabaze.services.GameService;
import cz.larpovadatabaze.utils.HbUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class GameDetail extends CsldBasePage {
    @SpringBean
    GameService gameService;

    private Game game;

    public GameDetail(PageParameters params){
        Integer gameId = params.get("id").to(Integer.class);
        game = gameService.getById(gameId);
        if(HbUtils.isProxy(game)){
            game = HbUtils.deproxy(game);
        }


        List<Comment> reversedComments = new ArrayList<Comment>(game.getComments());
        Collections.reverse(reversedComments);
        final CommentsListPanel comments = new CommentsListPanel("commentsList", reversedComments);
        comments.setOutputMarkupId(true);
        final RatingsResultPanel ratingsResult = new RatingsResultPanel("ratingsResults", game);
        ratingsResult.setOutputMarkupId(true);

        List<CsldUser> wantedBy = new ArrayList<CsldUser>();
        for(UserPlayedGame played : game.getPlayed()){
            if(played.getState() == UserPlayedGame.WANT_TO_PLAY){
                wantedBy.add(played.getPlayerOfGame());
            }
        }
        final SimpleListUsersPanel wantedToPlay =  new SimpleListUsersPanel("wantsToPlay", wantedBy);
        wantedToPlay.setOutputMarkupId(true);

        add(new PlayedPanel("playedPanel", game) {
            @Override
            protected void onCsldAction(AjaxRequestTarget target, Form<?> form) {
                super.onCsldAction(target, form);

                Game gameInner = gameService.getById(game.getId());
                if (HbUtils.isProxy(gameInner)) {
                    gameInner = HbUtils.deproxy(gameInner);
                }
                List<CsldUser> wantedBy = new ArrayList<CsldUser>();
                for (UserPlayedGame played : gameInner.getPlayed()) {
                    if (played.getState() == UserPlayedGame.WANT_TO_PLAY) {
                        wantedBy.add(played.getPlayerOfGame());
                    }
                }
                wantedToPlay.reload(target, wantedBy);
            }
        });
        add(new GameDetailPanel("gameDetail", game));
        add(new CommentsPanel("addComment", game){
            @Override
            protected void onCsldAction(AjaxRequestTarget target, Form<?> form) {
                super.onCsldAction(target, form);

                Game gameInner = gameService.getById(game.getId());
                if(HbUtils.isProxy(gameInner)){
                    gameInner = HbUtils.deproxy(gameInner);
                }
                comments.reload(target, gameInner.getComments());
            }
        });
        add(comments);

        add(ratingsResult);
        add(new CanNotRatePanel("canNotRatePanel"));
        add(new RatingsPanel("ratingsPanel", game){
            @Override
            protected void onCsldAction(AjaxRequestTarget target, Form<?> form) {
                super.onCsldAction(target, form);

                ratingsResult.reload(target);
            }
        });

        EditGamePanel editGamePanel = new EditGamePanel("editGamePanel", game);
        add(editGamePanel);

        DeleteGamePanel deleteGamePanel = new DeleteGamePanel("deleteGamePanel", game);
        add(deleteGamePanel);

        List<Game> similarGames = gameService.getSimilar(game);
        add(new GameListPanel("similarGames", similarGames));

        List<Game> gamesOfAuthors = gameService.gamesOfAuthors(game);
        add(new GameListPanel("gamesOfAuthors", gamesOfAuthors));

        add(wantedToPlay);
    }
}
