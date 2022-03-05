package com.github.devcxl.tcg;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jetbrains.java.generate.velocity.VelocityFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2017/12/19
 */
public class VelocityTest {

    @Test
    public void testSplitLowerCase() {
        VelocityEngine velocityEngine = VelocityFactory.getVelocityEngine();
        velocityEngine.loadDirective("com.github.devcxl.tcg.directive.LowerCase");
        velocityEngine.loadDirective("com.github.devcxl.tcg.directive.ServiceImpl");
        velocityEngine.loadDirective("com.github.devcxl.tcg.directive.Service");
        velocityEngine.loadDirective("com.github.devcxl.tcg.directive.Split");
        String template = "#Split(\"#LowerCase(${NAME})\" '.') #Service(${NAME}) #ServiceImpl(${NAME},true)";
        Map<String, Object> map = new HashMap<>();
        map.put("NAME", "HykesIsStrong");

        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(new VelocityContext(map), writer, "", template);
        System.out.println(writer.toString());
    }

}
