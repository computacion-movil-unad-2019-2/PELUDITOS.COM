using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using Peluditos.com.Class;
using Peluditos.com.Models;

namespace Peluditos.com.Controllers
{
    public class MascotaController : ApiController
    {
        IMascota masc = new CMascota();
        // GET: api/Mascota
        public IEnumerable<mascota> Get()
        {
            return masc.listAll();
        }

        // GET: api/Mascota/5
        public mascota Get(int id)
        {
            return masc.getId(id);
        }

        // POST: api/Mascota
        public HttpResponseMessage Post([FromBody]mascota obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                op = masc.crear(obj);
                var result = new { estado = op, mensaje = op ? "Creado" : "No Creado" };
                if (op)
                {
                    response = Request.CreateResponse(HttpStatusCode.OK, result);
                }
                else
                {
                    response = Request.CreateResponse(HttpStatusCode.NotFound, result);
                }

                return response;
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message);
            }
        }

        // PUT: api/Mascota/5
        public HttpResponseMessage Put(int id, [FromBody]mascota obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                obj.id = id;
                op = masc.actualizar(obj);
                var result = new { estado = op, mensaje = op ? "Actualizado" : "No Actualizado" };
                if (op)
                {
                    response = Request.CreateResponse(HttpStatusCode.OK, result);
                }
                else
                {
                    response = Request.CreateResponse(HttpStatusCode.NotFound, result);
                }

                return response;

            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message);
            }

        }

        // DELETE: api/Mascota/5
        public void Delete(int id)
        {
        }
    }
}
