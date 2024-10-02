package org.utfpr.mf.mf_api.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.utfpr.mf.mf_api.MfApiApplication;
import org.utfpr.mf.model.Credentials;
import org.utfpr.mf.rdb.DatabaseInserterSample;
import org.utfpr.mf.rdb.RdbDatabase;
import org.utfpr.mf.rdb.TempDatabase;
import org.utfpr.mf.tools.DataImporter;

import java.sql.SQLException;
import java.util.HashMap;

@Service
public class TempDatabaseService {

    private RdbDatabase rdbDatabase;
    private HashMap<String, TempDatabase> tempDatabases = new HashMap<>();

    public TempDatabaseService(
            @Value("${spring.datasource.url}")
            String connectionString,
            @Value("${spring.datasource.username}")
            String username,
            @Value("${spring.datasource.password}")
            String password
    ) {
        this.rdbDatabase = new RdbDatabase(new Credentials(connectionString, username, password));
        assert this.rdbDatabase.getConnection() != null : "Unable to connect to the database";
        if(this.rdbDatabase.getConnection() == null) {
            throw new RuntimeException("Could not connect to the database");
        }
    }

    @PostConstruct
    public void createTestDatabase() throws SQLException {
        var tdb = TempDatabase.create("airport3", rdbDatabase.getCredentials());
        tempDatabases.put("airport3", tdb);
        if(tdb.getCredentials().getCreationMethod() == Credentials.CreationMethod.CREATE_DATABASE) {
            DataImporter.Companion.runSQLFromFile("src/main/resources/data.sql", tdb.getConnection());
            if(MfApiApplication.INSERT_TEST_DATA) {
                DatabaseInserterSample sample = new DatabaseInserterSample(tdb.getCredentials());
                sample.insertData();
            }
        }
    }

    @PreDestroy
    public void destroy() throws SQLException {
        for(var tdb : tempDatabases.values()) {
            tdb.drop();
        }
    }
}
