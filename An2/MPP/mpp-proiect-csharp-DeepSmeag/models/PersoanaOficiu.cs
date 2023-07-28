using mpp_proiect_csharp_DeepSmeag.models.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.models
{

    [Serializable]
    public class PersoanaOficiu : Entitate<int>
    {
        public string username { get; }        

        public string password { get; }

        public PersoanaOficiu(int id, string username, string password) : base(id)
        {
            this.username = username;
            this.password = password;
        }
    }
}
