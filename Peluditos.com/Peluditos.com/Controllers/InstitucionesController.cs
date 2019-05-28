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
    public class InstitucionesController : ApiController
    {
        IInstituciones inst = new CInstituciones();
        // GET: api/Instituciones
        public IEnumerable<instituciones> Get()
        {
            return inst.listAll();
        }

        // GET: api/Instituciones/5
        public instituciones Get(int id)
        {
            return inst.getId(id);
        }

        // POST: api/Instituciones
        public HttpResponseMessage Post([FromBody]instituciones obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                op = inst.crear(obj);
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

        // PUT: api/Instituciones/5
        public HttpResponseMessage Put(int id, [FromBody]instituciones obj)
        {
            try
            {
                bool op = false;
                HttpResponseMessage response;
                obj.id = id;
                op = inst.actualizar(obj);
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

        // DELETE: api/Instituciones/5
        public void Delete(int id)
        {
        }
    }
}
