using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.repo.interfaces
{
    internal interface IRepoInterface<EntityType, EntityId> where EntityType : class
    {
        List<EntityType>? getAll();
        EntityType? getOne(EntityId entityId);

        Boolean saveEntity(EntityType entityType);
        Boolean deleteEntity(EntityId entityId);

        Boolean updateEntity(EntityType entityType);    }
}
