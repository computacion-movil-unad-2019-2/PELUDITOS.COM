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
    public class AdoptanteController : ApiController
    {
        IAdoptante adopt = new CAdoptante();
        // GET: api/Adoptante
        [HttpGet]
        public IEnumerable<adoptante> Get()
        {
            return adopt.listAll();
        }

        // GET: api/Adoptante/5
        [HttpGet]
        public adoptante Get(int id)
        {
            return adopt.getId(id);
        }

        // POST: api/Adoptante
        [HttpPost]
        public HttpResponseMessage Post([FromBody]adoptante obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                op = adopt.crear(obj);
                var result = new { estado= op, mensaje = op? "Creado" : "No Creado"};
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

        // PUT: api/Adoptante/5
        public HttpResponseMessage Put(int id, [FromBody]adoptante obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                obj.id = id;
                op = adopt.actualizar(obj);
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
            catch(Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message);
            }
            
        }

        // DELETE: api/Adoptante/5
        public void Delete(int id)
        {
        }
    }
}
