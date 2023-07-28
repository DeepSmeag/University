
//using mpp_proiect_csharp_DeepSmeag.services.interfaces;
//using mpp_proiect_csharp_DeepSmeag.networking.objectprotocol;
//using mpp_proiect_csharp_DeepSmeag.uiForms;
using System.Configuration;
using System.Linq.Expressions;
using System.Text;
using System.Text.Json.Serialization;
//using Grpc.Net.Client;
//using Rpcprotocol;
using mpp_proiect_csharp_DeepSmeag.models;
using Newtonsoft.Json;

namespace mpp_proiect_csharp_DeepSmeag
{
    internal class Program
    {
        static HttpClient client = new HttpClient(new LoggingHandler(new HttpClientHandler()));
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
            try
            {
                RunAsync().Wait();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }

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
            Proba res = await GetProbaAsync("http://localhost:8080/proba/"+id.ToString());
            Console.WriteLine(res);

            //postProba
            Proba proba = await PostProbaAsync("http://localhost:8080/proba");
            Console.WriteLine(proba.ToString());

        }
        static async Task<String> GetTextAsync(string path)
        {
            string res = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
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
            if (response.IsSuccessStatusCode)
            {
                //proba = await response.Content.ReadAsAsync<Proba>();
                string res = await response.Content.ReadAsStringAsync();
                Console.WriteLine($"{res}");
                //Console.WriteLine("Result proba: {0}", proba);
            }
            else
            {
                Console.WriteLine($"Error: {response.StatusCode}");
            }
            return proba;
        }
//function to add entity using REST API
        static async Task<Proba> PostProbaAsync(string path)
        {
            NumeProba numeProba = new NumeProba(4, "REST");
            CategorieVarsta categorieVarsta = new CategorieVarsta(1, 6, 8);
            Proba proba = new Proba(0,numeProba,categorieVarsta);
            string jsonString = JsonConvert.SerializeObject(proba);
            HttpContent httpContent = new StringContent(jsonString, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PostAsync(path, httpContent);

            //HttpResponseMessage response = await client.PostAsJsonAsync(path, proba);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();

            }
            else
            {
                Console.WriteLine($"Error: {response.StatusCode} Details: {await response.Content.ReadAsStringAsync()}");
            }
            return proba;

        }
    }
    public class LoggingHandler : DelegatingHandler
    {
        public LoggingHandler(HttpMessageHandler innerHandler)
            : base(innerHandler)
        {
        }

        protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            Console.WriteLine("Request:");
            Console.WriteLine(request.ToString());
            if (request.Content != null)
            {
                Console.WriteLine(await request.Content.ReadAsStringAsync());
            }
            Console.WriteLine();

            HttpResponseMessage response = await base.SendAsync(request, cancellationToken);

            Console.WriteLine("Response:");
            Console.WriteLine(response.ToString());
            if (response.Content != null)
            {
                Console.WriteLine(await response.Content.ReadAsStringAsync());
            }
            Console.WriteLine();

            return response;
        }
    }
}