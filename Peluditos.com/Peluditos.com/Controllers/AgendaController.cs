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
    public class AgendaController : ApiController
    {
        IAgenda agen = new CAgenda();
        // GET: api/Agenda
        public IEnumerable<agenda> Get()
        {
            return agen.listAll();
        }

        // GET: api/Agenda/5
        public agenda Get(int id)
        {
            return agen.getId(id);
        }

        // POST: api/Agenda
        public HttpResponseMessage Post([FromBody]agenda obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                op = agen.crear(obj);
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

        // PUT: api/Agenda/5
        public HttpResponseMessage Put(int id, [FromBody]agenda obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                obj.id = id;
                op = agen.actualizar(obj);
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

        // DELETE: api/Agenda/5
        public void Delete(int id)
        {
        }
    }
}
