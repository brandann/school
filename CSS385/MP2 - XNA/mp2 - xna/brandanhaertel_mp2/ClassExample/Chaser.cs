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
    class Chaser : XNACS1Circle
    {
        private enum ChaseState
        {
            inactive,
            active
        }

        private ChaseState currentState = ChaseState.inactive;
        private const float kPatrolSpeed = 1.1f; //5f / 40f; 
        private const int lifespan = 800;
        private int lifecount = 0;
        private int chaserLoaded = 0;

        public Chaser()
        {
            Radius = 1.5f;
            CenterX = 0; 
            CenterY = 0;
            Visible = false;
            Texture = "chaser";
        }

        // UpdateWorld() function
        public void Update(Hero hero)
        {

            Vector2 toTarget = hero.Center - Center;
            float distToTarget = toTarget.Length();
            toTarget /= distToTarget;
            ComputeNewDirection(toTarget);

            if (currentState == ChaseState.active)
            {
                updateActive(hero);
            }
            else if (currentState == ChaseState.inactive)
            {
                updateInactive(hero);
            }
        }

        private void updateActive(Hero hero)
        {
            Visible = true;
            if(this.Collided(hero))
            {
                hero.caught();
                XNACS1Base.PlayACue("hit");
                inactive();
                return;
            }

            lifecount++;
            if (lifecount == lifespan)
                inactive();
            Center += VelocityDirection;

            if (outOfBounds())
            {
                inactive();
            }
        }

        private void updateInactive(Hero hero)
        {
            Visible = false;
        }

        private void inactive()
        {
            XNACS1Base.PlayBackgroundAudio("background", 1f);
            currentState = ChaseState.inactive;
            Visible = false;
            Center = new Vector2(0, 0);
            chaserLoaded++;
        }

        public void activate()
        {
            Center = new Vector2(0, 0);
            currentState = ChaseState.active;
            XNACS1Base.PlayBackgroundAudio("chase", 1f);
            lifecount = 0;
        }

        private bool outOfBounds()
        {
            if ((CenterX > XNACS1Base.World.WorldMax.X) || (CenterX < XNACS1Base.World.WorldMin.X))
            { 
                XNACS1Base.PlayACue("avoided");
                return true;
            }
            if ((CenterY > XNACS1Base.World.WorldMax.Y) || (CenterX < XNACS1Base.World.WorldMin.Y))
            {
                XNACS1Base.PlayACue("avoided");
                return true;
            }
            return false;
        }

        private void ComputeNewDirection(Vector2 toTarget)
        {
            // figure out if we should continue to adjust our direciton ...
            double cosTheta = Vector2.Dot(toTarget, FrontDirection);
            float theta = MathHelper.ToDegrees((float)Math.Acos(cosTheta));
            if (theta > 0.001f)
            {
                Vector3 frontDir3 = new Vector3(FrontDirection, 0f);
                Vector3 toTarget3 = new Vector3(toTarget, 0f);
                Vector3 zDir = Vector3.Cross(frontDir3, toTarget3);
                RotateAngle += Math.Sign(zDir.Z) * 0.05f * theta; // rotate 5% at a time towards final direction
                VelocityDirection = FrontDirection;
            }
        }

        public bool isActive()
        {
            return currentState == ChaseState.active;
        }

        public int getChaserLoaded()
        {
            return chaserLoaded;
        }
    }
}
