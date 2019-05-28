using Peluditos.com.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Peluditos.com.Class
{
    public class CAdoptante : IAdoptante
    {
        peluditosEntities db;

        public CAdoptante()
        {
            db = new peluditosEntities();
        }

        public bool crear(adoptante obj)
        {
            try
            {
                db.adoptante.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }

        public IList<adoptante> listAll()
        {
            return db.adoptante.ToList();
        }

        public adoptante getId(int id)
        {
            return db.adoptante.Single(x => x.id == id);
        }

        public bool actualizar(adoptante obj)
        {
            try
            {
                db.adoptante.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
    }

    public interface IAdoptante
    {
        bool crear(adoptante obj);
        IList<adoptante> listAll();
        adoptante getId(int id);
        bool actualizar(adoptante obj);
    }
}