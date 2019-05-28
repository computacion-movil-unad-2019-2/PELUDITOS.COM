using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using Peluditos.com.Models;

namespace Peluditos.com.Class
{
    public class CMascota : IMascota
    {
        peluditosEntities db;

        public CMascota()
        {
            db = new peluditosEntities();
        }

        public bool crear(mascota obj)
        {
            try
            {
                db.mascota.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }

        public IList<mascota> listAll()
        {
            return db.mascota.ToList();
        }

        public mascota getId(int id)
        {
            return db.mascota.Single(x => x.id == id);
        }

        public bool actualizar(mascota obj)
        {
            try
            {
                db.mascota.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
    }

    public interface IMascota
    {
        bool crear(mascota obj);
        IList<mascota> listAll();
        mascota getId(int id);
        bool actualizar(mascota obj);

    }
}