package cz.mendelu.catan.greenfoot.gamemode.playing.actors;

import cz.mendelu.catan.game.ResourceType;
import cz.mendelu.catan.greenfoot.ActorWithAccesibleGameWorld;
import greenfoot.Greenfoot;

/**
 * ResourceTypeActor umožnuje výběr suroviny při herních akcí
 * (např. při vyložení karty Monopol hráč vybírá surovinu, kterou budou ostatní hráči odevzdávat)
 *
 * @author xtrneny1
 * @version etapa 4
 */
public class ResourceTypeActor extends ActorWithAccesibleGameWorld {
    ResourceType resourceType;

    public ResourceTypeActor(ResourceType resourceType, int x, int y) {
        super(x,y);
        this.resourceType = resourceType;
        setBackground();
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this) && getGameWorld().playerPolicyAllowsProceedingAction()) {
            switch (getGameWorld().getState()){
                case INVENTION_CARD_PLAYED:
                    //do nothing
                    break;
            }
        }
    }


    private void setBackground(){
        switch (this.resourceType){
            case LUMBER:{
                setImage("images/resourceTypeIcons/lumberIcon.png");
                break;
            }
            case WOOL:{
                setImage("images/resourceTypeIcons/woolIcon.png");
                break;
            }
            case ORE:{
                setImage("images/resourceTypeIcons/oreIcon.png");
                break;
            }
            case GRAIN:{
                setImage("images/resourceTypeIcons/grainIcon.png");
                break;
            }
            case BRICK:{
                setImage("images/resourceTypeIcons/brickIcon.png");
                break;
            }
        }
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
