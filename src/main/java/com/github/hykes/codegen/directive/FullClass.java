package com.github.hykes.codegen.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * @author PailieXiangLong
 */
public class FullClass extends Directive {

    @Override
    public String getName() {
        return "FullClass";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String clazz = (String) node.jjtGetChild(0).value(internalContextAdapter);
        if (internalContextAdapter.containsKey(clazz)) {
            writer.write(internalContextAdapter.get(clazz).toString());
            return true;
        }
        return false;
    }
}
