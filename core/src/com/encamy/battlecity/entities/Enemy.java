package com.encamy.battlecity.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;


public class Enemy extends Sprite {
    private Vector2 velocity = new Vector2();
    private float speed;
    private float m_animationTime = 0;
    private float health;
    private int score;
    private Animation m_left, m_top, m_right, m_bottom;
    private MapObjects m_walls;

    public Enemy(Vector2 spawnpoint, EnemyProperties property)
    {
        super((TextureAtlas.AtlasRegion)property.bottomAnimation.getKeyFrame(0));

        SetProperty(property);
        SetPosition(spawnpoint);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float deltaTime)
    {
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

    private void SetPosition(Vector2 spawnpoint)
    {
        setX(spawnpoint.x);
        setY(spawnpoint.y);
    }

    private void SetProperty(EnemyProperties property)
    {
        m_left = property.leftAnimation;
        m_top = property.topAnimation;
        m_right = property.rightAnimation;
        m_bottom = property.bottomAnimation;
        speed = property.speed;
        health = property.health;
        score = property.score;
    }
}