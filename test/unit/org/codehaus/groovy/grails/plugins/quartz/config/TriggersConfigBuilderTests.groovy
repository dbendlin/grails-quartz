package org.codehaus.groovy.grails.plugins.quartz.config

import org.codehaus.groovy.grails.plugins.quartz.GrailsJobClassProperty as GJCP
import org.codehaus.groovy.grails.plugins.quartz.CustomTriggerFactoryBean

/**
 * TODO: write javadoc
 *
 * @author Sergey Nebolsin (nebolsin@gmail.com)
 */
class TriggersConfigBuilderTests extends GroovyTestCase {
    void testConfigBuilder() {
        def builder = new TriggersConfigBuilder('TestJob')
        def closure = {
            simple()
            simple timeout:1000
            simple startDelay:500
            simple startDelay:500, timeout: 1000
            simple startDelay:500, timeout: 1000, repeatCount: 3
            simple name: 'everySecond', timeout:1000
            cron()
            cron cronExpression:'0 15 6 * * ?'
            cron name: 'myTrigger', cronExpression:'0 15 6 * * ?'
            simple startDelay:500, timeout: 1000, repeatCount: 0
        }
        builder.build(closure)

        assertEquals 'Invalid triggers count', 10, builder.triggers.size()

        def jobName = 'TestJob0'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: GJCP.DEFAULT_START_DELAY,
                repeatInterval: GJCP.DEFAULT_TIMEOUT,
                repeatCount: GJCP.DEFAULT_REPEAT_COUNT,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob1'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: GJCP.DEFAULT_START_DELAY,
                repeatInterval: 1000,
                repeatCount: GJCP.DEFAULT_REPEAT_COUNT,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob2'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: 500,
                repeatInterval: GJCP.DEFAULT_TIMEOUT,
                repeatCount: GJCP.DEFAULT_REPEAT_COUNT,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob3'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: 500,
                repeatInterval: 1000,
                repeatCount: GJCP.DEFAULT_REPEAT_COUNT,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob4'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean 
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: 500,
                repeatInterval: 1000,
                repeatCount: 3,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'everySecond'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: GJCP.DEFAULT_START_DELAY,
                repeatInterval: 1000,
                repeatCount: GJCP.DEFAULT_REPEAT_COUNT,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob5'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay:GJCP.DEFAULT_START_DELAY,
                cronExpression: GJCP.DEFAULT_CRON_EXPRESSION,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'TestJob6'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                cronExpression: '0 15 6 * * ?',
                startDelay: GJCP.DEFAULT_START_DELAY,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
        
        jobName = 'myTrigger'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: GJCP.DEFAULT_START_DELAY,
                cronExpression: '0 15 6 * * ?',
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )

        jobName = 'TestJob7'
        assert builder.triggers[jobName]?.clazz == CustomTriggerFactoryBean
        assertPropertiesEquals(new Expando(
                name:jobName,
                group: GJCP.DEFAULT_TRIGGERS_GROUP,
                startDelay: 500,
                repeatInterval: 1000,
                repeatCount: 0,
                volatility: GJCP.DEFAULT_VOLATILITY
            ), builder.triggers[jobName].triggerAttributes
        )
    }

    private assertPropertiesEquals(expected, actual) {
        expected.properties.each { entry ->
            assert actual[entry.key] == entry.value, "Unexpected value for property: ${entry.key}" 
        }
        assert actual.size() == expected.properties?.size(), 'Different number of properties'
    }

}
