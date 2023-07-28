
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using mpp_proiect_csharp_DeepSmeag.networking.objectprotocol;
using mpp_proiect_csharp_DeepSmeag.uiForms;
using System.Configuration;
using Grpc.Net.Client;
using Rpcprotocol;
using mpp_proiect_csharp_DeepSmeag.models;

namespace mpp_proiect_csharp_DeepSmeag
{
    internal class Program
    {
        static HttpClient client = new HttpClient();
        //[STAThread]
        static void Main()
        {
            //log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
            //log.Info("Application started");
            ////read from appconfig
            //string serverIP = ConfigurationManager.AppSettings["ip"];

            //int serverPort = int.Parse(ConfigurationManager.AppSettings["port"]);

            ////IContestsServices server = new ContestsServicesObjectProxy(serverIP, serverPort);

            //using var channel = GrpcChannel.ForAddress("http://localhost:50051");
            //RpcWorkerService.RpcWorkerServiceClient server = new RpcWorkerService.RpcWorkerServiceClient(channel);

            //Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);
            //LoginMenu loginMenu = new LoginMenu();
            //loginMenu.setServer(server);
            //Application.Run(loginMenu);

            //RunAsync().Wait();
            Console.WriteLine("FINISHED");
        }
        static async Task RunAsync()
        {
            client.BaseAddress = new Uri("http://localhost:8080/proba/");
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));

            string text = await GetTextAsync("http://localhost:8080/proba/hello");
            Console.WriteLine(text);

            //getProba
            int id = 14;
            Proba res = await GetProbaAsync("http://localhost:8080/proba/get/" + id.ToString());
            Console.WriteLine(res);
        }
        static async Task<String> GetTextAsync(string path)
        {
            string res = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if(response.IsSuccessStatusCode)
            {
                res = await response.Content.ReadAsStringAsync();
            }
            Console.WriteLine("Result text: {0}", res);
            return res;

        }
        static async Task<Proba> GetProbaAsync(string path)
        {
            Proba proba = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if(response.IsSuccessStatusCode)
            {
                 proba = await response.Content.ReadAsAsync<Proba>();
            }
            return proba;
        }
    }
}