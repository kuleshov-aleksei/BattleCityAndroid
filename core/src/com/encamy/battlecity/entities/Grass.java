package com.encamy.battlecity.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.encamy.battlecity.Settings;
import com.encamy.battlecity.utils.Box2dHelpers;

import java.util.EnumSet;

public class Grass extends BaseWall
{
    public Grass(World world, Rectangle rectangle)
    {
        Box2dHelpers.createBox(
                world,
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height,
                true,
                EnumSet.of(Settings.ObjectType.WALL),
                false);

        super.m_world = world;
    }

    @Override
    public void update() {

    }

    @Override
    public void destroy() {

    }
}
