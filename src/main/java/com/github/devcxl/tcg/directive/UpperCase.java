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
 *  首字母大写
 * ${LowerCase 'abc'} => Abc
 * @author hehaiyangwork@gmail.com
 * @date 2017/12/19
 */
public class UpperCase extends Directive {
    @Override
    public String getName() {
        return "UpperCase";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String value = (String) node.jjtGetChild(0).value(context);
        String result = value.replaceFirst(value.substring(0, 1), value.substring(0, 1).toUpperCase());
        writer.write(result);
        return true;
    }
}
