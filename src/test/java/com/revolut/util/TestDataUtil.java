package com.revolut.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.revolut.config.GuiceModule;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.util.Arrays.asList;

public class TestDataUtil {

    private static DataSource ds;
    private static Flyway flyway;

    private static List<String> testDataQuerys = asList(
            "insert into transactions.accounts (title, branch_id, account_number, account_balance) values ('Edd', 901, 123401, 11000), ('Eddy', 902, 123402, 12000)"
    );

    private static List<String> tearDownScripts = asList(
            "delete from transactions.accounts where branch_id in (901, 902)",
            "delete from transactions.transfers"
    );

    static {
        Injector injector = Guice.createInjector(new GuiceModule());
        ds = injector.getBinding(Key.get(DataSource.class)).getProvider().get();
        flyway = new Flyway();
        flyway.setDataSource(ds);
    }

    public static void runFlywayMigration() {
        flyway.migrate();
    }

    public static void addTestData() throws SQLException {
        Statement statement = ds.getConnection().createStatement();
        for (String dataScript : testDataQuerys) {
            statement.execute(dataScript);
        }
    }


    public static void tearDown() throws SQLException {

        Statement statement = ds.getConnection().createStatement();
        for (String tearDownScript : tearDownScripts) {
            statement.execute(tearDownScript);
        }


    }

}
