package com.faren.gw2.gw2applicaton.item;

public enum GWItem_Type {
    ARMOR,
    BACK,
    BAG,
    CONSUMABLE,
    CONTAINER,
    CRAFTING_MATERIAL,
    GATHERING,
    GIZMO,
    MINIPET,
    TOOL,
    TRAIT,
    TRINKET,
    TROPHY,
    UPGRADE_COMPONENT,
    WEAPON;
    
    public String getFormatedName() {
        String n = this.name();
        String word = "";
        for(String w : n.split("_")) {
            w = w.toLowerCase();
            w = Character.toUpperCase(w.charAt(0)) + w.substring(1);
            word += w;
        }
        return word;
    }
}

