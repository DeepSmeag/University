using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    

    internal class PersoanaOficiu : Entitate<int>
    {
        private string username { get; }        

        public string password { get; }

        public PersoanaOficiu(int id, string username, string password) : base(id)
        {
            this.username = username;
            this.password = password;
        }
    }
}
