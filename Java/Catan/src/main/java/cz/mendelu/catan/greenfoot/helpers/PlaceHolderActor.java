package cz.mendelu.catan.greenfoot.helpers;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;

/**
 * Druh "nádoby" sloužící k zobrazení jednoduchých údajů v GUI
 * @author xmusil5
 * @version etapa 4
 */
public class PlaceHolderActor extends ActorWithAccesibleGameWorld {
    private PlaceHolderEventListener listener;

    public PlaceHolderActor(int x, int y, PlaceHolderEventListener listener){
        super(x,y);
        this.listener = listener;
    };

    public interface PlaceHolderEventListener{
        void onUpdate(PlaceHolderActor thisActor);
    }

    @Override
    public void act() {
        super.act();
        if (this.listener != null) this.listener.onUpdate(this);
    }
}
