package com.joeyvmason.serverless.spring.application;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdacontainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    SpringLambdacontainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    boolean initialized = false;

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (!initialized) {
            initialized = true;
            try {
                handler = SpringLambdacontainerHandler.getAwsProxyHandler(MvcConfig.class);
            } catch (ContainerInitializationException e) {
                e.printStackTrace();
                return null;
            }
        }

        return handler.proxy(awsProxyRequest, context);
    }
}
