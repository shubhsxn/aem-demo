package com.samples.demos.core.services.impl;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samples.demos.core.services.MyTestSampleService;

@Component(service=MyTestSampleService.class)
@Designate(ocd = MyTestSampleServiceImpl.Config.class)
public class MyTestSampleServiceImpl implements MyTestSampleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyTestSampleServiceImpl.class);
	
	private String MemberName;
	private String MemberPlace;
	private int PINCode;
	 
    @ObjectClassDefinition(name = "MyTestSampleService configuration")
    public @interface Config {
        
        @AttributeDefinition(name = "Name", defaultValue = "Raghava")
        String getMemberName();
        @AttributeDefinition(name = "Place", defaultValue = "Hyderabad")
        String getMemberPlace();
        @AttributeDefinition(name = "Pin", defaultValue = "500001")
        int getMemberPIN();
    }
    
    @Activate
    protected void activate(final Config config) {
    	MemberName = config.getMemberName();
    	MemberPlace = config.getMemberPlace();
    	PINCode = config.getMemberPIN();
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return MemberName;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return MemberPlace;
	}

	@Override
	public int getPIN() {
		// TODO Auto-generated method stub
		return PINCode;
	}

}
