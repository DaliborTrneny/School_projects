package cz.mendelu.catan.greenfoot;

import greenfoot.Actor;

/**
 * Slouží jako předek všem dalším grafickým prvkům. Umožnuje včasné uložení souřadnic při deklaraci a poskytuje přístup k hernímu světu (přecastovanému)
 *
 * @author xmusil5
 * @version etapa 4
 */
public abstract class ActorWithAccesibleGameWorld extends Actor {
    protected int horizontalCoordinate;
    protected int verticalCoordinate;

    public ActorWithAccesibleGameWorld(int horizontalCoordinate, int verticalCoordinate){
        this.horizontalCoordinate = horizontalCoordinate;
        this.verticalCoordinate = verticalCoordinate;
    }

    protected GameWorld getGameWorld(){
        return (GameWorld) getWorld();
    }

    public int getHorizontalCoordinate(){
        return this.horizontalCoordinate;
    }

    public int getVerticalCoordinate(){
        return this.verticalCoordinate;
    }
}
