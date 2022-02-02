package cz.mendelu.catan.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum ResourceType implements Serializable {
    LUMBER,
    WOOL,
    BRICK,
    GRAIN,
    ORE,
    EMPTY;


    public static String getImagePathByResourceType(ResourceType resourceType){
        String imagePath = "";
        switch (resourceType){
            case LUMBER:{
                imagePath = "images/resourceTypeIcons/lumberIcon.png";
                break;
            }
            case WOOL:{
                imagePath = "images/resourceTypeIcons/woolIcon.png";
                break;
            }
            case ORE:{
                imagePath = "images/resourceTypeIcons/oreIcon.png";
                break;
            }
            case GRAIN:{
                imagePath = "images/resourceTypeIcons/grainIcon.png";
                break;
            }
            case BRICK:{
                imagePath = "images/resourceTypeIcons/brickIcon.png";
                break;
            }
            case EMPTY:{
                throw new IllegalArgumentException("ResourceType->getImagePathByResourceType - ResourceTypeCanNotBeEmpty");
            }
        }
        return imagePath;
    }

    public static String getResourceTypeString(ResourceType resourceType){
        String resourceTypeString = "";
        switch (resourceType){
            case LUMBER:{
                resourceTypeString = "Drevo";
                break;
            }
            case WOOL:{
                resourceTypeString = "Vlna";
                break;
            }
            case ORE:{
                resourceTypeString = "Ruda";
                break;
            }
            case GRAIN:{
                resourceTypeString = "Obili";
                break;
            }
            case BRICK:{
                resourceTypeString = "Cihly";
                break;
            }
            case EMPTY:{
                resourceTypeString = "Poust";
                break;
            }
        }
        return resourceTypeString;
    }


    public static List<ResourceType>getAllTradeAbleResourceTypes(){
        List<ResourceType> types = new ArrayList<>();
        types.add(ResourceType.LUMBER);
        types.add(ResourceType.WOOL);
        types.add(ResourceType.BRICK);
        types.add(ResourceType.GRAIN);
        types.add(ResourceType.ORE);
        return types;
    }
    public static List<ResourceType>getResourceTypesForUpgradingVillage(){
        List<ResourceType> types = new ArrayList<>();
        types.add(ResourceType.GRAIN);
        types.add(ResourceType.GRAIN);
        types.add(ResourceType.ORE);
        types.add(ResourceType.ORE);
        types.add(ResourceType.ORE);
        return types;
    }

    public static List<ResourceType>getResourceTypesForBuildingVillage(){
        List<ResourceType> typesForBuildingVillage = new ArrayList<>();
        typesForBuildingVillage.add(ResourceType.GRAIN);
        typesForBuildingVillage.add(ResourceType.BRICK);
        typesForBuildingVillage.add(ResourceType.LUMBER);
        typesForBuildingVillage.add(ResourceType.WOOL);

        return typesForBuildingVillage;
    }

    public static List<ResourceType>getResourceTypesForBuyingActionCard(){
        List<ResourceType> typesForBuyingActionCard = new ArrayList<>();
        typesForBuyingActionCard.add(ResourceType.GRAIN);
        typesForBuyingActionCard.add(ResourceType.ORE);
        typesForBuyingActionCard.add(ResourceType.WOOL);

        return typesForBuyingActionCard;
    }

    public static List<ResourceType>getResourceTypesForBuildingRoad(){
        List<ResourceType> typesForBuildingRoad = new ArrayList<>();
        typesForBuildingRoad.add(ResourceType.BRICK);
        typesForBuildingRoad.add(ResourceType.LUMBER);

        return typesForBuildingRoad;
    }
}
