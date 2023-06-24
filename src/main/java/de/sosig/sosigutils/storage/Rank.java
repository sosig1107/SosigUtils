package de.sosig.sosigutils.storage;

public enum Rank {
    OWNER("§7[§c§lOwner§7] ", new String[]{"*"}),
    ADMIN("§7[§4§lAdmin§7] ", new String[]{"*"}),
    MODERATOR("§7[§a§lMod§7] ", new String[]{"minecraft.command.difficulty", "minecraft.command.gamemode", "minecraft.command.gamerule", "minecraft.command.kick", "minecraft.command.kill", "minecraft.command.whitelist"}),
    TWITCH("§7[§d§lTwitch§7] ", new String[]{}),
    YOUTUBE("§7[§4§lYouTube§7] ", new String[]{}),
    VIP("§7[§6§lVIP§7] ", new String[]{}),
    PLAYER("§7[§lPlayer§7] ", new String[]{});
    private String prefix;
    private String[] permissions;
    Rank(String prefix, String[] permissions){
        this.prefix = prefix;
        this.permissions = permissions;
    }
    public String getPrefix(){
        return prefix;
    }
    public String[] getPermissions(){
        return permissions;
    }
}
