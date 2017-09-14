package dev.brendan.snake.entities.statics;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.Entity;
import java.awt.Graphics;

public abstract class StaticEntity extends Entity{

    public StaticEntity(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
    }
}
