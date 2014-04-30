using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Graphics;

using XNACS1Lib;

namespace BrandanHaertel_NameSpace
{
    class Wall : XNACS1Rectangle
    {
        private enum PatrolState
        {
            still,
            moving,
            atack
        }

        private PatrolState currentState;
        float speed = .25f;
        float startingX;
        float boundry;
        float dir = 1;
        const float displacmentX = 10;
        const float displacmentY = 5;
        const float speedX = 10f / 40f;
        const float speedY = 5f / 40f;
        private bool paused = false;

        public Wall()
        {
            Size = new Vector2(5, 30);
            Center = new Vector2(0, 0);
            Color = Color.Brown;
            currentState = PatrolState.still;
            startingX = 0;
            boundry = .70f * ((Width > Height) ? Width : Height);
            Texture = "wall_moving";
        }

        // UpdateWorld() function
        public void Update(Hero hero)
        {
            Vector2 distance = Center - hero.Center;
            if (currentState == PatrolState.moving && (distance.Length() < boundry))
            {
                currentState = PatrolState.atack;
            }
            else if (currentState == PatrolState.atack && (distance.Length() > boundry))
            {
                currentState = PatrolState.moving;
            }

            switch (currentState)
            {
                case(PatrolState.still):
                    updateStill(hero);
                    break;
                case(PatrolState.moving):
                    updateMoving(hero);
                    break;
                case(PatrolState.atack):
                    updateAttack(hero);
                    break;
            }
        }

        private void updateStill(Hero hero)
        {
            Texture = "wall_moving";
        }

        private void updateMoving(Hero hero)
        {
            Texture = "wall_moving";
            this.Color = Color.Brown;
            speed = speedY;
            if (Math.Abs(CenterY - (XNACS1Base.World.WorldMax.Y / 2)) > displacmentY)
            {
                dir = -dir;
            }
            CenterY += speed * dir;
        }

        private void updateAttack(Hero hero)
        {
            Texture = "bar_invert";
            this.Color = Color.Black;
            speed = speedX;
            
            if (Math.Abs(CenterX - startingX) > displacmentX)
            {
                dir = -dir;
            }
            CenterX += speed * dir;
        }

        public void makeMoving()
        {
            currentState = PatrolState.moving;
            Texture = "wall_moving";
        }

        public void setPos(Vector2 pos)
        {
            Center = pos;
            CenterY += XNACS1Base.RandomFloat(-displacmentY, displacmentY);
            startingX = pos.X;
        }

        public void Pause()
        {
            if (!paused)
            {
                Texture = "wall_paused";
                currentState = PatrolState.still;
                paused = true;
            }
        }
    }
}

