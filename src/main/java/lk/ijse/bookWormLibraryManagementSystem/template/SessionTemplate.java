package lk.ijse.bookWormLibraryManagementSystem.template;

import lk.ijse.bookWormLibraryManagementSystem.util.SessionFactoryConfig;
import org.hibernate.Session;

public abstract class SessionTemplate<T> {
    // La sequence est fixe et finale
    public final T execute() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        try {
            injectSession(session);   // etape 1 : injecter la session
            return doWork(session);   // etape 2 : faire le travail
        } catch (Exception e) {
            e.printStackTrace();
            return getDefaultValue();
        } finally {
            session.close();          // etape 3 : fermer — toujours execute
        }
    }
    // Sous-classes implementent ces methodes
    protected abstract void injectSession(Session session);
    protected abstract T doWork(Session session);
    protected T getDefaultValue() { return null; }
}
