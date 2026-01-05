package com.phuong_coi.english.util;
import com.google.cloud.NoCredentials;
import com.google.cloud.ServiceOptions;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.phuong_coi.english.entity.Employee;
import com.phuong_coi.english.entity.User;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ObjectifyInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("========================================");
        System.out.println("Initializing Objectify via Listener...");
        
        try {
            String emulatorHost = System.getenv("DATASTORE_EMULATOR_HOST");
            String projectId = System.getenv("DATASTORE_PROJECT_ID");
            System.out.println("host duoc lay tu bien moi truong set bang bash :" + emulatorHost);
            System.out.println("project id duoc lay tu bien moi truong set bang bash :" + projectId);
            
            if (emulatorHost == null) {
                // emulatorHost = System.getProperty("datastore.emulator.host");
                //emulatorHost = "localhost:8081";
            }
            if (projectId == null) {
                projectId = System.getProperty("datastore.project_id");
                if (projectId == null) {
                    projectId = "usermanager-480702";
                }
            }
            
            System.out.println("Configuration:");
            System.out.println("  Project ID: " + projectId);
            
            Datastore datastore;
            
            if (emulatorHost != null && !emulatorHost.isEmpty()) {
                System.out.println("  Emulator Host: " + emulatorHost);
                System.out.println("  Mode: LOCAL EMULATOR");
                
                DatastoreOptions options = DatastoreOptions.newBuilder()
                    .setProjectId(projectId)
                    .setHost("http://" + emulatorHost)
                    .setCredentials(NoCredentials.getInstance())
                    .setRetrySettings(ServiceOptions.getNoRetrySettings())
                    .build();
                
                datastore = options.getService();
                
            } else {
                System.out.println("  Mode: PRODUCTION CLOUD DATASTORE");
                
                DatastoreOptions options = DatastoreOptions.newBuilder()
                    .setProjectId(projectId)
                    .build();
                
                datastore = options.getService();
            }
            
            // Init Objectify
            ObjectifyService.init(new ObjectifyFactory(datastore));
            
            // Register entities
            ObjectifyService.register(User.class);
            ObjectifyService.register(Employee.class);
            
            System.out.println("✓ Objectify initialized successfully!");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("✗ Failed to initialize Objectify!");
            e.printStackTrace();
            throw new RuntimeException("Cannot start application without Objectify", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Objectify context destroyed");
    }
}
