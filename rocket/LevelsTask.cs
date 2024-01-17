using System;
using System.Collections.Generic;

namespace func_rocket
{
    public class LevelsTask
    {
        static readonly Physics standardPhysics = new Physics();

        public static IEnumerable<Level> CreateLevels()
        {
            yield return new Level("Zero", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(600, 200), 
                (size, v) => Vector.Zero, standardPhysics);
            
            yield return new Level("Heavy", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(600, 200), 
                (size, v) => new Vector(0, 0.9), standardPhysics);
            
            yield return new Level("Up", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(700, 500), 
                (size, v) => new Vector(0, -300 / (size.Y - v.Y + 300)), standardPhysics);
            
            yield return new Level("WhiteHole", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(600, 200), 
                (size, v) => 140 * (v - new Vector(600, 200)) / (Math.Pow((v - new Vector(600, 200)).Length, 2) + 1), standardPhysics);
            
            var blackHolePosition = new Vector(400, 350);
            yield return new Level("BlackHole", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(600, 200), 
                (size, v) => 300 * (blackHolePosition - v) / (Math.Pow((blackHolePosition - v).Length, 2) + 1), standardPhysics);
            
            yield return new Level("BlackAndWhite", 
                new Rocket(new Vector(200, 500), Vector.Zero, -0.5 * Math.PI),
                new Vector(600, 200), 
                (size, v) => 0.5 * (((140 * (v - new Vector(600, 200))) / (Math.Pow((v - new Vector(600, 200)).Length, 2) + 1)) 
                    + (300 * (blackHolePosition - v)) / (Math.Pow((blackHolePosition - v).Length, 2) + 1)), standardPhysics);
        }
    }
}
