package cz.mendelu.catan.greenfoot.helpers;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import cz.mendelu.catan.greenfoot.GameWorld;
import greenfoot.Greenfoot;


/**
 * Reprezentace obecného tlačítka s nastavitelnou akcí na kliknutí
 *
 * @author xmusil5
 * @version etapa 4
 */
public class ButtonActor extends ActorWithAccesibleGameWorld {
    GreenfootButtonOnClickListener listener;

    public ButtonActor(String image, GreenfootButtonOnClickListener listener, int x, int y){
        super(x,y);
        this.setImage(String.format(image));
        this.listener = listener;
    }


    /**
     * @author xmusil5
     * @version etapa 4
     */
    public interface GreenfootButtonOnClickListener{
        ButtonActor parentActor = null;
        void onClick(GameWorld gameWorld);
    }


    /**
     * Při kliknutí na tlačítko se provede dále specifikovaná akce
     * @author xmusil5
     * @version etapa 4
     */
    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)){
            listener.onClick(getGameWorld());
        }
    }
}

