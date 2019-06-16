package dao;

import model.AcqReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ReportDao {

    private static final SessionFactory SESSION_FACTORY;
    private static Session session;
    private Transaction transaction = null;

    static {
        try {
            SESSION_FACTORY = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void save(AcqReport report) {
        session.save(report);
    }

    public void begin() {
        if (session == null || !session.isOpen())
            session = SESSION_FACTORY.openSession();
        transaction = session.beginTransaction();
    }

    public synchronized void close() {
        if (session != null)
            session.close();
    }

    public void commit() {
        transaction.commit();
    }

    public void rollback() {
        transaction.rollback();
    }
}
