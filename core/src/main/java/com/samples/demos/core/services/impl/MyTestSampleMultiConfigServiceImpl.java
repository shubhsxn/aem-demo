package com.samples.demos.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.samples.demos.core.services.MyTestSampleMultiConfigService;


@Component(service=MyTestSampleMultiConfigService.class)
@Designate(ocd=MyTestSampleMultiConfigServiceImpl.Config.class, factory=true)
public class MyTestSampleMultiConfigServiceImpl implements MyTestSampleMultiConfigService {

	String MemName;
	String MemPlace;
	int MemPIN;
	
	@ObjectClassDefinition(name="MyTest Multi Config factory Demo")
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
		MemName = config.getMemberName();
		MemPlace = config.getMemberPlace();
		MemPIN = config.getMemberPIN();
    }
 
	@Override
	public String getMemberName() {
		// TODO Auto-generated method stub
		return MemName;
	}

	@Override
	public String getMemberPlace() {
		// TODO Auto-generated method stub
		return MemPlace;
	}

	@Override
	public int getMemberPIN() {
		// TODO Auto-generated method stub
		return MemPIN;
	}

}
