package com.github.hykes.codegen.directive;

import com.github.hykes.codegen.utils.StringUtils;
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
public class ServiceImpl extends Directive {

    private final static String END = "ServiceImpl";

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
        String result = value.replaceFirst(value.substring(0, 1), value.substring(0, 1).toLowerCase()) + END;
        int i = node.jjtGetNumChildren();
        if (i>1){
            boolean upperCase = (boolean) node.jjtGetChild(1).value(internalContextAdapter);
            if (upperCase) {
                result = value + END;
            }
        }
        if (StringUtils.isBlank(value)) {
            writer.write(value);
            return true;
        }
        writer.write(result);
        return true;
    }
}
