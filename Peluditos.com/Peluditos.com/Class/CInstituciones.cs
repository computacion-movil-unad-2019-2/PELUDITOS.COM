using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using Peluditos.com.Models;

namespace Peluditos.com.Class
{
    public class CInstituciones : IInstituciones
    {
        peluditosEntities db;

        public CInstituciones()
        {
            db = new peluditosEntities();
        }

        public bool crear(instituciones obj)
        {
            try
            {
                db.instituciones.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }

        public IList<instituciones> listAll()
        {
            return db.instituciones.ToList();
        }

        public instituciones getId(int id)
        {
            return db.instituciones.Single(x => x.id == id);
        }

        public bool actualizar(instituciones obj)
        {
            try
            {
                db.instituciones.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
    }

    public interface IInstituciones
    {
        bool crear(instituciones obj);
        IList<instituciones> listAll();
        instituciones getId(int id);
        bool actualizar(instituciones obj);
    }
}