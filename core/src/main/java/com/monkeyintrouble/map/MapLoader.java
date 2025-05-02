public static GameMap loadLevel1(GameUI gameUI) {
    // Load textures
    Texture[] tileTextures = loadTextures();
    
    // Create rooms
    Room[] rooms = createRooms();
    
    // Create and return game map
    return new GameMap(tileTextures, rooms, gameUI);
} 