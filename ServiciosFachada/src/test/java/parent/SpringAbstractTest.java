package parent;

//import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class SpringAbstractTest {

	private ApplicationContext context;
	
	
	public void initSpringContext(){
		
		setContext(new ClassPathXmlApplicationContext("applicationContext.xml"));		
	}


	protected ApplicationContext getContext() {
		return context;
	}


	protected void setContext(ApplicationContext context) {
		this.context = context;
	}
	
	protected Object getBean(String bean){		
		return context.getBean(bean);
	}

	
	

}
