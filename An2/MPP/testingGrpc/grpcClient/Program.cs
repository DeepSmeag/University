using Grpc.Net.Client;
using HelloWorldGrpc;

namespace grpcClient
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // To customize application configuration such as set high DPI settings or default font,
            // see https://aka.ms/applicationconfiguration.
            ApplicationConfiguration.Initialize();
            bool ok = startClient(null);
            Console.WriteLine("Press any key to exit...");
            if (ok)
            {
                Application.Run(new Form1());
            }
        }

        static bool startClient(string[] args)
        {
            // Create a channel using the local gRPC server address
            using var channel = GrpcChannel.ForAddress("http://localhost:50051");

            // Create a client for the HelloWorldService
            var client = new HelloWorldService.HelloWorldServiceClient(channel);

            // Call the SayHello method and get the response
            var response = client.SayHello(new HelloRequest { Message = "Worldyy" });

            // Print the response
            Console.WriteLine($"Received response: {response.Message}");

            GetUsersResponse users = client.GetUsers(new GetUsersRequest());
            bool ok = false;
            foreach (var user in users.Users)
            {
                if(user.Name.Equals("user12") || user.Name.Equals("user22"))
                {
                    ok= true;
                }
            }
            return ok;
        }
    }

    //internal class User
    //{
    //    public string Name { get; set; }
    //    public string Password { get; set; }

    //    public User(string Name, string Password) {
    //        this.Name = Name;
    //        this.Password = Password;
    //    }

    //}
}