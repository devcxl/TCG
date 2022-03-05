package com.github.devcxl.tcg.directive;

import com.github.devcxl.tcg.utils.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * @author orange
 */
public class Service extends Directive {
    private final static String END = "Service";

    @Override
    public String getName() {
        return END;
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String value = (String) node.jjtGetChild(0).value(internalContextAdapter);
        if (StringUtils.isBlank(value)) {
            writer.write(value);
            return true;
        }
        String result = value + END;
        writer.write(result);
        return false;
    }
}
