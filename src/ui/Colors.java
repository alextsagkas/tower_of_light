package ui;

import java.awt.*;


/**
 * Application wide class to provide Colors to all UI component implementations in a centralized and non-contradicting
 * way.
 * <p>
 * All the colors are taken from the <a href="https://tailwindcss.com/docs/customizing-colors">tailwindcss.com</a>
 * website where the creators of the framework have dedicated extreme amount of effort to create the beautiful palette.
 */
public final class Colors {
    /**
     * slate-50
     */
    public static Color backgroundColor = new Color(0xf8fafc);

    /**
     * slate-950
     */
    public static Color borderColor = new Color(0x020617);

    /**
     * slate-100
     */
    public static Color playerColor = new Color(0xf1f5f9);

    /**
     * slate-900
     */
    public static Color undiscoveredColor = new Color(0x0f172a);

    /**
     * slate-800
     */
    public static Color wallTileInvisibleColor = new Color(0x1e293b);
    /**
     * slate-700
     */
    public static Color wallTileVisibleColor = new Color(0x334155);

    /**
     * slate-500
     */
    public static Color floorTileInvisibleColor = new Color(0x64748b);
    /**
     * slate-400
     */
    public static Color floorTileVisibleColor = new Color(0x94a3b8);

    /**
     * yellow-950
     */
    public static Color lightWallTileInvisibleColor = new Color(0x422006);
    /**
     * yellow-900
     */
    public static Color lightWallTileVisibleColor = new Color(0x713f12);

    /**
     * yellow-800
     */
    public static Color lightFloorTileInvisibleColor = new Color(0x854d0e);
    /**
     * yellow-700
     */
    public static Color lightFloorTileVisibleColor = new Color(0xa16207);

    /**
     * yellow-600
     */
    public static Color spellTileInvisibleColor = new Color(0xca8a04);
    /**
     * yellow-500
     */
    public static Color spellTileVisibleColor = new Color(0xeab308);

    /**
     * yellow-400
     */
    public static Color exitTileInvisibleColor = new Color(0xfacc15);
    /**
     * yellow-300
     */
    public static Color exitTileVisibleColor = new Color(0xfde047);

    /**
     * gray-100
     */
    public static Color infoPanelBackgroundColor = new Color(0xf3f4f6);

    /**
     * gray-950
     */
    public static Color infoPanelForegroundColor = new Color(0x030712);
}
