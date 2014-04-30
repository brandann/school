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
    class Hero : XNACS1Rectangle
    {

        private enum HeroState
        {
            ignored,
            noticed
        }

        private HeroState currentState = HeroState.ignored;
        private int caught_count = 0;

        public Hero()
        {
            Size = new Vector2(6f, 6);
            Center = new Vector2(10, 10);
            Texture = "hero";
            RotateAngle += 180;
        }

        // UpdateWorld() function
        public void Update(Vector2 delta)
        {
            if (delta.Length() != 0)
            {
                //delta.X /= 2; delta.Y /= 2;
                Vector2 toTarget = Center - (Center + delta);
                float distToTarget = toTarget.Length();
                toTarget /= distToTarget;
                ComputeNewDirection(toTarget);
                Center += delta;
            }
            
            XNACS1Base.World.ClampAtWorldBound(this);
        }

        public void Update(Wall block)
        {
                Vector2 bc = block.Center;

                if (Collided(block))
                {
                    //if (block.RightOf(this))
                    //{
                    //    move -X
                    //    CenterX = block.CenterX - ((this.Width / 2) + block.Width / 2);
                    //}
                    //else if (block.LeftOf(this))
                    //{
                    //    move +x
                    //    CenterX = block.CenterX + ((this.Width / 2) + block.Width / 2);

                    //}
                    //else if (block.Above(this))
                    //{
                    //    move -y
                    //    CenterY = block.CenterY - ((this.Width / 2) + block.Height / 2);

                    //}
                    //else if (block.Below(this))
                    //{
                    //    move +y
                    //    CenterY = block.CenterY + ((this.Width / 2) + block.Height / 2);
                    //}

                    //Vector2 distanceVectorFromCenter = this.Center - block.Center;
                    //float distanceFromCenters = distanceVectorFromCenter.Length();
                    //float greatDistance = this.Width + (block.Width / 2);
                    //float errorDistance = greatDistance - distanceFromCenters;

                    //if (distanceFromCenters < greatDistance) //move in X direction
                    //{
                    //    CenterX = block.CenterX + greatDistance;
                    //}
                    //else //move in X direction
                    //{
                    //    float Radius = this.Width / 2f;
                    //    float HW = block.Width / 2f;
                    //    float bestdist = Radius + HW;
                    //    float claculateddist = HW - this.CenterX;
                    //    this.CenterX += claculateddist;
                    //}

                    Vector2 T = block.FrontDirection;   // this is the Tangent vector
                    Vector2 V = Center - block.Center;  // from block to current Center
                    float d_T = Vector2.Dot(V, T);      // V in T direction (a signed-value)
                    Vector2 pt_T = block.Center + (d_T * T); // Point on Tangent vector
                    Vector2 N = Center - pt_T;      // perpendicular vector to the block
                    N.Normalize();
                    float d_N = Vector2.Dot(V, N); // V in N direction (a signed-value)

                    if (Math.Abs(d_T) < (block.Width / 2f))  // solve for top/bottom overlap
                    {
                        float dOnN = (Width / 2f) + (block.Height / 2f);
                        if (Math.Abs(d_N) < dOnN)
                            Center = pt_T + Math.Sign(d_N) * (dOnN * N);
                    }
                    else
                    {
                        float distanceY = this.CenterY - block.CenterY;
                        if (Math.Abs(distanceY) < block.Height / 2)
                        {
                            float R = this.Width/2;
                            float HW = block.Width/2;
                            float BD = R + HW;
                            float CD = CenterX - block.CenterX;
                            if(CenterX > block.CenterX)
                                CenterX = block.CenterX + BD;
                            if (CenterX < block.CenterX)
                                CenterX = block.CenterX - BD;
                        }
                    }

                }
        }

        public void setNoticed()
        {
            currentState = HeroState.noticed;
            Color = Color.Black;
        }

        public void caught()
        {
            caught_count++;
            currentState = HeroState.ignored;
            Color = Color.Aquamarine;
        }

        public int getCaught()
        {
            return caught_count;
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
                RotateAngle += Math.Sign(zDir.Z) * .5f * theta; // rotate 5% at a time towards final direction
                VelocityDirection = FrontDirection;
            }
        }

    }
}
