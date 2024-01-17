using System;
using System.Linq;

namespace func_rocket
{
    public class ForcesTask
    {
        public static RocketForce GetThrustForce(double forceValue) => r => new Vector(Math.Cos(r.Direction), Math.Sin(r.Direction)) * forceValue;

        public static RocketForce ConvertGravityToForce(Gravity gravity, Vector spaceSize) => r => gravity(spaceSize, r.Location);

        public static RocketForce Sum(params RocketForce[] forces) => r => {
            Vector totalForce = Vector.Zero;
            foreach (var force in forces)
                totalForce += force(r);
            return totalForce;
        };
    }
}
