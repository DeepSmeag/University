using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class RegisterParticipantRequest : Request
    {
    private String name;
    private int age;
    private InscrieriProba proba;

    public RegisterParticipantRequest(String name, int age, InscrieriProba proba)
    {
        this.name = name;
        this.age = age;
        this.proba = proba;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public InscrieriProba getProba()
    {
        return proba;
    }
}

}
