using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using networking.dto;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    public class ContestsServicesObjectProxy : IContestsServices
    {
        private string host;
        private int port;

        private IContestsObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public ContestsServicesObjectProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public virtual void login(PersoanaOficiu user, IContestsObserver client)
        {
            initializeConnection();
            PersoanaOficiuDTO udto = new PersoanaOficiuDTO(user);

            sendRequest(new LoginRequest(udto));
            Response response = readResponse();

            if (response is OkResponse)
            {
                this.client = client;
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new ContestsException(err.getMessage());
            }

        }


        public virtual void logout(PersoanaOficiu user, IContestsObserver client)
        {
            sendRequest(new LogoutRequest(user));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ContestsException(err.getMessage());
            }
        }

        public List<InscrieriProba> getContests()
        {
            sendRequest(new GetContestsRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ContestsException(err.getMessage());
            }
            if (response is GetContestsResponse)
            {
                GetContestsResponse resp = (GetContestsResponse)response;
                return resp.getContests();
            }
            return null;
        }
        public List<Participant> getParticipants()
        {
            sendRequest(new GetParticipantsRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ContestsException(err.getMessage());
            }
            if (response is GetParticipantsResponse)
            {
                GetParticipantsResponse resp = (GetParticipantsResponse)response;
                return resp.getParticipants();
            }
            return null;
        }
        public List<Participant> getParticipants(int idProba)
        {
            sendRequest(new GetParticipantsByProbaRequest(idProba));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ContestsException(err.getMessage());
            }
            if (response is GetParticipantsResponse)
            {
                GetParticipantsResponse resp = (GetParticipantsResponse)response;

                return resp.getParticipants();
            }
            return null;
        }
        public void attemptRegisterParticipant(String participantName, int age, InscrieriProba selectedItem)
        {
            sendRequest(new RegisterParticipantRequest(participantName, age, selectedItem));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ContestsException(err.getMessage());
            }
            // ok response doesn't need handling
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();

                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new ContestsException("Error sending object " + e);
            }

        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();

                }


            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }
        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }


        private void handleUpdate(UpdateResponse update)
        {
            if (update is UpdateContestsResponse)
            {
                UpdateContestsResponse resp = (UpdateContestsResponse)update;
                try
                {
                    client.updateContestsModel();
                }
                catch (Exception e)
                {
                    //e.printStackTrace();
                }
            }
            if (update is UpdateParticipantsResponse)
            {

                UpdateParticipantsResponse resp = (UpdateParticipantsResponse)update;
                try
                {
                    client.updateParticipantsModel();
                }
                catch (Exception e)
                {

                }
            }
        }
        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {

                        lock (responses)
                        {


                            responses.Enqueue((Response)response);

                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

    }
}
