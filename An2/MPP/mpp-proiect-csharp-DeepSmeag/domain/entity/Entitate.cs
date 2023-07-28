using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain.entity
{
    internal class Entitate<IdType>
    {
        public IdType Id { get; set; }

        public Entitate(IdType id)
        {
            this.Id = id;
        }
        
    }
}
