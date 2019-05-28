using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using Peluditos.com.Models;

namespace Peluditos.com.Class
{
    public class CAgenda : IAgenda
    {
        peluditosEntities db;

        public CAgenda()
        {
            db = new peluditosEntities();
        }

        public bool crear(agenda obj)
        {
            try
            {
                db.agenda.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }

        public IList<agenda> listAll()
        {
            return db.agenda.ToList();
        }

        public agenda getId(int id)
        {
            return db.agenda.Single(x => x.id == id);
        }

        public bool actualizar(agenda obj)
        {
            try
            {
                db.agenda.Add(obj);
                db.SaveChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
    }

    public interface IAgenda
    {
        bool crear(agenda obj);
        IList<agenda> listAll();
        agenda getId(int id);
        bool actualizar(agenda obj);
    }
}