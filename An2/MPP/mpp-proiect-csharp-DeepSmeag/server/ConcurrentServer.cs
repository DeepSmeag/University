
using log4net.Config;
using mpp_proiect_csharp_DeepSmeag.networking.objectprotocol;
using mpp_proiect_csharp_DeepSmeag.persistence;
using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using mpp_proiect_csharp_DeepSmeag.services;
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.server
{

    class StartServer
    {
        static readonly string host = "127.0.0.1";
        static readonly int port = 55356;
        static void Main(string[] args)
        {

            // IUserRepository userRepo = new UserRepositoryMock();
            //  IUserRepository userRepo=new UserRepositoryDb();
            // IMessageRepository messageRepository=new MessageRepositoryDb();
            try
            {
                //FileInfo configFile = new FileInfo("log4net.config");
                //XmlConfigurator.Configure(configFile);
                
                //log4net.Config.XmlConfigurator.Configure();
                LogManager.info("Starting server ...--------");
                IPersoanaOficiuRepoInterface persOficiuRepoInterface = new PersoanaOficiuRepo();
                IParticipantRepoInterface participantRepoInterface = new ParticipantRepo();
                IProbaRepoInterface probaRepoInterface = new ProbaRepo();
                IInscriereRepoInterface inscriereRepoInterface = new InscriereRepo();
                ServiceFacade.setRepos(persOficiuRepoInterface, participantRepoInterface, probaRepoInterface, inscriereRepoInterface);
                IContestsServices serviceImpl = ServiceFacade.getInstance();
                LogManager.info("Initialized service & repo ...--------");
                // IChatServer serviceImpl = new ChatServerImpl();
                SerialContestsServer server = new SerialContestsServer(host, port, serviceImpl);
                server.Start();
                Console.WriteLine("Server started ...");
                //Console.WriteLine("Press <enter> to exit...");
                Console.ReadLine();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }


        }
    }
    public abstract class AbstractServer
    {
        private TcpListener server;
        private String host;
        private int port;
        public AbstractServer(String host, int port)
        {
            this.host = host;
            this.port = port;
        }
        public void Start()
        {
            IPAddress adr = IPAddress.Parse(host);
            IPEndPoint ep = new IPEndPoint(adr, port);
            server = new TcpListener(ep);
            server.Start();
            while (true)
            {
                Console.WriteLine("Waiting for clients ...");
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Client connected ...");
                processRequest(client);
            }
        }

        public abstract void processRequest(TcpClient client);

    }


    public abstract class ConcurrentServer : AbstractServer
    {

        public ConcurrentServer(string host, int port) : base(host, port)
        { }

        public override void processRequest(TcpClient client)
        {

            Thread t = createWorker(client);
            t.Start();

        }

        protected abstract Thread createWorker(TcpClient client);

    }


    public class SerialContestsServer : ConcurrentServer
    {
        private IContestsServices server;
        private ContestsClientObjectWorker worker;
        public SerialContestsServer(string host, int port, IContestsServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialContestsServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ContestsClientObjectWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

}
