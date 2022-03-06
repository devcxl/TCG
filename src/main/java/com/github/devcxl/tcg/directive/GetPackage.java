package com.github.devcxl.tcg.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * 获取package路径
 *
 * @author hehaiyangwork@gmail.com
 * @date 2017/12/19
 */
public class GetPackage extends Directive {
    @Override
    public String getName() {
        return "GetPackage";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String clazz = (String) node.jjtGetChild(0).value(context);
        if (context.containsKey(clazz)) {
            String packagePath = context.get(clazz).toString();
            packagePath = "" + packagePath;
            writer.write(packagePath);
            return true;
        }
        return false;
    }
}
