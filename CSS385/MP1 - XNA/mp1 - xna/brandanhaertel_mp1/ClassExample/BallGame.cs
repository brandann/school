
#region Reference to system libraries
    using System;
    using System.Collections.Generic;
    using Microsoft.Xna.Framework;
    using Microsoft.Xna.Framework.Audio;
    using Microsoft.Xna.Framework.Content;
    using Microsoft.Xna.Framework.GamerServices;
    using Microsoft.Xna.Framework.Graphics;
    using Microsoft.Xna.Framework.Input;
    using Microsoft.Xna.Framework.Net;
    using Microsoft.Xna.Framework.Storage;
#endregion

// Label A: Reference to XNACS1Lib
using XNACS1Lib;

namespace BrandanHaertel_NameSpace
{
    /// Label B: Subclass from XNACS1Base
    public class BallGame : XNACS1Base
    {
        XNACS1Circle cir;
        XNACS1Rectangle bar;
        XNACS1Rectangle obs;
        Boolean play = false;
        int hit = 0; 
 

        // Label C: InitializeWorld() function
        protected override void  InitializeWorld()
        {
            //Set the coordinate system to work with
            World.SetWorldCoordinate(new Vector2(0, 0), 100);
                    
            //Set the background
            World.SetBackgroundColor(Color.Black);
            World.SetBackgroundTexture("background");

            //start background music
            PlayBackgroundAudio("a_background", 0.1f);

            //Set Echo Color
            SetTopEchoColor(Color.White);
            SetBottomEchoColor(Color.White);

            //Create bar
            Vector2 pos = new Vector2();
            pos.X = World.WorldMax.X / 2.0f;
            pos.Y = 3;
            XNACS1Rectangle bottomBar = new XNACS1Rectangle(pos, 10, 2, "bar");
            bar = bottomBar;

            //moving bar - temp
            pos.Y = 35;
            obs = new XNACS1Rectangle(pos, 10, .25f, "bar");
            obs.VelocityX = 1;
            obs.VelocityY = 0;
        }
       
        // Label D: UpdateWorld() function
        protected override void UpdateWorld()
        {
            //end game
            if (GamePad.ButtonBackClicked())
                this.Exit();

            //start game with new ball
            if (!play && GamePad.ButtonAClicked())
            {
                //create and set ball data
                Vector2 pos = (World.WorldMax + World.WorldMin) / 2.0f;
                cir = new XNACS1Circle(pos, 2f, "circle");
                cir.VelocityY = RandomFloat(.5f, 2f);
                cir.VelocityX = RandomFloat(.5f, 2f);

                bar.Speed = 2;

                //game status varibles
                play = true;
                hit = 0;
            }

            //ball is active
            if (play)
            {
                //bounce on paddle
                if (cir.Collided(bar) && cir.CenterY - 1 > bar.CenterY + 1){
                        cir.VelocityY = cir.VelocityY * -1;
                        PlayACue("bar");
                        hit++;
                }
                else if (cir.Collided(obs)){
                    //if (cir.Above(obs) || cir.Below(obs))
                    //{
                        //if (cir.CenterY > obs.CenterY || cir.CenterY < cir.CenterY)
                        //{
                            cir.VelocityY = cir.VelocityY * -1;
                            PlayACue("wall");
                        //}
                    //}
                }

                //hit wall
                BoundCollideStatus collideStatus = World.CollideWorldBound(cir); 
                switch (collideStatus) { 
                    case BoundCollideStatus.CollideTop:
                        cir.VelocityY = cir.VelocityY * -1;
                        PlayACue("wall");
                        EchoToTopStatus("Ball: Collide TOP");
                        break;
                    case BoundCollideStatus.CollideLeft:
                        cir.VelocityX = cir.VelocityX * -1;
                        PlayACue("wall");
                        EchoToTopStatus("Ball: Collide LEFT");
                        break ; 
                    case BoundCollideStatus.CollideRight:
                        cir.VelocityX = cir.VelocityX * -1;
                        PlayACue("wall");
                        EchoToTopStatus("Ball: Collide RIGHT");
                        break ; 
                    case BoundCollideStatus.CollideBottom:
                        PlayACue("die");
                        cir.RemoveFromAutoDrawSet();
                        play = false;
                        break ;
                }

                

                //move ball
                cir.CenterX += cir.VelocityX;
                cir.CenterY += cir.VelocityY;

                
            }
            else{
                //show no ball message
                EchoToTopStatus("No ball in the world!");
            }
            
            //move and keep bar in window
            bar.CenterX += (GamePad.ThumbSticks.Right.X * bar.Speed);
            World.ClampAtWorldBound(bar);

            BoundCollideStatus collideStatus2 = World.CollideWorldBound(obs); 
            switch (collideStatus2)
            {
                case BoundCollideStatus.CollideLeft:
                    obs.VelocityX = obs.VelocityX * -1;
                    break;
                case BoundCollideStatus.CollideRight:
                    obs.VelocityX = obs.VelocityX * -1;
                    break;
            }

            //move obs bar
            obs.CenterX += obs.VelocityX;
            obs.CenterY += obs.VelocityY;

            //display score
            EchoToBottomStatus("" + hit);
        }
        
    }
}
