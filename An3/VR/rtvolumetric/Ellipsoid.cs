using System;


namespace rt
{
    public class Ellipsoid : Geometry
    {
        private Vector Center { get; }
        private Vector SemiAxesLength { get; }
        private double Radius { get; }


        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Material material, Color color) : base(material, color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Color color) : base(color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            Radius = radius;
        }

        public override Intersection GetIntersection(Line line, double minDist, double maxDist)
        {
            // TODO: ADD CODE HERE
            double asq = SemiAxesLength.X * SemiAxesLength.X;
            double bsq = SemiAxesLength.Y * SemiAxesLength.Y;
            double csq = SemiAxesLength.Z * SemiAxesLength.Z;
            double A = line.Dx.X * line.Dx.X / asq + line.Dx.Y * line.Dx.Y / bsq + line.Dx.Z * line.Dx.Z / csq;
            double B = 2 * (line.Dx.X * (line.X0.X - Center.X) / asq + line.Dx.Y * (line.X0.Y - Center.Y) / bsq + line.Dx.Z * (line.X0.Z - Center.Z) / csq);

            double x0xc = (line.X0.X - Center.X) * (line.X0.X - Center.X) / asq;
            double y0yc = (line.X0.Y - Center.Y) * (line.X0.Y - Center.Y) / bsq;
            double z0zc = (line.X0.Z - Center.Z) * (line.X0.Z - Center.Z) / csq;

            double C = x0xc + y0yc + z0zc - Radius * Radius;

            double Delta = B * B - 4 * A * C;

            double t1, t2;

            if (Delta < 0)
            {
                return new Intersection();
            }
            else if (Delta == 0.0d)
            {
                double t = -B / (2 * A);

                Vector intersection = line.CoordinateToPosition(t);

                bool visible = true;

                double len = (intersection - line.X0).Length();

                if (len < minDist || len > maxDist || t < 0)
                    visible = false;

                //var n = (line.CoordinateToPosition(t) - Center).Normalize();
                Vector n = new Vector(
            2 * (intersection.X - Center.X) / asq,
            2 * (intersection.Y - Center.Y) / bsq,
            2 * (intersection.Z - Center.Z) / csq
        ).Normalize();

                return new Intersection(true, visible, this, line, t, n, this.Material, this.Color);


            }
            else
            {
                t1 = (-B + Math.Sqrt(Delta)) / (2 * A);
                t2 = (-B - Math.Sqrt(Delta)) / (2 * A);
                bool visible = true;
                if (t1 < t2)
                {

                    Vector intersection = line.CoordinateToPosition(t1);

                    double len = (intersection - line.X0).Length();
                    if (len < minDist || len > maxDist || t1 < 0)
                        visible = false;
                    //var n = (line.CoordinateToPosition(t1) - Center).Normalize();
                    Vector n = new Vector(
            2 * (intersection.X - Center.X) / asq,
            2 * (intersection.Y - Center.Y) / bsq,
            2 * (intersection.Z - Center.Z) / csq
        ).Normalize();
                    return new Intersection(true, visible, this, line, t1, n, this.Material, this.Color);

                }
                else
                {
                    Vector intersection = line.CoordinateToPosition(t2);
                    double len = (intersection - line.X0).Length();
                    if (len < minDist || len > maxDist || t2 < 0)
                        visible = false;
                    //var n = (line.CoordinateToPosition(t2) - Center).Normalize();
                    Vector n = new Vector(
            2 * (intersection.X - Center.X) / asq,
            2 * (intersection.Y - Center.Y) / bsq,
            2 * (intersection.Z - Center.Z) / csq
        ).Normalize();
                    return new Intersection(true, visible, this, line, t2, n, this.Material, this.Color);

                }
            }
        }
    }
}
