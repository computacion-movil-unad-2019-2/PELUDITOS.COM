using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Peluditos.com.Models;

namespace Peluditos.com.Controllers
{
    public class PersonasController : ApiController
    {
        List<Persona> lstPersonas = new List<Persona>();

        public PersonasController()
        {
            Persona obj = new Persona { id = 1, nombre = "Hector granada", email = "hfgranada@gmail.com" };
            lstPersonas.Add(obj);

            obj = new Persona { id = 2, nombre = "Leidy Johana Henao", email = "lady@gmail.com" };
            lstPersonas.Add(obj);

            obj = new Persona { id = 3, nombre = "Juan granada", email = "juan@gmail.com" };
            lstPersonas.Add(obj);

            obj = new Persona { id = 4, nombre = "Alejo granada", email = "alejo@gmail.com" };
            lstPersonas.Add(obj);
        }

        // GET api/<controller>
        public List<Persona> GetAll()
        {
            return this.lstPersonas;
        }

        // GET api/<controller>/1
        public Persona GetId(int id)
        {
            return this.lstPersonas.Find(x=> x.id == id);
        }
    }
}
