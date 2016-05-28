package com.mymock.nutch;


import org.junit.After;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;

import static com.github.dreamhead.moco.Runner.runner;



/**
 * @author jianglibo@gmail.com
 * 
 * May 28, 2016
 */
public class BaseForMocoRunner {
    private Runner runner;

    public void setup(HttpServer server) {
        runner = runner(server);
        runner.start();
    }

    @After
    public void tearDown() {
        runner.stop();
    }
}
