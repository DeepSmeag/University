using System;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            return -n * viewPlaneSize / imgSize + viewPlaneSize / 2;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = Intersection.NONE;

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            // ADD CODE HERE: Detect whether the given point has a clear line of sight to the given light
            // creating a ray from the intersection point to the light source
            Line ray = new Line(point, light.Position);
            // max distance to check, it makes sense that we wouldn't go further than the 2 points are placed
            var maxDist = (light.Position - point).Length() + 1.0d;
            // to calculate intersection we turn to the same function, math-heavy
            Intersection intersect = FindFirstIntersection(ray, 0.0001d, maxDist);
            return !(intersect.Valid);
        }

        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color(0.2, 0.2, 0.2, 1.0);

            var viewParallel = (camera.Up ^ camera.Direction).Normalize();

            var image = new Image(width, height);

            var cameraPosition = camera.Position;
            var vecW = camera.Direction * camera.ViewPlaneDistance;

            for (var i = 0; i < width; i++)
            {
                for (var j = 0; j < height; j++)
                {
                    // ADD CODE HERE: Implement pixel color calculation
                    //4 components
                    //1. positioning the camera, it's the point from which our vectors start
                    //2. camera Direction, so in which direction (3D vector) the center of the camera is looking
                    //3. a parallel vector pointing to the side of the camera, like scanning pixels from left to right
                    //4. a parallel vector pointing up from the camera, like scanning pixels from top to bottom
                    //summing all of these together gives us the direction of the ray for pixel[i,j]

                    Vector rayDirection = cameraPosition + vecW + viewParallel * ImageToViewPlane(i, width, camera.ViewPlaneWidth) + camera.Up * ImageToViewPlane(j, height, camera.ViewPlaneHeight);
                    Line ray = new Line(cameraPosition, rayDirection);
                    //now we want to see if this ray we send from the camera intersects anything, so we call this function; it has the ray and how far we test the ray for intersections
                    Intersection intersect = FindFirstIntersection(ray, camera.FrontPlaneDistance, camera.BackPlaneDistance);
                    // we still test for the intersection in case there was none
                    if (intersect.Valid && intersect.Visible)
                    {
                        Color color = new Color();
                        // now we know there is an object that the camera theoretically sees, so now we need to calculate the proper lighting based on all the lights in the scene
                        foreach (var light in lights)
                        {
                            // first of all, ambient lighting lights everything, we need to multiply by the sphere's material property for ambient (maybe it's a sphere that absorbs ambient light and doesn't reflect it, so it will be darker)
                            color += intersect.Material.Ambient * light.Ambient;
                            // checking to see if the object is lit (if there is a clear path from that intersect point to the light source we're currently at)
                            // since we're not doing any kind of ray bouncing, this is all we do for "isLit"
                            if (IsLit(intersect.Position, light))
                            {

                                //Console.WriteLine("Not a RawCtMask, but a "+ intersect.Geometry.GetType().ToString());
                                // now we know there is a direct path to the lighting source, so we calculate the vectors we need to calculate the proper lighting
                                // N = normal to the surface at intersection point
                                //Vector N = intersect.Geometry.Normal(intersect.Position);
                                Vector N = intersect.Normal;
                                // T = vector from intersection to light (direction of direct lighting)
                                Vector T = (light.Position - intersect.Position).Normalize();
                                // E = vector from intersection to camera (direction of seeing the object)
                                Vector E = (camera.Position - intersect.Position).Normalize();
                                // R = reflection (the same angle of T with regards to N)
                                Vector R = (N * (N * T) * 2 - T).Normalize();
                                // if angle between N and T is <90degrees, we take
                                if (N * T > 0.0d)
                                {
                                    color += intersect.Material.Diffuse * light.Diffuse * (N * T);
                                }
                                // if angle between what camera sees and reflection vector is <90 degres, it means we're seeing a highlight on the object
                                // so that's specular light; formula is from classes, so not even trying to go into it
                                if (E * R > 0.0d)
                                {
                                    color += intersect.Material.Specular * light.Specular * Math.Pow(E * R, intersect.Material.Shininess);
                                }
                                //color *= light.Intensity;


                            }
                            // if there is no direct path to the light source, we don't bother with anything else
                            // my guess is that here we would have an "else" for ray bouncing
                        }
                        image.SetPixel(i, j, color);
                    }
                    else
                    {
                        image.SetPixel(i, j, background);
                    }
                }
            }

            image.Store(filename);
        }
    }
}