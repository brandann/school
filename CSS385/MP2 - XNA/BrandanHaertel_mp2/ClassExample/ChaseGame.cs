
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
    public class ChaseGame : XNACS1Base
    {
        private Wall[] static_wall;
        private Wall[] moving_wall;
        private Hero hero;
        private Chaser chaser;
        private Guard guard;

        // Label C: InitializeWorld() function
        protected override void  InitializeWorld()
        {
            //Set the coordinate system to work with
            World.SetWorldCoordinate(new Vector2(0, 0), 100);
                    
            //Set the background
            World.SetBackgroundColor(Color.CornflowerBlue);
            World.SetBackgroundTexture("background");
            
            LoadStaticWalls();
            LoadGaurd();
            LoadHero();
            LoadChaser();
            LoadMovingWalls();

            EchoToTopStatus("You are jerry the turtle, you are afraid of shadows!");
            SetTopEchoColor(Color.White);

            PlayBackgroundAudio("background", 1f);
        }

        // Label D: UpdateWorld() function
        protected override void UpdateWorld()
        {
            //end game
            if (GamePad.ButtonBackClicked())
                this.Exit();

            hero.Update(GamePad.ThumbSticks.Left);
            hero.Update(GamePad.ThumbSticks.Right);
            guard.Update(hero);            
            chaser.Update(hero);

            if (hero.Collided(guard))
            {
                if (!chaser.isActive())
                    chaser.activate();
            }

            if (!chaser.isActive())
            {
                for (int i = 0; i < moving_wall.Length; i++)
                    moving_wall[i].Update(hero);
            }
            else //chaser is active
            {
                for (int i = 0; i < moving_wall.Length; i++)
                    moving_wall[i].Texture = "wall_paused";
            }

            for (int i = 0; i < moving_wall.Length; i++)
                hero.Update(moving_wall[i]);
            for (int i = 0; i < static_wall.Length; i++)
                hero.Update(static_wall[i]);

            EchoToBottomStatus("Caught Counter: " + hero.getCaught() + "  Hero Avoided: " + (chaser.getChaserLoaded() - hero.getCaught()));
        }

        protected void LoadHero()
        {
            hero = new Hero();
        }

        protected void LoadChaser()
        {
            chaser = new Chaser();
        }

        protected void LoadGaurd()
        {
            guard = new Guard();
        }

        protected void LoadMovingWalls()
        {
            int numofwalls = 5;
            moving_wall = new Wall[numofwalls];

            Vector2 pos = new Vector2(20, World.WorldMax.Y / 2);
            for (int i = 0; i < moving_wall.Length; i++)
            {
                moving_wall[i] = new Wall();
                //moving_wall[i].RotateAngle = 90;
                moving_wall[i].setPos(pos);
                moving_wall[i].makeMoving();
                pos.X += (float)80 / numofwalls;
            }
        }

        protected void LoadStaticWalls()
        {
            static_wall = new Wall[2];

            float world_half = World.WorldMax.Y / 2;
            float world_quarter = world_half / 2;
            Vector2 pos = new Vector2(50, world_quarter - 5);
            for (int i = 0; i < static_wall.Length; i++)
            {
                static_wall[i] = new Wall();
                static_wall[i].Width = 70; static_wall[i].Height = 2;
                static_wall[i].Texture = "static";
                //static_wall[i].Center = pos;
                //pos.Y += world_half + 10;
            }
            pos = new Vector2(World.WorldMax.X / 2, 6);
            static_wall[0].Center = pos;
            pos = new Vector2(World.WorldMax.X / 2, World.WorldMax.Y - 6);
            static_wall[1].Center = pos;
        }
    }
}
