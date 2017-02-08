package com.shivamdev.contactsmanager.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by shivam on 7/2/17.
 */

public class MockWebserverRule implements TestRule {

    public final MockWebServer server = new MockWebServer();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                server.start();
                base.evaluate();
                server.shutdown();
            }
        };
    }
}
