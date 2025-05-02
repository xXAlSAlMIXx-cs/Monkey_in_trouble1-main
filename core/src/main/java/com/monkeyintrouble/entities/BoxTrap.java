package com.monkeyintrouble.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.monkeyintrouble.ui.GameUI;

public class BoxTrap {
    private static final float TILE_SIZE = 32f;

    // Texture indices
    private static final int TRAP_ACTIVE = 31;
    private static final int TRAP_INACTIVE = 30;
    private static final int BOX_NORMAL = 39;
    private static final int BOX_PRESSED = 41;
    private static final int BUTTON = 32;
    private static final int PUSHABLE_BOX = 42;

    private final float trapX;
    private final float trapY;
    private final float boxX;
    private final float boxY;
    private final float buttonX;
    private final float buttonY;
    private boolean isTriggered;
    private final Rectangle trapHitbox;
    private final Rectangle boxHitbox;
    private final Rectangle buttonHitbox;
    private final Rectangle pushableBoxHitbox;
    private float pushableBoxX;
    private float pushableBoxY;
    private final GameUI gameUI;

    public BoxTrap(float trapX, float trapY, float boxX, float boxY, float buttonX, float buttonY, float pushableBoxX, float pushableBoxY, GameUI gameUI) {
        this.trapX = trapX;
        this.trapY = trapY;
        this.boxX = boxX;
        this.boxY = boxY;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.pushableBoxX = pushableBoxX;
        this.pushableBoxY = pushableBoxY;
        this.isTriggered = false;
        this.gameUI = gameUI;

        // Create hitboxes
        this.trapHitbox = new Rectangle(trapX, trapY, TILE_SIZE, TILE_SIZE);
        this.boxHitbox = new Rectangle(boxX, boxY, TILE_SIZE, TILE_SIZE);
        this.buttonHitbox = new Rectangle(buttonX, buttonY, TILE_SIZE, TILE_SIZE);
        this.pushableBoxHitbox = new Rectangle(pushableBoxX, pushableBoxY, TILE_SIZE, TILE_SIZE);
    }

    public void update(float deltaTime) {
        // Check if pushable box is on button
        if (!isTriggered) {
            // Get the exact positions for debugging
            System.out.println("Box position: (" + pushableBoxX + ", " + pushableBoxY + ")");
            System.out.println("Button position: (" + buttonX + ", " + buttonY + ")");
            
            // Check for overlap
            boolean isOverlapping = pushableBoxHitbox.overlaps(buttonHitbox);
            System.out.println("Checking overlap status: " + isOverlapping);

            if (isOverlapping) {
                isTriggered = true;
                gameUI.setSuccessMessage("test succeeded");
                System.out.println("\n*********************************");
                System.out.println("*       test succeeded          *");
                System.out.println("*********************************\n");
            }
        }
    }

    public void render(SpriteBatch batch, Texture[] tileTextures) {
        // Draw trap
        int trapTexture = isTriggered ? TRAP_INACTIVE : TRAP_ACTIVE;
        batch.draw(tileTextures[trapTexture], trapX, trapY, TILE_SIZE, TILE_SIZE);

        // Draw box
        int boxTexture = isTriggered ? BOX_PRESSED : BOX_NORMAL;
        batch.draw(tileTextures[boxTexture], boxX, boxY, TILE_SIZE, TILE_SIZE);

        // Draw button
        batch.draw(tileTextures[BUTTON], buttonX, buttonY, TILE_SIZE, TILE_SIZE);

        // Draw pushable box
        batch.draw(tileTextures[PUSHABLE_BOX], pushableBoxX, pushableBoxY, TILE_SIZE, TILE_SIZE);
    }

    public Rectangle getTrapHitbox() {
        return trapHitbox;
    }

    public Rectangle getBoxHitbox() {
        return boxHitbox;
    }

    public Rectangle getPushableBoxHitbox() {
        return pushableBoxHitbox;
    }

    public void movePushableBox(float deltaX, float deltaY) {
        // Update both the box position and hitbox position
        pushableBoxX += deltaX;
        pushableBoxY += deltaY;
        pushableBoxHitbox.x = pushableBoxX;
        pushableBoxHitbox.y = pushableBoxY;

        // Print detailed position information
        System.out.println("\n=========================================");
        System.out.println("BOX MOVEMENT TRACKING");
        System.out.println("=========================================");
        System.out.println("Box moved by: (" + deltaX + ", " + deltaY + ")");
        System.out.println("New box position: (" + pushableBoxX + ", " + pushableBoxY + ")");
        System.out.println("Box hitbox: x=" + pushableBoxHitbox.x + 
                         ", y=" + pushableBoxHitbox.y + 
                         ", w=" + pushableBoxHitbox.width + 
                         ", h=" + pushableBoxHitbox.height);
        System.out.println("Button position: (" + buttonX + ", " + buttonY + ")");
        System.out.println("Button hitbox: x=" + buttonHitbox.x + 
                         ", y=" + buttonHitbox.y + 
                         ", w=" + buttonHitbox.width + 
                         ", h=" + buttonHitbox.height);
        System.out.println("Distance to button: " + 
                         Math.sqrt(Math.pow(pushableBoxX - buttonX, 2) + 
                                 Math.pow(pushableBoxY - buttonY, 2)));
        System.out.println("=========================================\n");
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void reset() {
        isTriggered = false;
        pushableBoxX = boxX;
        pushableBoxY = boxY;
        pushableBoxHitbox.x = pushableBoxX;
        pushableBoxHitbox.y = pushableBoxY;
    }
} 