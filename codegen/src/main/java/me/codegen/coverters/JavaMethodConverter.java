package me.codegen.coverters;

import me.Converter;
import me.codegen.model.JavaMethod;
import me.codegen.model.JavaMethodBuilder;
import me.codegen.model.JavaProperty;
import me.codegen.model.JavaType;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class JavaMethodConverter implements Converter<ExecutableElement, JavaMethod> {

    private final Converter<String, JavaType> toJavaType;
    private final Converter<VariableElement, JavaProperty> toJavaProperty;

    public JavaMethodConverter(Converter<String, JavaType> toJavaType, Converter<VariableElement, JavaProperty> toJavaProperty) {
        this.toJavaType = toJavaType;
        this.toJavaProperty = toJavaProperty;
    }

    @Override
    public JavaMethod covert(ExecutableElement executableElement) {
        JavaMethodBuilder methodBuilder = new JavaMethodBuilder()
                .withName(executableElement.getSimpleName().toString())
                .withReturnType(toJavaType.covert(executableElement.getReturnType().toString()));
        //Populate constructor parameters
        for (VariableElement variableElement : executableElement.getParameters()) {
            methodBuilder = methodBuilder
                    .withName(executableElement.getSimpleName().toString())
                    .withReturnType(toJavaType.covert(executableElement.getReturnType().toString()))
                    .addToArguments(toJavaProperty.covert(variableElement));
            for (TypeMirror thrownType : executableElement.getThrownTypes()) {
                methodBuilder = methodBuilder.addToExceptions(toJavaType.covert(thrownType.toString()));
            }
        }
        return methodBuilder.build();
    }
}
