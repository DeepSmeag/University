using mpp_proiect_csharp_DeepSmeag.networking.objectprotocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using mpp_proiect_csharp_DeepSmeag.models;
using networking.dto;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    public class ContestsClientObjectWorker : IContestsObserver //, Runnable
    {
        private IContestsServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        public ContestsClientObjectWorker(IContestsServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {

                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    Console.WriteLine("Reading request...");
                    Request request = (Request)formatter.Deserialize(stream);
                    Console.WriteLine(request.ToString());
                    Console.WriteLine("GOT HERE");
                    Console.WriteLine("Request read: " + request.ToString());
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                        Console.WriteLine("Sent response: " + response.ToString());
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            Console.WriteLine("Handling request...");
            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;
                PersoanaOficiuDTO udto = logReq.getUser();
                PersoanaOficiu user = udto.getPersoanaOficiu();
                Console.WriteLine(user);
                try
                {
                    lock (server)
                    {
                        server.login(user, this);
                    }
                    Console.WriteLine("Login went well");
                    return new OkResponse();
                }
                catch (ContestsException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                try
                {
                    lock (server)
                    {

                        server.logout(logReq.getUser(), this);
                    }
                    connected = false;
                    return new OkResponse();

                }
                catch (ContestsException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is GetContestsRequest)
            {
                GetContestsRequest getContestsRequest = (GetContestsRequest)request;
                try
                {
                    return new GetContestsResponse(server.getContests());
                }
                catch (ContestsException e)
                {
                    return new ErrorResponse(e.getMessage());
                }
            }
            if (request is GetParticipantsRequest)
            {

                GetParticipantsRequest getParticipantsRequest = (GetParticipantsRequest)request;

                try
                {

                    return new GetParticipantsResponse(server.getParticipants());
                }
                catch (ContestsException e)
                {
                    return new ErrorResponse(e.getMessage());
                }
            }
            if (request is GetParticipantsByProbaRequest)
            {

                GetParticipantsByProbaRequest getParticipantsByProbaRequest = (GetParticipantsByProbaRequest)request;
                try
                {
                    return new GetParticipantsResponse(server.getParticipants(getParticipantsByProbaRequest.getIdProba()));
                }
                catch (ContestsException e)
                {
                    return new ErrorResponse(e.getMessage());
                }
            }
            if (request is RegisterParticipantRequest)
            {
                try
                {
                    RegisterParticipantRequest registerParticipantRequest = (RegisterParticipantRequest)request;
                    server.attemptRegisterParticipant(registerParticipantRequest.getName(), registerParticipantRequest.getAge(), registerParticipantRequest.getProba());
                    return new OkResponse();
                }
                catch (ContestsException e)
                {
                    return new ErrorResponse(e.getMessage());
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                formatter.Serialize(stream, response);
                stream.Flush();
            }

        }
        public void updateContestsModel()
        {
            Response updateContestsResponse = new UpdateContestsResponse();
            try
            {
                sendResponse(updateContestsResponse);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        public void updateParticipantsModel()
        {
            Response updateParticipantsResponse = new UpdateParticipantsResponse();
            try
            {
                sendResponse(updateParticipantsResponse);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
    }

}
