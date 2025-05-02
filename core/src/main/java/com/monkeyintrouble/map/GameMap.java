import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final Texture[] tileTextures;
    private final Room[] rooms;
    private final List<BoxTrap> boxTraps;
    private final GameUI gameUI;

    public GameMap(Texture[] tileTextures, Room[] rooms, GameUI gameUI) {
        this.tileTextures = tileTextures;
        this.rooms = rooms;
        this.boxTraps = new ArrayList<>();
        this.gameUI = gameUI;
        initializeBoxTraps();
    }

    private void initializeBoxTraps() {
        for (Room room : rooms) {
            for (int y = 0; y < room.mapData.length; y++) {
                for (int x = 0; x < room.mapData[y].length; x++) {
                    int tileId = room.mapData[y][x];
                    if (tileId == 31) { // Trap tile
                        float trapX = (x + room.offsetX) * TILE_SIZE;
                        float trapY = (room.mapData.length - y - 1) * TILE_SIZE + (room.offsetY * TILE_SIZE);
                        
                        // Find the corresponding button and box
                        float buttonX = 0, buttonY = 0;
                        float boxX = 0, boxY = 0;
                        float pushableBoxX = 0, pushableBoxY = 0;
                        
                        for (int by = 0; by < room.mapData.length; by++) {
                            for (int bx = 0; bx < room.mapData[by].length; bx++) {
                                int btileId = room.mapData[by][bx];
                                float worldX = (bx + room.offsetX) * TILE_SIZE;
                                float worldY = (room.mapData.length - by - 1) * TILE_SIZE + (room.offsetY * TILE_SIZE);
                                
                                if (btileId == 32) { // Button
                                    buttonX = worldX;
                                    buttonY = worldY;
                                } else if (btileId == 39) { // Box
                                    boxX = worldX;
                                    boxY = worldY;
                                } else if (btileId == 42) { // Pushable box
                                    pushableBoxX = worldX;
                                    pushableBoxY = worldY;
                                }
                            }
                        }
                        
                        if (buttonX != 0 && buttonY != 0 && boxX != 0 && boxY != 0) {
                            boxTraps.add(new BoxTrap(trapX, trapY, boxX, boxY, buttonX, buttonY, pushableBoxX, pushableBoxY, gameUI));
                        }
                    }
                }
            }
        }
    }
} 