package com.encamy.battlecity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

class Player extends Sprite implements InputProcessor {

    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2;
    private float m_animationTime = 0;
    private Animation m_left, m_top, m_right, m_bottom;

    public Player(Animation left, Animation top, Animation right, Animation bottom)
    {
        super(((TextureAtlas.AtlasRegion) top.getKeyFrame(0)));
        m_left = left;
        m_top = top;
        m_right = right;
        m_bottom = bottom;
    }

    @Override
    public void draw(Batch batch)
    {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float deltaTime)
    {
        // update position
        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);

        // update animation
        m_animationTime += deltaTime;
        if (velocity.x < 0)
        {
            super.setRegion(((TextureAtlas.AtlasRegion) m_left.getKeyFrame(m_animationTime)));
        }
        else if (velocity.x > 0)
        {
            super.setRegion(((TextureAtlas.AtlasRegion) m_right.getKeyFrame(m_animationTime)));
        }
        else if (velocity.y < 0)
        {
            super.setRegion(((TextureAtlas.AtlasRegion) m_bottom.getKeyFrame(m_animationTime)));
        }
        else if (velocity.y > 0)
        {
            super.setRegion(((TextureAtlas.AtlasRegion) m_top.getKeyFrame(m_animationTime)));
        }
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
                velocity.y = speed;
                velocity.x = 0;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                velocity.y = 0;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                velocity.y = 0;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                velocity.x = 0;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        velocity.x = 0;
        velocity.y = 0;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if (screenX > Gdx.graphics.getWidth() * 0.3f)
        {
            return false;
        }

        if (screenY < Gdx.graphics.getHeight() * 0.3f)
        {
            velocity.y = speed;
            velocity.x = 0;
            return  true;
        }

        if (screenY > Gdx.graphics.getHeight() * 0.6f)
        {
            velocity.y = -speed;
            velocity.x = 0;
            return  true;
        }

        if (screenX < Gdx.graphics.getWidth() * 0.15f)
        {
            velocity.x = -speed;
            velocity.y = 0;
            return  true;
        }

        if (screenX > Gdx.graphics.getWidth() * 0.15f)
        {
            velocity.x = speed;
            velocity.y = 0;
            return  true;
        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
