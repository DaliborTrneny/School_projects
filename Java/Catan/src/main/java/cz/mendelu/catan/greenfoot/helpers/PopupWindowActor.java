package cz.mendelu.catan.greenfoot.helpers;

import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;

/**
 * Jednoduchý actor sloužící jako základ pro zobrazování vyskakovacích okenv GUI
 * @author xmusil5
 * @version etapa 4
 */
public class PopupWindowActor extends ActorWithAccesibleGameWorld {
    public static final int POPUP_WINDOW_WIDTH = 800;
    public static final int POPUP_WINDOW_HEIGHT = 500;
    public static final int LABEL_OFFSET_UP = 215;


    public PopupWindowActor(int x, int y){
        super(x,y);
        this.setImage("images/game/popupWindow.png");
    }

    @Override
    public void act() {
        super.act();
    }
}
