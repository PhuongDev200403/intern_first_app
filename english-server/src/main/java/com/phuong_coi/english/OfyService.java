package com.phuong_coi.english;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.phuong_coi.english.entity.User;

public class OfyService {
    private static boolean initialized = false;

    private static synchronized void initIfNeeded() {
        if (!initialized) {
            System.out.println("Initializing Objectify for Datastore Emulator...");
            
            String emulatorHost = System.getProperty("datastore.emulator.host");
            String projectId = System.getProperty("datastore.project_id");
            
            System.out.println("Java System Properties:");
            System.out.println("  datastore.emulator.host: " + emulatorHost);
            System.out.println("  datastore.project_id: " + projectId);
            
            if (projectId == null || projectId.isEmpty()) {
                System.err.println("ERROR: datastore.project_id not set!");
                throw new RuntimeException("Project ID is required but not found in system properties");
            }
            
            try {
                // Tạo DatastoreOptions với cấu hình thủ công
                DatastoreOptions.Builder optionsBuilder = DatastoreOptions.newBuilder()
                    .setProjectId(projectId);
                
                // Nếu có emulator host thì set
                if (emulatorHost != null && !emulatorHost.isEmpty()) {
                    System.out.println("Using Datastore Emulator at: " + emulatorHost);
                    optionsBuilder.setHost("http://" + emulatorHost);
                } else {
                    System.out.println("Using Cloud Datastore (production mode)");
                }
                
                Datastore datastore = optionsBuilder.build().getService();
                
                // Khởi tạo Objectify với Datastore đã cấu hình
                ObjectifyService.init(new ObjectifyFactory(datastore));
                ObjectifyService.register(User.class);
                
                initialized = true;
                System.out.println("✓ Objectify initialized successfully!");
                System.out.println("  Project ID: " + projectId);
                System.out.println("  Emulator: " + (emulatorHost != null ? emulatorHost : "Not used (Cloud mode)"));
                
            } catch (Exception e) {
                System.err.println("✗ Failed to initialize Objectify: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Cannot initialize Objectify", e);
            }
        }
    }

    private OfyService() {}

    public static Objectify ofy() {
        initIfNeeded();
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        initIfNeeded();
        return ObjectifyService.factory();
    }
}
