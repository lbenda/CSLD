package cz.larpovadatabaze.components.panel.game;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.Rating;
import cz.larpovadatabaze.exceptions.WrongParameterException;
import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import cz.larpovadatabaze.services.RatingService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * This panel contains Slider which allows user to rate game. It also has info about rating the game, if the game was
 * not rated by the user previously.
 */
public abstract class RatingsPanel extends Panel {
    @SpringBean
    RatingService ratingService;

    private Rating actualRating;

    public RatingsPanel(String id, final Game game) {
        super(id);

        final Form form = new Form("ratingForm");

        CsldUser logged = ((CsldAuthenticatedWebSession) CsldAuthenticatedWebSession.get()).getLoggedUser();
        // Be Careful logged can be null. It is valid value for it.
        int loggedId = (logged != null) ? logged.getId() : -1;
        Integer rating = 0;

        final Model<Integer> ratingOfGame = new Model<Integer>(rating);
        final Label label = new Label("rating", ratingOfGame);
        form.add(label);
        label.setOutputMarkupId(true);

        try {
            actualRating = ratingService.getUserRatingOfGame(loggedId, game.getId());
        } catch (WrongParameterException e) {
            // This should never happen.
            e.printStackTrace();
        }
        if(actualRating != null){
            ratingOfGame.setObject(actualRating.getRating());
            if(actualRating.getUser() == null){
                actualRating.setUser(logged);
                actualRating.setUserId(loggedId);
            }
        } else {
            actualRating = new Rating();
            actualRating.setGame(game);
            actualRating.setGameId(game.getId());
            actualRating.setUser(logged);
            actualRating.setUserId(loggedId);
        }


        AjaxSlider slider = new AjaxSlider("slider", ratingOfGame, label) {
            @Override
            public void onValueChanged(AjaxRequestTarget target)
            {
                saveOrUpdateRating(ratingOfGame);
                onCsldAction(target, form);
            }
        };
        slider.setRange(Slider.Range.MIN).setMax(10);
        slider.setRange(Slider.Range.MAX).setMin(1);
        form.add(slider);

        add(form);
    }

    private void saveOrUpdateRating(Model<Integer> ratingOfGame) {
        Integer rating = ratingOfGame.getObject();
        actualRating.setRating(rating);
        ratingService.saveOrUpdate(actualRating);
    }

    protected void onConfigure() {
        setVisibilityAllowed(CsldAuthenticatedWebSession.get().isSignedIn());
    }

    protected void onCsldAction(AjaxRequestTarget target, Form<?> form){}
}
