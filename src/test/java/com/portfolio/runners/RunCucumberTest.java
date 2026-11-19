package com.portfolio.runners;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "cucumber.glue",
        value = "com.portfolio.stepdefinitions"
)
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty, html:target/cucumber-report.html"
)
public class RunCucumberTest {

}

