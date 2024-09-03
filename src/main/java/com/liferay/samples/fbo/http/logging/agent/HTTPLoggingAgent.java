package com.liferay.samples.fbo.http.logging.agent;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

public class HTTPLoggingAgent {

	public static void premain(String argument, Instrumentation inst) {

		System.out.println("HTTP Logging Agent premain");
		
        new AgentBuilder.Default()
        	.type(ElementMatchers.named("com.nimbusds.oauth2.sdk.http.HTTPRequest"))
            .transform(new Transformer() {
            	@Override
            	public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription,
            			ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {

            		System.out.println("HTTP Logging Agent Transforming: " + typeDescription.getName());

                    return builder.method(ElementMatchers.named("send"))
                                  .intercept(Advice.to(HTTPResponseAdvice.class));            	}
            })
        	.installOn(inst);

	}
}
