package com.tms.teachmeskills.config;

import com.tms.teachmeskills.dao.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/c18-onl2022");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "Barsukov28071992");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                /*hibernate.hbm2ddl.auto автоматически проверяет или экспортирует DDL схемы в базу данных
                при создании SessionFactory.
                Список возможных вариантов:
                validate: проверить схему, не вносить изменения в базу данных.
                update: обновить схему, в т.ч. сделать alter table  и добавить новые поля.
                create: создает схему, уничтожая предыдущие данные.
                create-drop: удалить схему, когда SessionFactory закрывается явно, как правило, когда приложение остановлено.
                */
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.USE_QUERY_CACHE, true);

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
