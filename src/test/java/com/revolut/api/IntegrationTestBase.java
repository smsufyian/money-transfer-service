package com.revolut.api;

import com.revolut.Application;
import com.revolut.util.TestDataUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;

import static spark.Spark.stop;

public abstract class IntegrationTestBase {

    @BeforeClass
    public static void setUp() {
        Application.main();
        TestDataUtil.runFlywayMigration();

    }

    @AfterClass
    public static void stopServer() {
        stop();
    }


    @Before
    public void beforeMethod() throws SQLException {
        TestDataUtil.addTestData();
    }

    @After
    public void tearDown() throws SQLException {
        TestDataUtil.tearDown();
    }

}
