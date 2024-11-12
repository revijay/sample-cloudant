package io.openliberty.sample.cloudant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.ServerInformation;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;

@ApplicationScoped
public class CloudantProducer {

    @Inject
    @ConfigProperty(name = "cloudant.apikey") 
    String apikey;
    
    @Inject
    @ConfigProperty(name = "cloudant.servicename") 
    String servicename;
    
    @Inject
    @ConfigProperty(name = "cloudant.host") 
    String host;
	
	@Produces
    public Cloudant createCloudant() {
		
		IamAuthenticator authenticator = new IamAuthenticator.Builder()
			    .apikey(apikey)
			    .build();

		Cloudant service = new Cloudant(servicename, authenticator);
		service.setServiceUrl("https://"+host);

		return service;
    }
	
	@Produces
    public ServerInformation createDB(Cloudant client) {
		 ServerInformation response =
				client.getServerInformation().execute().getResult();
		 return response;
    }
}


