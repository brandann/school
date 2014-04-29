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
    class Guard : XNACS1Rectangle
    {
        protected enum PatrolState
        {
            patrol,
            alarmed
        }

        private float speed = .5f;
        private float amplitude;
        private float periods;
        private float frequency;
        private float level = XNACS1Base.World.WorldMax.Y / 2;
        private int flip = 1;
        String currentTexture;
        private PatrolState current_state;
        float boundry = 5f;
        float alarmedMax = .5f, alarmedMin = -.5f;

        public Guard()
        {
            Size = new Vector2(3, 3);
            CenterX = XNACS1Base.RandomFloat(0,XNACS1Base.World.WorldMax.X);
            CenterY = XNACS1Base.World.WorldMax.Y/2;
            currentTexture = Texture = "guardR";
            Color = Color.Black;
            current_state = PatrolState.patrol;
            ShouldTravel = false;
            //set sine data
            setPatrol();
        }

        private void setPatrol()
        {
            amplitude = XNACS1Base.RandomFloat((float)(XNACS1Base.World.WorldMax.Y * .18), (float)(XNACS1Base.World.WorldMax.Y * .22));
            periods = XNACS1Base.RandomFloat(1, 4);
            frequency = periods * 2f * (float)(Math.PI) / XNACS1Base.World.WorldDimension.X;
        }

        // UpdateWorld() function
        public void Update(Hero hero)
        {

            //check if hero is near gaurd
            Vector2 distance = Center - hero.Center;
            if (distance.Length() < boundry)
            {
                current_state = PatrolState.alarmed;
            }
            else
            {
                if (current_state == PatrolState.alarmed)
                {
                    hero.setNoticed();
                }
                current_state = PatrolState.patrol;
            }

            switch (current_state)
            {
                case(PatrolState.patrol):
                    updatePatrol(hero);
                    break;
                case(PatrolState.alarmed):
                    updateAlarmed(hero);
                    break;
            }

            XNACS1Base.World.ClampAtWorldBound(this);
        }

        private void updatePatrol(Hero hero)
        {
            CenterY = (float)(flip * (amplitude * Math.Sin(.25 * CenterX)) + level);
            //CenterY = (float)(1 * (level + amplitude * Math.Sin(CenterX * ((2 * Math.PI * frequency) / XNACS1Base.World.WorldMax.X))));
            CenterX += speed;

            Texture = currentTexture;

            //hit wall
            BoundCollideStatus collideStatus = XNACS1Base.World.CollideWorldBound(this);
            switch (collideStatus)
            {
                case BoundCollideStatus.CollideLeft:
                    amplitude = XNACS1Base.RandomFloat(5f, 20f);
                    frequency = XNACS1Base.RandomFloat(0.125f, 2f);
                    speed = -speed; flip = -flip;
                    currentTexture = Texture = "guardR";
                    break;
                case BoundCollideStatus.CollideRight:
                    amplitude = XNACS1Base.RandomFloat(10f, 20f);
                    frequency = XNACS1Base.RandomFloat(0.125f, 10f);
                    speed = -speed; flip = -flip;
                    currentTexture = Texture = "guardL";
                    break;
            }
        }

        private void updateAlarmed(Hero hero)
        {
            Texture = "guardScare";
            Velocity = new Vector2(XNACS1Base.RandomFloat(alarmedMin, alarmedMax), XNACS1Base.RandomFloat(alarmedMin, alarmedMax));
            Center += Velocity;
        }

    }
}
