package panel;

public abstract class DefaultPanel {
    private static final int originalTileSize = 16;
    private static final int scale = 4;

    public static final int tileSize = originalTileSize * scale;
    public static final int maxScreenCol = 26;
    public static final int maxScreenRow = 15;
    
    public final static int screenWidth = tileSize * maxScreenCol;
    public final static int screenHeight = tileSize * maxScreenRow;

    public static final int FPS = 30;
}
