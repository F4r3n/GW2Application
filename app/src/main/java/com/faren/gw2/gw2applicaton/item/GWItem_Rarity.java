package com.faren.gw2.gw2applicaton.item;

public enum GWItem_Rarity {
    JUNK,
    BASIC,
    FINE,
    MASTERWORK,
    RARE,
    EXOTIC,
    ASCENDED,
    LEGENDARY;

    @Override
    public String toString() {
        String n = this.name();
        n = n.toLowerCase();
        n = Character.toUpperCase(n.charAt(0)) + n.substring(1);
        return n;
    }

}
