package io.github.skywindfox.dreamachive;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 简单封装的菜单按钮类
 */
public class MenuButton {
    private Texture texture;
    private float x, y, width, height;

    public MenuButton(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public boolean isClicked(float clickX, float clickY) {
        return clickX >= x && clickX <= x + width &&
               clickY >= y && clickY <= y + height;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
